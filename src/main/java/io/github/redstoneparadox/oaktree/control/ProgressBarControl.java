package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Direction2D;
import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.style.ControlStyle;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A node representing a percent-based progress bar.
 */
public class ProgressBarControl extends Control {
	protected float percent = 100.0f;
	protected int barWidth = 1;
	protected int barHeight = 1;
	protected Direction2D direction = Direction2D.RIGHT;

	private ControlStyle barStyle = null;

	public ProgressBarControl() {
		this.id = "progress_bar";
	}

	/**
	 * Sets the percentage of the progress bar.
	 *
	 * @param percent The percentage.
	 */
	public void setPercent(float percent) {
		this.percent = percent;
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
	 */
	public void setBarSize(int width, int height) {
		this.barWidth = width;
		this.barHeight = height;
	}

	public Vector2 getBarSize() {
		return new Vector2(barWidth, barHeight);
	}

	/**
	 * Sets the {@link Direction2D} for the bar to be
	 * drawn in. A values of {@link Direction2D#DOWN}
	 * means that the progress bar will be drawn
	 * downwards.
	 *
	 * @param direction The direction to face.
	 */
	public void setDirection(Direction2D direction) {
		this.direction = direction;
	}

	public Direction2D getDirection() {
		return direction;
	}

	/**
	 * Sets the {@link ControlStyle} for the progress bar.
	 *
	 * @param style The {@link ControlStyle} to draw.
	 */
	public void setBarStyle(ControlStyle style) {
		internalTheme.add("self/bar", style);
	}

	public ControlStyle getBarStyle() {
		return internalTheme.get("self/bar");
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);
		barStyle = getStyle(gui.getTheme(), "bar");
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);

		int barX = trueX + ((oldArea.width/2) - (barWidth/2));
		int barY = trueY + ((oldArea.height/2) - (barHeight/2));

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
