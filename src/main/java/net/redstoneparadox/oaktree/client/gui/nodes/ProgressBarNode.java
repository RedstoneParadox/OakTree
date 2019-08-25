package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeDirection;

/**
 * A node representing a percent-based progress bar.
 */
public class ProgressBarNode extends Node<ProgressBarNode> {

    StyleBox barStyle = null;

    public float percent = 100.0f;

    float barWidth = 0.1f;
    float barHeight = 0.1f;

    NodeDirection direction = NodeDirection.RIGHT;

    /**
     * Sets the {@link StyleBox} for the progress bar.
     *
     * @param value The {@link StyleBox} to draw.
     * @return The node itself.
     */
    public ProgressBarNode setBarStyle(StyleBox value) {
        barStyle = value;
        return this;
    }

    /**
     * Sets the percentage of the progress bar.
     *
     * @param percent The percentage.
     * @return The node itself.
     */
    public ProgressBarNode setPercent(float percent) {
        this.percent = percent;
        return this;
    }

    /**
     * Sets the width and height of the progress bar. It
     * is suggested to make it smaller than the node
     * itself.
     *
     * @param width The width of the progress bar.
     * @param height The height of the progress bar.
     * @return The node itself.
     */
    public ProgressBarNode setBarSize(float width, float height) {
        barWidth = width;
        barHeight = height;
        return this;
    }

    /**
     * Sets the {@link NodeDirection} for the bar to be
     * drawn in. A values of {@link NodeDirection#DOWN}
     * means that the progress bar will be drawn
     * downwards.
     *
     * @param direction The direction to face.
     * @return The node itself.
     */
    public ProgressBarNode setDirection(NodeDirection direction) {
        this.direction = direction;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        super.draw(mouseX, mouseY, deltaTime, gui);

        float barX = trueX + ((trueWidth/2) - (barWidth/2));
        float barY = trueY + ((trueHeight/2) - (barHeight/2));

        switch (direction) {
            case UP:
                barStyle.draw(barX, barY + barHeight, barWidth, barHeight * (this.percent/100.0f), gui, false, true);
                break;
            case DOWN:
                barStyle.draw(barX, barY, barWidth, barHeight * (this.percent/100.0f), gui);
                break;
            case LEFT:
                barStyle.draw(barX + barWidth, barY, barWidth * (this.percent/100.0f), barHeight, gui, true, false);
                break;
            case RIGHT:
                barStyle.draw(barX, barY, barWidth * (this.percent/100.0f), barHeight, gui);
                break;
        }
    }
}
