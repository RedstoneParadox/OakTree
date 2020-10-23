package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.math.Direction2D;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class DropdownControl extends InteractiveControl<DropdownControl> implements MouseButtonListener {
	@NotNull protected Control<?> dropdown = new Control<>();
	@NotNull protected Direction2D dropdownDirection = Direction2D.DOWN;

	public DropdownControl() {
		this.dropdown.visible(false);
		this.id = "dropdown";
	}

	public DropdownControl dropdown(@NotNull Control<?> dropdown) {
		this.dropdown = dropdown;
		this.dropdown.visible(false);
		return this;
	}

	public @NotNull Control<?> getDropdown() {
		return dropdown;
	}

	public DropdownControl dropdownDirection(@NotNull Direction2D dropdownDirection) {
		this.dropdownDirection = dropdownDirection;
		return this;
	}

	public @NotNull Direction2D getDropdownDirection() {
		return this.dropdownDirection;
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		dropdown.setup(client, gui);
		ClientListeners.MOUSE_BUTTON_LISTENERS.add(this);
	}

	@Override
	public void zIndex(List<Control<?>> controls) {
		if (!visible) return;
		controls.add(this);
		dropdown.zIndex(controls);
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

		int dropdownX = 0;
		int dropdownY = 0;

		switch (dropdownDirection) {
			case UP:
				dropdownY = -dropdown.area.height;
				break;
			case DOWN:
				dropdownY = area.y + area.height;
				break;
			case LEFT:
				dropdownX = -dropdown.area.width;
				break;
			case RIGHT:
				dropdownX = area.x + area.width;
				break;
		}

		dropdown.expand = false;
		if (dropdown.visible) dropdown.preDraw(gui, dropdownX + trueX, dropdownY + trueY, containerWidth, containerHeight, mouseX, mouseY);
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);
		if (dropdown.visible) dropdown.draw(matrices, mouseX, mouseY, deltaTime, gui);
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && justPressed && isMouseWithin) {
			dropdown.visible = !dropdown.visible;
		}
	}
}
