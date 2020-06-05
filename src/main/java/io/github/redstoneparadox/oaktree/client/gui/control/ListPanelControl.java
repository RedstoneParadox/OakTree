package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.geometry.ScreenPos;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;

public class ListPanelControl extends PanelControl<ListPanelControl> {
	protected boolean horizontal = false;
	protected int displayCount = 1;
	protected int startIndex = 0;

	public ListPanelControl() {
		id = "list_panel";
	}

	public ListPanelControl horizontal(boolean horizontal) {
		this.horizontal = horizontal;
		return this;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	// TODO: Display count should only be clamped during preDraw
	public ListPanelControl displayCount(int displayCount) {
		if (displayCount < 1) this.displayCount = 1;
		else this.displayCount = Math.min(displayCount, children.size());
		return this;
	}

	public int getDisplayCount() {
		return displayCount;
	}

	public ListPanelControl startIndex(int currentIndex) {
		if (currentIndex < 0) this.startIndex = 0;
		else this.startIndex = Math.min(currentIndex, children.size() - displayCount);
		return this;
	}

	public ListPanelControl scroll(int amount) {
		return startIndex(startIndex + amount);
	}

	protected int getStartIndex() {
		return startIndex;
	}

	@Override
	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		if (!horizontal) {
			int sectionHeight = area.height/displayCount;
			ScreenPos innerDimensions = innerDimensions(area.width, sectionHeight);
			ScreenPos innerPosition = innerPosition(trueX, trueY);

			for (int i = 0; i < displayCount; i += 1) {
				int entryY = innerPosition.y + (i * sectionHeight);

				Control<?> child = children.get(i + startIndex);
				if (child.isVisible()) child.preDraw(gui, innerPosition.x, entryY, innerDimensions.x, innerDimensions.y, mouseX, mouseY);
			}
		}
		else {
			int sectionWidth = area.width/displayCount;
			ScreenPos innerDimensions = innerDimensions(sectionWidth, area.height);
			ScreenPos innerPosition = innerPosition(trueX, trueY);

			for (int i = 0; i < displayCount; i += 1) {
				int entryX = innerPosition.x + (i * sectionWidth);

				Control<?> child = children.get(i + startIndex);
				if (child.isVisible()) child.preDraw(gui, entryX, innerPosition.y, innerDimensions.x, innerDimensions.y, mouseX, mouseY);
			}
		}
	}

	@Override
	boolean shouldDraw(Control<?> child) {
		int index = children.indexOf(child);
		return index >= startIndex && index < startIndex + displayCount;
	}
}
