package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Rectangle;
import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.painter.Theme;
import io.github.redstoneparadox.oaktree.util.Action;
import io.github.redstoneparadox.oaktree.util.ZIndexedControls;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

/**
 * The base class for all controls.
 */
public class Control {
	public static PainterKey DEFAULT = new PainterKey();

	protected Control parent;
	protected Control tooltip = null;
	protected @NotNull String id = "control";
	protected @NotNull Anchor anchor = Anchor.TOP_LEFT;
	protected final @NotNull  Rectangle area = new Rectangle(0, 0, 1, 1);
	protected boolean expand = false;
	protected boolean visible = true;
	protected boolean isTooltip = false;
	protected Action onTick = () -> {};

	//Internal State
	protected PainterKey painterKey = DEFAULT;
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
	 * Anchors the position of this Control relative to the parent using
	 * a {@link Anchor}. For example, a value of
	 * {@link Anchor#CENTER} and a position of (10, 0)
	 * will result in the Control being placed 10 pixels to the left of
	 * its anchor
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
	 * Sets the offset of the Control on the screen relative to the anchor.
	 * Values are in pixels. Due to the way screen coordinates work,
	 * positive y is down so a position of (10, 10) will place the Control 10
	 * pixels to the left and 10 pixels below the top-left corner of the
	 * screen.
	 *
	 * @param x The new x offset in pixels.
	 * @param y The new y offset in pixels.
	 */
	public void setOffset(int x, int y) {
		area.setX(x);
		area.setY(y);
		if (!isTooltip) markDirty();
	}

	/**
	 * Sets the offset of the Control on the screen relative to the anchor.
	 * Values are in pixels. Due to the way screen coordinates work,
	 * positive y is down so a position of (10, 10) will place the Control 10
	 * pixels to the left and 10 pixels below the top-left corner of the
	 * parent Control.
	 *
	 * @param offset The offset in Vector2 form.
	 */
	public void setOffset(@NotNull Vector2 offset) {
		setOffset(offset.getX(), offset.getY());
	}

	public @NotNull Vector2 getOffset() {
		return new Vector2(area.getX(), area.getY());
	}

	/**
	 * Sets the width and height of this Control. Values are in pixels.
	 * Due to the way screen coordinates work, positive y is down so a
	 * size of (10, 10) would mean that the Control extends 10 pixels to the
	 * left of and 10 pixels down from its position.
	 *
	 * @param width The new width of this Control in pixels.
	 * @param height The new height of this Control in pixels.
	 */
	public void setSize(int width, int height) {
		area.setWidth(width);
		area.setHeight(height);
		if (!isTooltip) markDirty();
	}

	/**
	 * Sets the width and height of this Control. Values are in pixels.
	 * Due to the way screen coordinates work, positive y is down so a
	 * size of (10, 10) would mean that the Control extends 10 pixels to the
	 * left of and 10 pixels down from it's position.
	 *
	 * @param size The size in Vector2 form.
	 */
	public void setSize(@NotNull Vector2 size) {
		setSize(size.getX(), size.getY());
	}

	public @NotNull Vector2 getSize() {
		return new Vector2(area.getWidth(), area.getHeight());
	}

	public @NotNull Rectangle getArea() {
		return this.area;
	}

	/**
	 * Sets whether or not this should automatically expand to the
	 * size of the parent Control. If set to true, any size and position settings
	 * will be ignored when drawing the Control.
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
	 * Sets whether or not this Control should be visible. Any Controls that
	 * are not visible will not be drawn, cannot be interacted with,
	 * and will cause their children to not be drawn (if they have any).
	 *
	 * @param visible Whether or not this Control should be visible.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
		markDirty();
	}

	public boolean isVisible() {
		return this.visible;
	}

	public void setTooltip(Control tooltip) {
		this.tooltip = tooltip;
	}

	public Control getTooltip() {
		return tooltip;
	}

	public void setIsTooltip(boolean isTooltip) {
		this.isTooltip = isTooltip;
	}

	public boolean isTooltip() {
		return isTooltip;
	}

	/**
	 * Sets a function to run every time this Control is ticked.
	 *
	 * @param onTick the function to run.
	 */
	public void onTick(@NotNull Action onTick) {
		this.onTick = onTick;
	}

	public Vector2 getTruePosition() {
		return new Vector2(trueX, trueY);
	}

	protected void updateTree(ZIndexedControls zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		zIndexedControls.add(this);
		Window window = MinecraftClient.getInstance().getWindow();
		if (tooltip != null && tooltip.visible) {
			zIndexedControls.addOffset(300);
			tooltip.updateTree(zIndexedControls, 0, 0, window.getWidth(), window.getHeight());
			zIndexedControls.addOffset(-300);
		}

		if (expand && !isTooltip) {
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
		if (captured) return false;

		int x = trueArea.getX();
		int y = trueArea.getY();
		int width = trueArea.getWidth();
		int height = trueArea.getHeight();

		captured = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseX <= y + height;

		if (captured && tooltip != null) {
			tooltip.visible = true;
			tooltip.setOffset(mouseX, mouseY);
		} else if (tooltip != null){
			tooltip.visible = false;
		}

		return captured;
	}

	// Update current
	protected void prepare() {
		onTick.run();
	}

	// Draw
	protected void draw(MatrixStack matrices, Theme theme) {
		theme.get(id, painterKey).draw(matrices, trueArea.getX(), trueArea.getY(), trueArea.getWidth(), trueArea.getHeight());
	}

	protected void setParent(Control parent) {
		this.parent = parent;
	}

	protected Control getParent() {
		return parent;
	}

	protected void markDirty() {
		if (parent != null) parent.markDirty();
	}

	public static class PainterKey {
		protected PainterKey() {}
	}
}
