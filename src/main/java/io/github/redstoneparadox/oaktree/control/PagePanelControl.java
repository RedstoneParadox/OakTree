package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Vector2;

public class PagePanelControl extends PanelControl {
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

	@Override
	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		Control child = children.get(page);
		Vector2 innerPosition = innerPosition(trueX, trueY);
		Vector2 innerDimensions = innerDimensions(area.getWidth(), area.getHeight());

		if (child.isVisible()) child.preDraw(gui, innerPosition.getX(), innerPosition.getY(), innerDimensions.getX(), innerDimensions.getY(), mouseX, mouseY);
	}

	@Override
	boolean shouldDraw(Control child) {
		return children.indexOf(child) == page;
	}
}
