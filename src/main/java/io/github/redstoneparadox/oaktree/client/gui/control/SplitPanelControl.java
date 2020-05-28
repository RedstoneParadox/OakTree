package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public class SplitPanelControl extends PanelControl<SplitPanelControl> {
    public int splitSize = 0;
    public Distribution distribution = Distribution.HALF;
    public boolean verticalSplit = false;

    public SplitPanelControl() {
        this.id = "split_panel";
    }

    public SplitPanelControl splitSize(int splitSize) {
        this.splitSize = splitSize;
        return this;
    }

    public SplitPanelControl half() {
        this.distribution = Distribution.HALF;
        return this;
    }

    public SplitPanelControl everyOther() {
        this.distribution = Distribution.EVERY_OTHER;
        return this;
    }

    public SplitPanelControl verticalSplit(boolean verticalSplit) {
        this.verticalSplit = verticalSplit;
        return this;
    }

    @Override
    void arrangeChildren(int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        ScreenVec firstPosition = innerPosition(trueX, trueY);
        ScreenVec firstDimensions;

        ScreenVec secondPosition;
        ScreenVec secondDimension;

        if (verticalSplit) {
            firstDimensions = innerDimensions(area.width, splitSize);

            secondPosition = innerPosition(trueX, trueY + splitSize);
            secondDimension = innerDimensions(area.width, area.height - splitSize);
        }
        else {
            firstDimensions = innerDimensions(splitSize, area.height);

            secondPosition = innerPosition(trueX + splitSize, trueY);
            secondDimension = innerDimensions(area.width - splitSize, area.height);
        }

        if (distribution == Distribution.HALF) {
            float half = children.size()/2.0f;
            for (int i = 0; i < children.size(); i += 1) {
                Control child = children.get(i);
                if (child != null) {
                    if (i < half) child.preDraw(mouseX, mouseY, deltaTime, gui, firstPosition.x, firstPosition.y, firstDimensions.x, firstDimensions.y, controlList);
                    else child.preDraw(mouseX, mouseY, deltaTime, gui, secondPosition.x, secondPosition.y, secondDimension.x, secondDimension.y, controlList);
                }
            }
        }
        else if (distribution == Distribution.EVERY_OTHER) {
            boolean second = false;
            for (Control child: children) {
                if (child != null) {
                    if (second) child.preDraw(mouseX, mouseY, deltaTime, gui, secondPosition.x, secondPosition.y, secondDimension.x, secondDimension.y, controlList);
                    else child.preDraw(mouseX, mouseY, deltaTime, gui, firstPosition.x, firstPosition.y, firstDimensions.x, firstDimensions.y, controlList);
                }
                second = !second;
            }
        }
    }

    public enum Distribution {
        HALF,
        EVERY_OTHER
    }
}
