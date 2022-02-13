package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.painter.Painter;
import io.github.redstoneparadox.oaktree.painter.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SliderControl extends InteractiveControl implements MouseButtonListener {
	public static PainterKey SLIDER = new PainterKey();

	protected float scrollPercent = 0.0f;
	protected int barLength = 1;
	protected boolean horizontal = false;
	protected @NotNull BiConsumer<ControlGui, SliderControl> onSlide = (gui, control) -> {};
	protected boolean held = false;
	protected Painter sliderStyle = null;

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

	public void onSlide(@NotNull Consumer<ControlGui> onSlide) {
		this.onSlide = ((controlGui, sliderControl) -> onSlide.accept(controlGui));
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		captured = super.interact(mouseX, mouseY, deltaTime, captured);

		if (captured && held) {
			if (horizontal) {
				scrollPercent = Math.max(0.0f, Math.min(((float)mouseX - trueX)/(area.getWidth() - barLength) * 100.0f, 100.0f));
			}
			else {
				scrollPercent = Math.max(0.0f, Math.min(((float)mouseY - trueY)/(area.getHeight() - barLength) * 100.0f, 100.0f));
			}
		}

		return captured;
	}

	@Override
	protected void draw(MatrixStack matrices, Theme theme) {
		super.draw(matrices, theme);

		int sliderX = trueX;
		int sliderY = trueY;

		int sliderWidth = area.getWidth();
		int sliderHeight = area.getHeight();

		if (horizontal) {
			sliderX += (int)((scrollPercent)/100 * (area.getWidth() - barLength));
			sliderWidth = barLength;
		}
		else {
			sliderY += (int)((scrollPercent)/100 * (area.getHeight() - barLength));
			sliderHeight = barLength;
		}

		if (sliderStyle != null) theme.get(id, SLIDER).draw(matrices, sliderX, sliderY, sliderWidth, sliderHeight);
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			held = !released;
		}
	}
}
