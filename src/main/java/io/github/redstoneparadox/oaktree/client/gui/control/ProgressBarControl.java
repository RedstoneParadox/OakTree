package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlDirection;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A node representing a percent-based progress bar.
 */
public class ProgressBarControl extends Control<ProgressBarControl> {

    StyleBox barStyle = null;

    public float percent = 100.0f;

    int barWidth = 1;
    int barHeight = 1;

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
    public ProgressBarControl barSize(int width, int height) {
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
    public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        if (!visible) return;
        super.draw(matrices, mouseX, mouseY, deltaTime, gui);

        int barX = trueX + ((area.width/2) - (barWidth/2));
        int barY = trueY + ((area.height/2) - (barHeight/2));

        switch (direction) {
            case UP:
                barStyle.draw(barX, barY + barHeight, barWidth, (int) (barHeight * (this.percent/100.0f)), gui, false, true);
                break;
            case DOWN:
                barStyle.draw(barX, barY, barWidth, (int) (barHeight * (this.percent/100.0f)), gui);
                break;
            case LEFT:
                barStyle.draw(barX + barWidth, barY, (int) (barWidth * (this.percent/100.0f)), barHeight, gui, true, false);
                break;
            case RIGHT:
                barStyle.draw(barX, barY, (int) (barWidth * (this.percent/100.0f)), barHeight, gui);
                break;
        }
    }
}
