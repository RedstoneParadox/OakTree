package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Rectangle;

import java.util.List;

/**
 * A {@link PanelControl} that arranges its children
 * as a vertical or horizontal list. It can be set
 * to only display some of its children at once
 * and can be scrolled.
 */
public class ListPanelControl extends PanelControl {
	protected boolean horizontal = false;
	protected int displayCount = 1;
	protected int startIndex = 0;

	public ListPanelControl() {
		id = "list_panel";
	}

	/**
	 * Sets whether this should display its children
	 * horizontally instead of vertically.
	 *
	 * @param horizontal The value to set.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * Sets how many children should be displayed
	 * at once.
	 *
	 * @param displayCount The count to set.
	 */
	public void setDisplayCount(int displayCount) {
		// TODO: Display count should only be clamped during prepare
		if (displayCount < 1) this.displayCount = 1;
		else this.displayCount = Math.min(displayCount, children.size());
		markDirty();
	}

	public int getDisplayCount() {
		return displayCount;
	}

	/**
	 * Sets the index of the first child to be
	 * displayed.
	 *
	 * @param startIndex The index of the child
	 */
	public void setStartIndex(int startIndex) {
		if (startIndex < 0) this.startIndex = 0;
		else this.startIndex = Math.min(startIndex, children.size() - displayCount);
		markDirty();
	}

	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * Scrolls the list by a certain amount
	 *
	 * @param amount The amount to scroll by
	 */
	public void scrollBy(int amount) {
		setStartIndex(startIndex + amount);
	}

	@Override
	protected void updateTree(List<Control> orderedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		for (int i = 0; i < children.size(); i++) {
			Control child = children.get(i);
			child.visible = (i >= startIndex) && (i < startIndex + displayCount);
		}

		super.updateTree(orderedControls, containerX, containerY, containerWidth, containerHeight);
	}

	@Override
	protected Rectangle getChildArea(int index) {
		if (index < startIndex || index >= startIndex + displayCount) {
			return Rectangle.DEFAULT;
		}

		int i = index - startIndex;
		int innerX;
		int innerY;
		int innerWidth;
		int innerHeight;

		if (horizontal) {
			int divisionWidth = trueArea.getWidth()/children.size();

			innerX = trueArea.getX() + leftPadding + divisionWidth * i;
			innerY = trueArea.getY() + topPadding;
			innerWidth = divisionWidth - leftPadding - rightPadding;
			innerHeight = trueArea.getHeight() - topPadding - bottomPadding;
		} else {
			int divisionHeight = trueArea.getHeight()/children.size();

			innerX = trueArea.getX() + leftPadding;
			innerY = trueArea.getY() + topPadding + divisionHeight * i;
			innerWidth = trueArea.getWidth() - leftPadding - rightPadding;
			innerHeight = divisionHeight - topPadding - bottomPadding;
		}

		return new Rectangle(innerX, innerY, innerWidth, innerHeight);
	}
}
