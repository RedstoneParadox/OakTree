package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;

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
        float firstWidth;
        float firstHeight;

        float secondX;
        float secondY;
        float secondWidth;
        float secondHeight;

        if (verticalSplit) {
            firstWidth = innerWidth;
            firstHeight = splitSize;

            secondX = innerX;
            secondY = innerY + splitSize;
            secondWidth = innerWidth;
            secondHeight = innerHeight - splitSize;
        }
        else  {
            firstWidth = splitSize;
            firstHeight = innerHeight;

            secondX = innerX + splitSize;
            secondY = innerY;
            secondWidth = innerWidth - splitSize;
            secondHeight = innerHeight;
        }

        if (distribution == Distribution.HALF) {
            float half = children.size()/2.0f;
            for (int i = 0; i < children.size(); i += 1) {
                Control child = children.get(i);
                if (child != null) {
                    if (i < half) child.preDraw(mouseX, mouseY, deltaTime, gui, innerX, innerY, firstWidth, firstHeight);
                    else child.preDraw(mouseX, mouseY, deltaTime, gui, secondX, secondY, secondWidth, secondHeight);
                }
            }
        }
        else if (distribution == Distribution.EVERY_OTHER) {
            boolean second = false;
            for (Control child: children) {
                if (child != null) {
                    if (second) child.preDraw(mouseX, mouseY, deltaTime, gui, secondX, secondY, secondWidth, secondHeight);
                    else child.preDraw(mouseX, mouseY, deltaTime, gui, innerX, innerY, firstWidth, firstHeight);
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
