package io.github.redstoneparadox.oaktree.client.gui.control;

public class ListPanelControl extends PanelControl<ListPanelControl> {
    public boolean horizontal = false;
    public int displayCount = 1;
    public int currentIndex = 0;

    public ListPanelControl() {
        id = "list_panel";
    }

    public ListPanelControl horizontal(boolean horizontal) {
        this.horizontal = horizontal;
        return this;
    }

    public ListPanelControl displayCount(int displayCount) {
        if (displayCount < 1) this.displayCount = 1;
        else this.displayCount = Math.min(displayCount, children.size());
        return this;
    }

    public ListPanelControl currentIndex(int currentIndex) {
        if (currentIndex < 0) this.currentIndex = 0;
        else this.currentIndex = Math.min(currentIndex, children.size() - displayCount);
        return this;
    }

    public ListPanelControl scroll(int amount) {
        currentIndex(currentIndex + amount);
        return this;
    }
}
