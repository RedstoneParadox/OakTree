package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.style.ControlStyle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class SliderControl extends InteractiveControl<SliderControl> implements MouseButtonListener {
	protected float scrollPercent = 0.0f;
	protected int barLength = 1;
	protected boolean horizontal = false;
	protected @NotNull BiConsumer<ControlGui, SliderControl> onSlide = (gui, control) -> {};
	protected boolean held = false;
	protected ControlStyle sliderStyle = null;

	public SliderControl() {
		this.id = "slider";
	}

	public void setScrollPercent(float scrollPercent) {
		this.scrollPercent = scrollPercent;
	}

	public float getScrollPercent() {
		return scrollPercent;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SliderControl scrollPercent(float scrollPercent) {
		this.scrollPercent = scrollPercent;
		return this;
	}

	public void setBarLength(int barLength) {
		this.barLength = barLength;
	}

	public int getBarLength() {
		return barLength;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SliderControl barLength(int barLength) {
		this.barLength = barLength;
		return this;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SliderControl horizontal(boolean horizontal) {
		this.horizontal = horizontal;
		return this;
	}

	public void setSliderStyle(ControlStyle sliderStyle) {
		internalTheme.add("self", "slider", sliderStyle);
	}

	public ControlStyle getSliderStyle() {
		return internalTheme.get("self/slider");
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SliderControl sliderStyle(ControlStyle sliderStyle) {
		internalTheme.add("slider", "slider", sliderStyle);
		return this;
	}

	public void onSlide(@NotNull Consumer<ControlGui> onSlide) {
		this.onSlide = ((controlGui, sliderControl) -> onSlide.accept(controlGui));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SliderControl onSlide(@NotNull BiConsumer<ControlGui, SliderControl> onSlide) {
		this.onSlide = onSlide;
		return this;
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		ClientListeners.MOUSE_BUTTON_LISTENERS.add(this);
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);
		sliderStyle = getStyle(gui.getTheme(), "slider");

		if (isMouseWithin && held) {
			if (horizontal) {
				scrollPercent = Math.max(0.0f, Math.min(((float)mouseX - trueX)/(area.width - barLength) * 100.0f, 100.0f));
			}
			else {
				scrollPercent = Math.max(0.0f, Math.min(((float)mouseY - trueY)/(area.height - barLength) * 100.0f, 100.0f));
			}

			onSlide.accept(gui, this);
		}
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);

		int sliderX = trueX;
		int sliderY = trueY;

		int sliderWidth = area.width;
		int sliderHeight = area.height;

		if (horizontal) {
			sliderX += (int)((scrollPercent)/100 * (area.width - barLength));
			sliderWidth = barLength;
		}
		else {
			sliderY += (int)((scrollPercent)/100 * (area.height - barLength));
			sliderHeight = barLength;
		}

		if (sliderStyle != null) sliderStyle.draw(sliderX, sliderY, sliderWidth, sliderHeight, gui);
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			held = !released;
		}
	}
}
