package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Direction2D;
import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.painter.Painter;
import io.github.redstoneparadox.oaktree.painter.Theme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A {@link Control} that acts as a percentage
 * based progress bar.
 */
public class ProgressBarControl extends Control {
	public static PainterKey BAR = new PainterKey();

	protected float percent = 100.0f;
	protected int barWidth = 1;
	protected int barHeight = 1;
	protected Direction2D direction = Direction2D.RIGHT;

	private Painter barStyle = null;

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
	 * is suggested to make it smaller than the control
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

	@Override
	protected void draw(GuiGraphics graphics, Theme theme) {
		super.draw(graphics, matrices, theme);

		int barX = trueArea.getX() + ((trueArea.getWidth() /2) - (barWidth/2));
		int barY = trueArea.getY() + ((trueArea.getHeight() /2) - (barHeight/2));
		Painter barPainter = theme.get(id, BAR);

		switch (direction) {
			case UP -> {
				int drawHeight = (int) (barHeight * (this.percent / 100.0f));
				barPainter.draw(graphics, barX, barY + (barHeight - drawHeight), barWidth, drawHeight);
			}
			case DOWN -> barPainter.draw(graphics, barX, barY, barWidth, (int) (barHeight * (this.percent / 100.0f)));
			case LEFT -> {
				int drawWidth = (int) (barWidth * (this.percent / 100.0f));
				barPainter.draw(graphics, barX - drawWidth, barY, drawWidth, barHeight);
			}
			case RIGHT -> barPainter.draw(graphics, barX, barY, (int) (barWidth * (this.percent / 100.0f)), barHeight);
		}
	}
}
