package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.geometry.Vector2D;

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
    void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
        Vector2D firstPosition = innerPosition(trueX, trueY);
        Vector2D firstDimensions;

        Vector2D secondPosition;
        Vector2D secondDimension;

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
                Control<?> child = children.get(i);
                if (child != null) {
                    if (i < half) child.preDraw(gui, firstPosition.x, firstPosition.y, firstDimensions.x, firstDimensions.y, mouseX, mouseY);
                    else child.preDraw(gui, secondPosition.x, secondPosition.y, secondDimension.x, secondDimension.y, mouseX, mouseY);
                }
            }
        }
        else if (distribution == Distribution.EVERY_OTHER) {
            boolean second = false;
            for (Control<?> child: children) {
                if (child != null) {
                    if (second) child.preDraw(gui, secondPosition.x, secondPosition.y, secondDimension.x, secondDimension.y, mouseX, mouseY);
                    else child.preDraw(gui, firstPosition.x, firstPosition.y, firstDimensions.x, firstDimensions.y, mouseX, mouseY);
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
