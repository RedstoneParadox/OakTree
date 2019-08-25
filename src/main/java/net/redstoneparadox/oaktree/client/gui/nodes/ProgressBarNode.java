package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeDirection;

public class ProgressBarNode extends Node<ProgressBarNode> {

    StyleBox barStyle = null;
    StyleBox unfilledBarStyle = null;

    public float percentFilled = 100.0f;

    float barWidth = 0.1f;
    float barHeight = 0.1f;

    NodeDirection barDirection = NodeDirection.RIGHT;

    public ProgressBarNode setBarStyle(StyleBox value) {
        barStyle = value;
        return this;
    }

    public ProgressBarNode setUnfilledBarStyle(StyleBox value) {
        unfilledBarStyle = value;
        return this;
    }

    public ProgressBarNode setPercent(float value) {
        percentFilled = value;
        return this;
    }

    public ProgressBarNode setBarSize(float width, float height) {
        barWidth = width;
        barHeight = height;
        return this;
    }

    public ProgressBarNode setBarDirection(NodeDirection value) {
        barDirection = value;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        super.draw(mouseX, mouseY, deltaTime, gui);

        float barX = trueX + ((trueWidth/2) - (barWidth/2));
        float barY = trueY + ((trueHeight/2) - (barHeight/2));

        if (unfilledBarStyle != null) {
            unfilledBarStyle.draw(barX, barY, barWidth, barHeight, gui);
        }

        switch (barDirection) {
            case UP:
                barStyle.draw(barX, barY + barHeight, barWidth, barHeight * (percentFilled/100.0f), gui, false, true);
                break;
            case DOWN:
                barStyle.draw(barX, barY, barWidth, barHeight * (percentFilled/100.0f), gui);
                break;
            case LEFT:
                barStyle.draw(barX + barWidth, barY, barWidth * (percentFilled/100.0f), barHeight, gui, true, false);
                break;
            case RIGHT:
                barStyle.draw(barX, barY, barWidth * (percentFilled/100.0f), barHeight, gui);
                break;
        }
    }
}
