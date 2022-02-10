package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Rectangle;
import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.painter.Painter;
import io.github.redstoneparadox.oaktree.painter.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * The base class for all controls.
 */
public class Control {
	protected Control parent;
	protected @NotNull String id = "control";
	protected @NotNull Anchor anchor = Anchor.TOP_LEFT;
	protected final @NotNull  Rectangle area = new Rectangle(0, 0, 1, 1);
	protected boolean expand = false;
	protected boolean visible = true;
	protected BiConsumer<ControlGui, Control> onTick = (gui, control) -> {};
	protected Painter currentStyle = Painter.BLANK;
	protected Theme internalTheme = new Theme();

	//Internal State
	protected int trueX = 0;
	protected int trueY = 0;
	protected Rectangle trueArea = new Rectangle(0, 0, 1, 1);

	public void setId(@NotNull String id) {
		this.id = id;
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
	 */
	public void setAnchor(@NotNull Anchor anchor) {
		this.anchor = anchor;
	}

	public @NotNull Anchor getAnchor() {
		return anchor;
	}

	/**
	 * Sets the offset of the node on the screen relative to the anchor.
	 * Values are in pixels. Due to the way screen coordinates work,
	 * positive y is down so a position of (10, 10) will place the node 10
	 * pixels to the left and 10 pixels below the top-left corner of the
	 * screen.
	 *
	 * @param x The new x offset in pixels.
	 * @param y The new y offset in pixels.
	 */
	public void setOffset(int x, int y) {
		area.setX(x);
		area.setY(y);
	}

	/**
	 * Sets the offset of the node on the screen relative to the anchor.
	 * Values are in pixels. Due to the way screen coordinates work,
	 * positive y is down so a position of (10, 10) will place the node 10
	 * pixels to the left and 10 pixels below the top-left corner of the
	 * screen.
	 *
	 * @param offset The offset in Vector2 form.
	 */
	public void setOffset(@NotNull Vector2 offset) {
		area.setX(offset.getX());
		area.setY(offset.getY());
	}

	public @NotNull Vector2 getOffset() {
		return new Vector2(area.getX(), area.getY());
	}

	/**
	 * Sets the width and height of this node. Values are in pixels.
	 * Due to the way screen coordinates work, positive y is down so a
	 * size of (10, 10) would mean that the node extends 10 pixels to the
	 * left of and 10 pixels down from it's position.
	 *
	 * @param width The new width of this node in pixels.
	 * @param height The new height of this node in pixels.
	 */
	public void setSize(int width, int height) {
		area.setWidth(width);
		area.setHeight(height);
	}

	/**
	 * Sets the width and height of this node. Values are in pixels.
	 * Due to the way screen coordinates work, positive y is down so a
	 * size of (10, 10) would mean that the node extends 10 pixels to the
	 * left of and 10 pixels down from it's position.
	 *
	 * @param size The size in Vector2 form.
	 */
	public void setSize(@NotNull Vector2 size) {
		area.setWidth(size.getX());
		area.setHeight(size.getY());
	}

	public @NotNull Vector2 getSize() {
		return new Vector2(area.getWidth(), area.getHeight());
	}

	public @NotNull Rectangle getArea() {
		return this.area;
	}

	/**
	 * Sets whether or not this should automatically expand to the
	 * size of the parent node. If set to true, any size and position settings
	 * will be ignored when drawing the node.
	 *
	 * @param expand The value to set
	 */
	public void setExpand(boolean expand) {
		this.expand = expand;
	}

	public boolean shouldExpand() {
		return expand;
	}

	/**
	 * Sets whether or not this node should be visible. Any nodes that
	 * are not visible will not be drawn, cannot be interacted with,
	 * and will cause their children to not be drawn (if they have any).
	 *
	 * @param visible Whether or not this node should be visible.
	 * @return The node itself.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return this.visible;
	}

	/**
	 * Sets the base {@link Painter} for this node. For most
	 * nodes, this is the only style, but some will have multiple
	 * styles so it is considered the default style.
	 *
	 * @param baseStyle The StyleBox for this node.
	 */
	public void setBasePainter(Painter baseStyle) {
		internalTheme.add("self", baseStyle);
	}

	public Painter getBasePainter() {
		return internalTheme.get("self/base");
	}

	/**
	 * Sets a function to run every time this node is ticked.
	 *
	 * @param onTick the function to run.
	 */
	public void onTick(@NotNull Consumer<ControlGui> onTick) {
		this.onTick = ((controlGui, cControl) -> onTick.accept(controlGui));
	}

	public Vector2 getTruePosition() {
		return new Vector2(trueX, trueY);
	}

	protected void updateTree(List<Control> zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		zIndexedControls.add(this);

		if (expand) {
			trueArea = new Rectangle(containerX, containerY, containerWidth, containerHeight);
		}
		else {
			Vector2 anchorOffset = anchor.getOffset(containerWidth, containerHeight);
			Vector2 drawOffset = anchor.getOffset(area.getWidth(), area.getHeight());

			trueArea = new Rectangle(
					area.getX() + anchorOffset.getX() + containerX - drawOffset.getX(),
					area.getY() + anchorOffset.getY() + containerY - drawOffset.getY(),
					area.getWidth(),
					area.getHeight()
			);
		}
	}

	// Capture the mouse and
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		int x = trueArea.getX();
		int y = trueArea.getY();

		return mouseX >= x && mouseX <= x + area.getWidth() && mouseY >= y && mouseX <= y + area.getHeight();
	}

	// Update current
	protected void prepare() {
		onTick.accept(null, this);
	}

	// Draw
	protected void draw(MatrixStack matrices) {
		currentStyle.draw(matrices, trueArea.getX(), trueArea.getY(), trueArea.getWidth(), trueArea.getHeight());
	}

	protected void setParent(Control parent) {
		this.parent = parent;
	}

	protected Control getParent() {
		return parent;
	}

	protected void markDirty() {
		parent.markDirty();
	}

	@ApiStatus.Internal
	@Deprecated
	public void zIndex(List<Control> controls) {
		if (!visible) return;
		controls.add(this);
	}

	@ApiStatus.Internal
	@Deprecated
	public void setup(MinecraftClient client, ControlGui gui) {

	}

	@ApiStatus.Internal
	@Deprecated
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		onTick.accept(gui, this);
		currentStyle = getPainter(gui.getTheme(), "base");

		if (!expand) {
			Vector2 anchorOffset = anchor.getOffset(containerWidth, containerHeight);
			Vector2 drawOffset = anchor.getOffset(area.getWidth(), area.getHeight());

			trueX = area.getX() + anchorOffset.getX() + offsetX - drawOffset.getX();
			trueY = area.getY() + anchorOffset.getY() + offsetY - drawOffset.getY();
		}
		else {
			trueX = offsetX;
			trueY = offsetY;

			area.setWidth(containerWidth);
			area.setHeight(containerHeight);
		}
	}

	@ApiStatus.Internal
	@Deprecated
	public void oldDraw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		currentStyle.draw(matrices, trueX, trueY, area.getWidth(), area.getHeight());
	}

	@ApiStatus.Internal
	protected final Painter getPainter(Theme theme, String name) {
		Painter style = internalTheme.get("self/" + name);

		if (style.blank) {
			style = theme.get(id + "/" + name);
		}

		return style;
	}

	public static class PainterKey {
		protected PainterKey() {}
	}
}
