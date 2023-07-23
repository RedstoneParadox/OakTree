package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.painter.Theme;
import io.github.redstoneparadox.oaktree.util.Action;
import io.github.redstoneparadox.oaktree.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

/**
 * A control representing a slider or a scrollbar.
 */
public class SliderControl extends Control implements MouseButtonListener {
	public static PainterKey THUMB = new PainterKey();

	protected float scrollPercent = 0.0f;
	protected int thumbSize = 1;
	protected boolean horizontal = false;

	protected Text text = Text.empty();
	protected @NotNull Action onSlide = () -> {};
	protected boolean held = false;

	public SliderControl() {
		this.id = "slider";

		ClientListeners.MOUSE_BUTTON_LISTENERS.add(this);
	}

	/**
	 * Sets how far this bar is scrolled from
	 * 0% to 100%.
	 *
	 * @param scrollPercent The percentage
	 */
	public void setScrollPercent(float scrollPercent) {
		this.scrollPercent = scrollPercent;
	}

	public float getScrollPercent() {
		return scrollPercent;
	}

	/**
	 * Sets the size of the thumb (the
	 * part you grab and move). If the
	 * slider is set to draw
	 * horizontally, the thumb's size is
	 * its height. If not, the size is
	 * the width.
	 *
	 * @param thumbSize The size
	 */
	public void setThumbSize(int thumbSize) {
		this.thumbSize = thumbSize;
	}

	public int getThumbSize() {
		return thumbSize;
	}

	/**
	 * @param horizontal If this should draw horizontally.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	/**
	 * Sets the text to display. The text will
	 * be drawn over the top of the thumb.
	 *
	 * @param text The text.
	 */
	public void setText(String text) {
		this.text = Text.of(text);
	}

	/**
	 * Sets the text to display. The text will
	 * be drawn over the top of the thumb.
	 *
	 * @param text The text.
	 */
	public void setText(Text text) {
		this.text = text;
	}

	public Text getText() {
		return text;
	}

	/**
	 * Sets an {@link Action} to run whenever
	 * the slider thumb is moved.
	 *
	 * @param onSlide The action to run.
	 */
	public void onSlide(@NotNull Action onSlide) {
		this.onSlide = onSlide;
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		captured = super.interact(mouseX, mouseY, deltaTime, captured);

		if (captured && held) {
			float percent = scrollPercent;

			if (horizontal) {
				scrollPercent = ((float) mouseX - trueArea.getX())/(trueArea.getWidth() - thumbSize) * 100.0f;
			}
			else {
				scrollPercent = ((float)mouseY - trueArea.getY())/(trueArea.getHeight() - thumbSize) * 100.0f;
			}

			scrollPercent = Math.max(0.0f, Math.min(100.0f, scrollPercent));
			if (scrollPercent != percent) onSlide.run();
		}

		return captured;
	}

	@Override
	protected void draw(GuiGraphics graphics, Theme theme) {
		super.draw(graphics, theme);

		int sliderX = trueArea.getX();
		int sliderY = trueArea.getY();

		int sliderWidth = trueArea.getWidth();
		int sliderHeight = trueArea.getHeight();

		if (horizontal) {
			sliderX += (int)((scrollPercent)/100 * (trueArea.getWidth() - thumbSize));
			sliderWidth = thumbSize;
		}
		else {
			sliderY += (int)((scrollPercent)/100 * (trueArea.getHeight() - thumbSize));
			sliderHeight = thumbSize;
		}

		theme.get(id, THUMB).draw(graphics, sliderX, sliderY, sliderWidth, sliderHeight);

		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		int textX = trueArea.getX() + trueArea.getWidth()/2 - textRenderer.getWidth(text)/2;
		int textY = trueArea.getY() + trueArea.getHeight()/2 - textRenderer.fontHeight/2;

		graphics.drawText(textRenderer, text, textX, textY, Color.WHITE.toInt(), false);
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		ClientListeners.MOUSE_BUTTON_LISTENERS.remove(this);
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			held = !released;
		}
	}
}
