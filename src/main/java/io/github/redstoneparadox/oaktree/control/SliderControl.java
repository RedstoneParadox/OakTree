package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.painter.Theme;
import io.github.redstoneparadox.oaktree.util.Action;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class SliderControl extends Control implements MouseButtonListener {
	public static PainterKey SLIDER = new PainterKey();

	protected float scrollPercent = 0.0f;
	protected int barLength = 1;
	protected boolean horizontal = false;
	protected @NotNull Action onSlide = () -> {};
	protected boolean held = false;

	public SliderControl() {
		this.id = "slider";
		ClientListeners.MOUSE_BUTTON_LISTENERS.add(this);
	}

	public void setScrollPercent(float scrollPercent) {
		this.scrollPercent = scrollPercent;
	}

	public float getScrollPercent() {
		return scrollPercent;
	}

	public void setBarLength(int barLength) {
		this.barLength = barLength;
	}

	public int getBarLength() {
		return barLength;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public void onSlide(@NotNull Action onSlide) {
		this.onSlide = onSlide;
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		captured = super.interact(mouseX, mouseY, deltaTime, captured);

		if (captured && held) {
			float percent = scrollPercent;

			if (horizontal) {
				scrollPercent = ((float) mouseX - trueArea.getX())/(trueArea.getWidth() - barLength) * 100.0f;
			}
			else {
				scrollPercent = ((float)mouseY - trueArea.getY())/(trueArea.getHeight() - barLength) * 100.0f;
			}

			scrollPercent = Math.max(0.0f, Math.min(100.0f, scrollPercent));
			if (scrollPercent != percent) onSlide.run();
		}

		return captured;
	}

	@Override
	protected void draw(MatrixStack matrices, Theme theme) {
		super.draw(matrices, theme);

		int sliderX = trueArea.getX();
		int sliderY = trueArea.getY();

		int sliderWidth = trueArea.getWidth();
		int sliderHeight = trueArea.getHeight();

		if (horizontal) {
			sliderX += (int)((scrollPercent)/100 * (trueArea.getWidth() - barLength));
			sliderWidth = barLength;
		}
		else {
			sliderY += (int)((scrollPercent)/100 * (trueArea.getHeight() - barLength));
			sliderHeight = barLength;
		}

		theme.get(id, SLIDER).draw(matrices, sliderX, sliderY, sliderWidth, sliderHeight);
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			held = !released;
		}
	}
}
