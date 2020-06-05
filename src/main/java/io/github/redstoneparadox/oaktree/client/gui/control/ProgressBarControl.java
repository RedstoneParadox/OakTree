package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.geometry.Direction2D;
import io.github.redstoneparadox.oaktree.client.geometry.ScreenPos;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.ControlStyle;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A node representing a percent-based progress bar.
 */
public class ProgressBarControl extends Control<ProgressBarControl> {
	protected float percent = 100.0f;
	protected int barWidth = 1;
	protected int barHeight = 1;
	protected Direction2D direction = Direction2D.RIGHT;

	private ControlStyle barStyle = null;

	public ProgressBarControl() {
		this.id = "progress_bar";
	}

	/**
	 * Sets the {@link ControlStyle} for the progress bar.
	 *
	 * @param style The {@link ControlStyle} to draw.
	 * @return The node itself.
	 */
	public ProgressBarControl barStyle(ControlStyle style) {
		internalTheme.add("bar", "bar", style);
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

	public float getPercent() {
		return percent;
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

	public ScreenPos getBarSize() {
		return new ScreenPos(barWidth, barHeight);
	}

	/**
	 * Sets the {@link Direction2D} for the bar to be
	 * drawn in. A values of {@link Direction2D#DOWN}
	 * means that the progress bar will be drawn
	 * downwards.
	 *
	 * @param direction The direction to face.
	 * @return The node itself.
	 */
	public ProgressBarControl drawDirection(Direction2D direction) {
		this.direction = direction;
		return this;
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);
		barStyle = getStyle(gui.getTheme(), "bar");
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);

		int barX = trueX + ((area.width/2) - (barWidth/2));
		int barY = trueY + ((area.height/2) - (barHeight/2));

		switch (direction) {
			case UP:
				int drawHeight = (int) (barHeight * (this.percent/100.0f));
				barStyle.draw(barX, barY + (barHeight - drawHeight), barWidth, drawHeight, gui);
				break;
			case DOWN:
				barStyle.draw(barX, barY, barWidth, (int) (barHeight * (this.percent/100.0f)), gui);
				break;
			case LEFT:
				int drawWidth = (int) (barWidth * (this.percent/100.0f));
				barStyle.draw(barX - drawWidth, barY, drawWidth, barHeight, gui);
				break;
			case RIGHT:
				barStyle.draw(barX, barY, (int) (barWidth * (this.percent/100.0f)), barHeight, gui);
				break;
		}
	}
}
