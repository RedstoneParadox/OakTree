package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlDirection;

/**
 * A node representing a percent-based progress bar.
 */
public class ProgressBarControl extends Control<ProgressBarControl> {

    StyleBox barStyle = null;

    public float percent = 100.0f;

    float barWidth = 0.1f;
    float barHeight = 0.1f;

    ControlDirection direction = ControlDirection.RIGHT;

    public ProgressBarControl() {
        this.id = "progress_bar";
    }

    /**
     * Sets the {@link StyleBox} for the progress bar.
     *
     * @param style The {@link StyleBox} to draw.
     * @return The node itself.
     */
    public ProgressBarControl barStyle(StyleBox style) {
        barStyle = style;
        return this;
    }

    /**
     * Sets the percentage of the progress bar.
     *
     * @param percent The percentage.
     * @return The node itself.
     */
    public ProgressBarControl percent(float percent) {
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
    public ProgressBarControl barSize(float width, float height) {
        barWidth = width;
        barHeight = height;
        return this;
    }

    /**
     * Sets the {@link ControlDirection} for the bar to be
     * drawn in. A values of {@link ControlDirection#DOWN}
     * means that the progress bar will be drawn
     * downwards.
     *
     * @param direction The direction to face.
     * @return The node itself.
     */
    public ProgressBarControl drawDirection(ControlDirection direction) {
        this.direction = direction;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);

        float barX = trueX + ((width/2) - (barWidth/2));
        float barY = trueY + ((height/2) - (barHeight/2));

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
