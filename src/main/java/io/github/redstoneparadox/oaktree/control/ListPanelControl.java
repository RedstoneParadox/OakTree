package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Vector2;

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
	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		if (!horizontal) {
			int sectionHeight = oldArea.height/displayCount;
			Vector2 innerDimensions = innerDimensions(oldArea.width, sectionHeight);
			Vector2 innerPosition = innerPosition(trueX, trueY);

			for (int i = 0; i < displayCount; i += 1) {
				int entryY = innerPosition.getY() + (i * sectionHeight);

				Control child = children.get(i + startIndex);
				if (child.isVisible()) child.preDraw(gui, innerPosition.getX(), entryY, innerDimensions.getX(), innerDimensions.getY(), mouseX, mouseY);
			}
		}
		else {
			int sectionWidth = oldArea.width/displayCount;
			Vector2 innerDimensions = innerDimensions(sectionWidth, oldArea.height);
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
