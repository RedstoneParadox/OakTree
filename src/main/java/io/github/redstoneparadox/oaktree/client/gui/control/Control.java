package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.ControlStyle;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.math.Rectangle;
import io.github.redstoneparadox.oaktree.client.math.Vector2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * The base class for all controls.
 */
public class Control<C extends Control<C>> {
	protected @NotNull String id = "control";
	protected @NotNull Anchor anchor = Anchor.TOP_LEFT;
	protected final @NotNull  Rectangle area = new Rectangle(0, 0, 1, 1);
	protected boolean expand = false;
	protected boolean visible = true;
	protected @NotNull  BiConsumer<ControlGui, C> onTick = (gui, control) -> {};

	protected ControlStyle currentStyle = ControlStyle.BLANK;
	protected Theme internalTheme = new Theme();

	protected int trueX = 0;
	protected int trueY = 0;

	public C id(@NotNull  String id) {
		this.id = id;
		return (C)this;
	}

	public @NotNull String getId() {
		return this.id;
	}

	/**
	 * Anchors the position of this node relative to the parent using
	 * a {@link Anchor}. For example, a value of
	 * {@link Anchor#CENTER} and a position of (10, 0)
	 * will result in the node being placed 10 pixels to the left of
	 * the parent node's center.
	 *
	 * @param anchor The {@link Anchor} to anchor to.
	 * @return The node itself.
	 */
	public C anchor(@NotNull Anchor anchor) {
		this.anchor = anchor;
		return (C)this;
	}

	public @NotNull Anchor getAnchor() {
		return anchor;
	}

	/**
	 * Sets the position of the node on the screen relative to the parent.
	 * Values are in pixels. Due to the way screen coordinates work,
	 * positive y is down so a position of (10, 10) will place the node 10
	 * pixels to the left and 10 pixels below the top-left corner of the
	 * screen.
	 *
	 * @param x The new x position in pixels.
	 * @param y The new y position in pixels.
	 * @return The node itself.
	 */
	public C position(int x, int y) {
		area.x = x;
		area.y = y;
		return (C)this;
	}

	/**
	 * Sets the width and height of this node. Values are in pixels.
	 * Due to the way screen coordinates work, positive y is down so a
	 * size of (10, 10) would mean that the node extends 10 pixels to the
	 * left of and 10 pixels down from it's position.
	 *
	 * @param width The new width of this node in pixels.
	 * @param height The new height of this node in pixels.
	 * @return The node itself.
	 */
	public C size(int width, int height) {
		area.width = width;
		area.height = height;
		return (C)this;
	}

	public @NotNull Rectangle getArea() {
		return this.area;
	}

	/**
	 * Sets whether or not this should automatically expand to the
	 * size of the parent node. If set to true, any size and position settings
	 * will be ignored when drawing the node.
	 *
	 * @param value
	 * @return The node itself.
	 */
	public C expand(boolean value) {
		expand = value;
		return (C)this;
	}

	public boolean shouldExpand() {
		return expand;
	}

	/**
	 * Sets whether or not this node should be visible. Any nodes that
	 * are not visible will not be drawn, cannot be interacted with,
	 * and will cause their children to not be drawn (if they have any).
	 *
	 * @param value Whether or not this node should be visible.
	 * @return The node itself.
	 */
	public C visible(boolean value) {
		this.visible = value;
		return (C)this;
	}

	public boolean isVisible() {
		return this.visible;
	}

	/**
	 * Sets the base {@link ControlStyle} for this node. For most
	 * nodes, this is the only style, but some will have multiple
	 * styles so it is considered the default style.
	 *
	 * @param baseStyle The StyleBox for this node.
	 * @return The node itself.
	 */
	public C baseStyle(ControlStyle baseStyle) {
		internalTheme.add("self", baseStyle);
		return (C)this;
	}

	/**
	 * Sets a function to run every time this
	 * node is ticked.
	 *
	 * @param function the function to run.
	 * @return The node itself.
	 */
	public C onTick(@NotNull BiConsumer<ControlGui, C> function) {
		onTick = function;
		return (C)this;
	}

	public Vector2 getTruePosition() {
		return new Vector2(trueX, trueY);
	}

	@ApiStatus.Internal
	public void zIndex(List<Control<?>> controls) {
		if (!visible) return;
		controls.add(this);
	}

	@ApiStatus.Internal
	public void setup(MinecraftClient client, ControlGui gui) {

	}

	@ApiStatus.Internal
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		onTick.accept(gui, (C)this);
		currentStyle = getStyle(gui.getTheme(), "base");

		if (!expand) {
			Vector2 anchorOffset = anchor.getOffset(containerWidth, containerHeight);
			Vector2 drawOffset = anchor.getOffset(area.width, area.height);

			trueX = area.x + anchorOffset.x + offsetX - drawOffset.x;
			trueY = area.y + anchorOffset.y + offsetY - drawOffset.y;
		}
		else {
			trueX = offsetX;
			trueY = offsetY;

			area.width = containerWidth;
			area.height = containerHeight;
		}
	}

	@ApiStatus.Internal
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		currentStyle.draw(trueX, trueY, area.width, area.height, gui);
	}

	@Deprecated
	@ApiStatus.ScheduledForRemoval
	void applyTheme(Theme theme) {

	}

	@ApiStatus.Internal
	protected final ControlStyle getStyle(Theme theme, String name) {
		ControlStyle style = internalTheme.get("self/" + name);

		if (style.blank) {
			style = theme.get(id + "/" + name);
		}

		return style;
	}
}
