package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Vector2;
import org.jetbrains.annotations.ApiStatus;

public class PagePanelControl extends PanelControl<PagePanelControl> {
	protected int page = 0;

	public PagePanelControl() {
		this.id = "page_panel";
	}

	public void setPage(int page) {
		if (page < 0) this.page = 0;
		else if (page >= children.size()) this.page = children.size() - 1;
		else this.page = page;
	}

	public void toNextPage() {
		setPage(page + 1);
	}

	public void toPreviousPage() {
		setPage(page - 1);
	}

	public int getPage() {
		return page;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public PagePanelControl page(int page) {
		if (page < 0) this.page = 0;
		else if (page >= children.size()) this.page = children.size() - 1;
		else this.page = page;

		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public PagePanelControl nextPage() {
		page(page + 1);
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public PagePanelControl previousPage() {
		page(page - 1);
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public PagePanelControl flipPages(int count) {
		page(page + count);
		return this;
	}

	@Override
	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		Control<?> child = children.get(page);
		Vector2 innerPosition = innerPosition(trueX, trueY);
		Vector2 innerDimensions = innerDimensions(area.width, area.height);

		if (child.isVisible()) child.preDraw(gui, innerPosition.x, innerPosition.y, innerDimensions.x, innerDimensions.y, mouseX, mouseY);
	}

	@Override
	boolean shouldDraw(Control<?> child) {
		return children.indexOf(child) == page;
	}
}
