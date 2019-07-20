package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeDirection;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public class ProgressBarNode extends Node {

    StyleBox barStyle = null;
    StyleBox unfilledBarStyle = null;

    float percentFilled = 100.0f;

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
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.draw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        ScreenVec anchorOffset = anchorAlignment.getOffset(containerWidth, containerHeight);
        ScreenVec drawOffset = drawAlignment.getOffset(width, height);

        float trueX = x + anchorOffset.x + offsetX - drawOffset.x;
        float trueY = y + anchorOffset.y + offsetY - drawOffset.y;

        float centerX = trueX + (width/2);
        float centerY = trueY + (height/2);

        float barX = centerX - (barWidth/2);
        float barY = centerY - (barHeight/2);

        if (unfilledBarStyle != null) {
            unfilledBarStyle.draw(barX, barY, barWidth, barHeight, gui, false);
        }

        switch (barDirection) {
            case UP:
                barStyle.draw(barX, barY, barWidth, barHeight * (percentFilled/100.0f), gui, true);
            case DOWN:
                barStyle.draw(barX, barY, barWidth, barHeight * (percentFilled/100.0f), gui, false);
            case LEFT:
                barStyle.draw(barX, barY, barWidth * (percentFilled/100.0f), barHeight, gui, true);
            case RIGHT:
                barStyle.draw(barX, barY, barWidth * (percentFilled/100.0f), barHeight, gui, false);
        }
    }
}
