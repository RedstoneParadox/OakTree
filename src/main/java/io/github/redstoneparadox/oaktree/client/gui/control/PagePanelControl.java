package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.geometry.Vector2D;

public class PagePanelControl extends PanelControl<PagePanelControl> {
    public int page = 0;

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

    @Override
    void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
        Control<?> child = children.get(page);
        Vector2D innerPosition = innerPosition(trueX, trueY);
        Vector2D innerDimensions = innerDimensions(area.width, area.height);

        if (child != null) child.preDraw(gui, innerPosition.x, innerPosition.y, innerDimensions.x, innerDimensions.y, mouseX, mouseY);
    }

    @Override
    boolean shouldDraw(Control<?> child) {
        return children.indexOf(child) == page;
    }
}
