package io.github.redstoneparadox.oaktree.control;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A control that can display two children
 * on either side of a horizontal or vertical
 * split. The first child is displayed to the
 * left or on the top and the second child is
 * displayed to the right or on the bottom.
 */
public class SplitControl extends Control {
	protected int splitPosition = 0;
	protected boolean vertical = false;
	protected @NotNull Control first = new Control();
	protected @NotNull Control second = new Control();

	public SplitControl() {
		this.id = "split";
	}

	/**
	 * Sets how far from the right or top
	 * the split should be.
	 *
	 * @param splitPosition The position.
	 */
	public void setSplitPosition(int splitPosition) {
		this.splitPosition = splitPosition;
	}

	public int getSplitPosition() {
		return splitPosition;
	}

	/**
	 * Sets whether this control is oriented
	 * horizontally or vertically.
	 *
	 * @param vertical The orientation.
	 */
	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public boolean isVertical() {
		return vertical;
	}

	/**
	 * Sets the first child, which is drawn
	 * either in the left or top half
	 * depending on the orientation.
	 *
	 * @param first The first child.
	 */
	public void setFirst(@NotNull Control first) {
		this.first = first;
		first.setParent(this);
		markDirty();
	}

	public @NotNull Control getFirst() {
		return first;
	}

	/**
	 * Sets the second child, which is
	 * drawn either in the right or bottom
	 * half depending on the orientation.
	 *
	 * @param second The second child.
	 */
	public void setSecond(@NotNull Control second) {
		this.second = second;
		second.setParent(this);
		markDirty();
	}

	public @NotNull Control getSecond() {
		return second;
	}

	@Override
	protected void updateTree(List<Control> orderedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(orderedControls, containerX, containerY, containerWidth, containerHeight);

		if (vertical) {
			int bottomY = trueArea.getY() + splitPosition;
			int topHeight = trueArea.getHeight() - splitPosition;

			if (first.visible) first.updateTree(orderedControls, trueArea.getX(), trueArea.getY(), trueArea.getWidth(), topHeight);
			if (second.visible) second.updateTree(orderedControls, trueArea.getX(), bottomY, trueArea.getWidth(), splitPosition);
		} else {
			int rightX = trueArea.getX() + splitPosition;
			int leftWidth = trueArea.getWidth() - splitPosition;

			if (first.visible) first.updateTree(orderedControls, trueArea.getX(), trueArea.getY(), leftWidth, trueArea.getHeight());
			if (second.visible) second.updateTree(orderedControls, rightX, trueArea.getY(), splitPosition, trueArea.getHeight());
		}
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		first.cleanup();
		second.cleanup();
	}
}
