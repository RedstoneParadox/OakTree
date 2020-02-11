package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public class ListPanelControl extends PanelControl<ListPanelControl> {
    public boolean horizontal = false;
    public int displayCount = 1;
    public int startIndex = 0;

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

    public ListPanelControl startIndex(int currentIndex) {
        if (currentIndex < 0) this.startIndex = 0;
        else this.startIndex = Math.min(currentIndex, children.size() - displayCount);
        return this;
    }

    public ListPanelControl scroll(int amount) {
        return startIndex(startIndex + amount);
    }

    @Override
    void arrangeChildren(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!horizontal) {
            float entryHeight = innerHeight/displayCount;

            for (int i = 0; i < displayCount; i += 1) {
                float entryY = trueY + (i * entryHeight);

                Control child = children.get(i + startIndex);
                if (child != null) child.preDraw(mouseX, mouseY, deltaTime, gui, innerX, entryY, innerWidth, entryHeight);
            }
        }
        else {
            float entryWidth = innerWidth/displayCount;

            for (int i = 0; i < displayCount; i += 1) {
                float entryX = trueX + (i * entryWidth);

                Control child = children.get(i + startIndex);
                if (child != null) child.preDraw(mouseX, mouseY, deltaTime, gui, entryX, innerY, entryWidth, innerHeight);
            }
        }
    }

    @Override
    boolean shouldDraw(Control child) {
        int index = children.indexOf(child);
        return index >= startIndex && index < startIndex + displayCount;
    }
}
