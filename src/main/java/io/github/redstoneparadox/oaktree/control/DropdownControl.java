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

public class DropdownControl extends InteractiveControl implements MouseButtonListener {
	@NotNull protected Control dropdown = new Control();
	@NotNull protected Direction2D dropdownDirection = Direction2D.DOWN;

	public DropdownControl() {
		this.dropdown.setVisible(false);
		this.id = "dropdown";
	}

	public void setDropdown(@NotNull Control dropdown) {
		this.dropdown = dropdown;
		this.dropdown.setVisible(false);
	}

	public @NotNull Control getDropdown() {
		return dropdown;
	}

	public void setDropdownDirection(@NotNull Direction2D dropdownDirection) {
		this.dropdownDirection = dropdownDirection;
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
	public void zIndex(List<Control> controls) {
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
				dropdownY = -dropdown.area.getHeight();
				break;
			case DOWN:
				dropdownY = area.getY() + area.getHeight();
				break;
			case LEFT:
				dropdownX = -dropdown.area.getWidth();
				break;
			case RIGHT:
				dropdownX = area.getX() + area.getWidth();
				break;
		}

		dropdown.expand = false;
		if (dropdown.visible) dropdown.preDraw(gui, dropdownX + trueX, dropdownY + trueY, containerWidth, containerHeight, mouseX, mouseY);
	}

	@Override
	public void oldDraw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.oldDraw(matrices, mouseX, mouseY, deltaTime, gui);
		if (dropdown.visible) dropdown.oldDraw(matrices, mouseX, mouseY, deltaTime, gui);
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && justPressed && isMouseWithin) {
			dropdown.visible = !dropdown.visible;
		}
	}
}
