package io.github.redstoneparadox.oaktree.control;

import java.util.List;

/**
 * A panel which organizes its children into
 * pages; each child has its own page.
 */
public class PagePanelControl extends PanelControl {
	protected int page = 0;

	public PagePanelControl() {
		this.name = "page_panel";
	}

	/**
	 * Sets the page to be displayed.
	 *
	 * @param page The page
	 */
	public void setPage(int page) {
		if (page < 0) this.page = 0;
		else if (page >= children.size()) this.page = children.size() - 1;
		else this.page = page;
		markDirty();
	}

	/**
	 * Advances to the next page.
	 */
	public void toNextPage() {
		setPage(page + 1);
	}

	/**
	 * Moves to the previous page.
	 */
	public void toPreviousPage() {
		setPage(page - 1);
	}

	/**
	 * @return The current page.
	 */
	public int getPage() {
		return page;
	}

	@Override
	protected void updateTree(List<Control> orderedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		for (int i = 0; i < children.size(); i++) {
			Control child = children.get(i);
			child.visible = i == page;
		}
		
		super.updateTree(orderedControls, containerX, containerY, containerWidth, containerHeight);
	}
}
