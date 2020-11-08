package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Vector2;
import org.jetbrains.annotations.ApiStatus;

public class ListPanelControl extends PanelControl<ListPanelControl> {
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

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ListPanelControl horizontal(boolean horizontal) {
		this.horizontal = horizontal;
		return this;
	}

	// TODO: Display count should only be clamped during preDraw
	public void setDisplayCount(int displayCount) {
		if (displayCount < 1) this.displayCount = 1;
		else this.displayCount = Math.min(displayCount, children.size());
	}

	public int getDisplayCount() {
		return displayCount;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ListPanelControl displayCount(int displayCount) {
		if (displayCount < 1) this.displayCount = 1;
		else this.displayCount = Math.min(displayCount, children.size());
		return this;
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

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ListPanelControl startIndex(int currentIndex) {
		if (currentIndex < 0) this.startIndex = 0;
		else this.startIndex = Math.min(currentIndex, children.size() - displayCount);
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public ListPanelControl scroll(int amount) {
		return startIndex(startIndex + amount);
	}

	@Override
	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		if (!horizontal) {
			int sectionHeight = area.height/displayCount;
			Vector2 innerDimensions = innerDimensions(area.width, sectionHeight);
			Vector2 innerPosition = innerPosition(trueX, trueY);

			for (int i = 0; i < displayCount; i += 1) {
				int entryY = innerPosition.y + (i * sectionHeight);

				Control<?> child = children.get(i + startIndex);
				if (child.isVisible()) child.preDraw(gui, innerPosition.x, entryY, innerDimensions.x, innerDimensions.y, mouseX, mouseY);
			}
		}
		else {
			int sectionWidth = area.width/displayCount;
			Vector2 innerDimensions = innerDimensions(sectionWidth, area.height);
			Vector2 innerPosition = innerPosition(trueX, trueY);

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
