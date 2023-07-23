package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.painter.Theme;
import io.github.redstoneparadox.oaktree.util.Action;
import io.github.redstoneparadox.oaktree.util.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * A control representing a slider or a scrollbar.
 */
public class SliderControl extends Control implements MouseButtonListener {
	public static PainterKey SLIDER = new PainterKey();

	protected float scrollPercent = 0.0f;
	protected int thumbSize = 1;
	protected boolean horizontal = false;
	protected @NotNull Action onSlide = () -> {};
	protected boolean held = false;
	private final LabelControl label = new LabelControl();

	public SliderControl() {
		this.id = "slider";

		label.fitText = true;
		label.capture = false;
		label.setFontColor(Color.WHITE);
		label.setAnchor(Anchor.CENTER);
		label.setParent(this);

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
		label.setText(Text.literal(text));
	}

	/**
	 * Sets the text to display. The text will
	 * be drawn over the top of the thumb.
	 *
	 * @param text The text.
	 */
	public void setText(Text text) {
		label.setText(text);
	}

	public Text getText() {
		return label.getText();
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
	protected void updateTree(List<Control> orderedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(orderedControls, containerX, containerY, containerWidth, containerHeight);
		label.updateTree(orderedControls, trueArea.getX(), trueArea.getY(), trueArea.getWidth(), trueArea.getHeight());
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

		theme.get(id, SLIDER).draw(graphics, sliderX, sliderY, sliderWidth, sliderHeight);
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
