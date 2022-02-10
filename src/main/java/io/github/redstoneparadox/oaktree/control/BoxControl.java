package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Vector2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * BoxControl is a type of PaddingControl that
 * can have a single child node.
 *
 * While {@link PanelControl} could also be used,
 * BoxControl is specialized for dealing with a
 * single child.
 */
public class BoxControl extends PaddingControl {
	protected  @NotNull Control child = new Control();

	public BoxControl() {
		this.id = "box";
	}

	/**
	 * Sets the child node of this node. The child node
	 * will be drawn relative to this node within the box
	 * created by the internal margins.
	 *
	 * @param child The node that is being added as a child.
	 */
	public void setChild(@NotNull Control child) {
		this.child = child;
	}

	public @NotNull Control getChild() {
		return child;
	}

	@Override
	protected void updateTree(List<Control> zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);
		if (!child.visible) return;

		int innerX = trueArea.getX() + leftPadding;
		int innerY = trueArea.getY() + topPadding;
		int innerWidth = trueArea.getWidth() + leftPadding + rightPadding;
		int innerHeight = trueArea.getHeight() + topPadding + bottomPadding;

		child.updateTree(zIndexedControls, innerX, innerY, innerWidth, innerHeight);
	}
}
