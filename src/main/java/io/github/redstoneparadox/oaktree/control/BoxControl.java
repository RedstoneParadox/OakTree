package io.github.redstoneparadox.oaktree.control;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * BoxControl is a type of {@link PaddingControl}
 * that can have a single child {@link Control}.
 * <p>
 * While {@link PanelControl} could also be used,
 * this class is specialized for dealing with a
 * single child.
 *
 * @deprecated Use {@link  PanelControl}
 */
@Deprecated
public class BoxControl extends PaddingControl {
	protected  @NotNull Control child = new Control();

	public BoxControl() {
		this.name = "box";
	}

	/**
	 * Sets the child of this control. The child
	 * will be drawn within the internal
	 * padding.
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
	protected void cleanup() {
		super.cleanup();
		child.cleanup();
	}

	@Override
	protected void updateTree(List<Control> orderedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(orderedControls, containerX, containerY, containerWidth, containerHeight);
		if (!child.visible) return;

		int innerX = trueArea.getX() + leftPadding;
		int innerY = trueArea.getY() + topPadding;
		int innerWidth = trueArea.getWidth() + leftPadding + rightPadding;
		int innerHeight = trueArea.getHeight() + topPadding + bottomPadding;

		child.updateTree(orderedControls, innerX, innerY, innerWidth, innerHeight);
	}
}
