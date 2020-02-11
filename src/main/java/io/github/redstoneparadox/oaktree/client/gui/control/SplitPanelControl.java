package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public class SplitPanelControl extends PanelControl<SplitPanelControl> {
    public float splitSize = 0.0f;
    public Distribution distribution = Distribution.HALF;
    public boolean verticalSplit = false;

    public SplitPanelControl() {
        this.id = "split_panel";
    }

    public SplitPanelControl splitSize(float splitSize) {
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
    void arrangeChildren(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        ScreenVec firstPosition = innerPosition(trueX, trueY);
        ScreenVec firstDimensions;

        ScreenVec secondPosition;
        ScreenVec secondDimension;

        if (verticalSplit) {
            firstDimensions = innerDimensions(width, splitSize);

            secondPosition = innerPosition(trueX, trueY + splitSize);
            secondDimension = innerDimensions(width, height - splitSize);
        }
        else {
            firstDimensions = innerDimensions(splitSize, height);

            secondPosition = innerPosition(trueX + splitSize, trueY);
            secondDimension = innerDimensions(width - splitSize, height);
        }

        if (distribution == Distribution.HALF) {
            float half = children.size()/2.0f;
            for (int i = 0; i < children.size(); i += 1) {
                Control child = children.get(i);
                if (child != null) {
                    if (i < half) child.preDraw(mouseX, mouseY, deltaTime, gui, firstPosition.x, firstPosition.y, firstDimensions.x, firstDimensions.y);
                    else child.preDraw(mouseX, mouseY, deltaTime, gui, secondPosition.x, secondPosition.y, secondDimension.x, secondDimension.y);
                }
            }
        }
        else if (distribution == Distribution.EVERY_OTHER) {
            boolean second = false;
            for (Control child: children) {
                if (child != null) {
                    if (second) child.preDraw(mouseX, mouseY, deltaTime, gui, secondPosition.x, secondPosition.y, secondDimension.x, secondDimension.y);
                    else child.preDraw(mouseX, mouseY, deltaTime, gui, firstPosition.x, firstPosition.y, firstDimensions.x, firstDimensions.y);
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
