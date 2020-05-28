package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;

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
    void arrangeChildren(int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        if (!horizontal) {
            int sectionHeight = area.height/displayCount;
            ScreenVec innerDimensions = innerDimensions(area.width, sectionHeight);
            ScreenVec innerPosition = innerPosition(trueX, trueY);

            for (int i = 0; i < displayCount; i += 1) {
                int entryY = innerPosition.y + (i * sectionHeight);

                Control child = children.get(i + startIndex);
                if (child != null) child.preDraw(mouseX, mouseY, deltaTime, gui, innerPosition.x, entryY, innerDimensions.x, innerDimensions.y, controlList);
            }
        }
        else {
            int sectionWidth = area.width/displayCount;
            ScreenVec innerDimensions = innerDimensions(sectionWidth, area.height);
            ScreenVec innerPosition = innerPosition(trueX, trueY);

            for (int i = 0; i < displayCount; i += 1) {
                int entryX = innerPosition.x + (i * sectionWidth);

                Control child = children.get(i + startIndex);
                if (child != null) child.preDraw(mouseX, mouseY, deltaTime, gui, entryX, innerPosition.y, innerDimensions.x, innerDimensions.y, controlList);
            }
        }
    }

    @Override
    boolean shouldDraw(Control child) {
        int index = children.indexOf(child);
        return index >= startIndex && index < startIndex + displayCount;
    }
}
