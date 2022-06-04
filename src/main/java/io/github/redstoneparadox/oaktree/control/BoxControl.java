package io.github.redstoneparadox.oaktree.control;

import org.jetbrains.annotations.NotNull;

/**
 * BoxControl is a type of {@link PaddingControl}
 * that can have a single child {@link Control}.
 *
 * While {@link PanelControl} could also be used,
 * this class is specialized for dealing with a
 * single child.
 */
public class BoxControl extends PaddingControl {
	protected  @NotNull Control child = new Control();

	public BoxControl() {
		this.id = "box";
	}

	/**
	 * Sets the child of this control. The child
	 * will be drawn within the internal
	 * margins.
	 *
	 * @param child The {@link } that is being
	 *           	added as a child.
	 */
	public void setChild(@NotNull Control child) {
		this.child = child;
		child.setParent(this);
		markDirty();
	}

	public @NotNull Control getChild() {
		return child;
	}

	@Override
	protected void updateTree(RootPanelControl.ZIndexedControls zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);
		if (!child.visible) return;

		int innerX = trueArea.getX() + leftPadding;
		int innerY = trueArea.getY() + topPadding;
		int innerWidth = trueArea.getWidth() + leftPadding + rightPadding;
		int innerHeight = trueArea.getHeight() + topPadding + bottomPadding;

		child.updateTree(zIndexedControls, innerX, innerY, innerWidth, innerHeight);
	}
}
