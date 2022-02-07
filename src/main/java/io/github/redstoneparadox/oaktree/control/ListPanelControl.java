package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Rectangle;
import io.github.redstoneparadox.oaktree.math.Vector2;

import java.util.List;

public class ListPanelControl extends PanelControl {
	protected boolean horizontal = false;
	protected int displayCount = 1;
	protected int startIndex = 0;

	public ListPanelControl() {
		id = "list_panel";
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	// TODO: Display count should only be clamped during preDraw
	public void setDisplayCount(int displayCount) {
		if (displayCount < 1) this.displayCount = 1;
		else this.displayCount = Math.min(displayCount, children.size());
	}

	public int getDisplayCount() {
		return displayCount;
	}

	public void setStartIndex(int startIndex) {
		if (startIndex < 0) this.startIndex = 0;
		else this.startIndex = Math.min(startIndex, children.size() - displayCount);
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void scrollBy(int amount) {
		setStartIndex(startIndex + amount);
	}


	@Override
	protected void updateTree(List<Control> zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		for (int i = 0; i < children.size(); i++) {
			Control child = children.get(i);
			child.visible = (i >= startIndex) && (i < startIndex + displayCount);
		}

		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);
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

	@Override
	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		if (!horizontal) {
			int sectionHeight = area.getHeight() /displayCount;
			Vector2 innerDimensions = innerDimensions(area.getWidth(), sectionHeight);
			Vector2 innerPosition = innerPosition(trueX, trueY);

			for (int i = 0; i < displayCount; i += 1) {
				int entryY = innerPosition.getY() + (i * sectionHeight);

				Control child = children.get(i + startIndex);
				if (child.isVisible()) child.preDraw(gui, innerPosition.getX(), entryY, innerDimensions.getX(), innerDimensions.getY(), mouseX, mouseY);
			}
		}
		else {
			int sectionWidth = area.getWidth() /displayCount;
			Vector2 innerDimensions = innerDimensions(sectionWidth, area.getHeight());
			Vector2 innerPosition = innerPosition(trueX, trueY);

			for (int i = 0; i < displayCount; i += 1) {
				int entryX = innerPosition.getX() + (i * sectionWidth);

				Control child = children.get(i + startIndex);
				if (child.isVisible()) child.preDraw(gui, entryX, innerPosition.getY(), innerDimensions.getX(), innerDimensions.getY(), mouseX, mouseY);
			}
		}
	}

	@Override
	boolean shouldDraw(Control child) {
		int index = children.indexOf(child);
		return index >= startIndex && index < startIndex + displayCount;
	}
}
