package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.geometry.ScreenPos;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;

public class PagePanelControl extends PanelControl<PagePanelControl> {
	protected int page = 0;

	public PagePanelControl() {
		this.id = "page_panel";
	}

	public PagePanelControl page(int page) {
		if (page < 0) this.page = 0;
		else if (page >= children.size()) this.page = children.size() - 1;
		else this.page = page;

		return this;
	}

	public PagePanelControl nextPage() {
		page(page + 1);
		return this;
	}

	public PagePanelControl previousPage() {
		page(page - 1);
		return this;
	}

	public PagePanelControl flipPages(int count) {
		page(page + count);
		return this;
	}

	public int getPage() {
		return page;
	}

	@Override
	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		Control<?> child = children.get(page);
		ScreenPos innerPosition = innerPosition(trueX, trueY);
		ScreenPos innerDimensions = innerDimensions(area.width, area.height);

		if (child.isVisible()) child.preDraw(gui, innerPosition.x, innerPosition.y, innerDimensions.x, innerDimensions.y, mouseX, mouseY);
	}

	@Override
	boolean shouldDraw(Control<?> child) {
		return children.indexOf(child) == page;
	}
}
