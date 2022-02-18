package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.math.Direction2D;
import io.github.redstoneparadox.oaktree.util.ZIndexedControls;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class DropdownControl extends Control implements MouseButtonListener {
	@NotNull protected Control dropdown = new Control();
	@NotNull protected Direction2D dropdownDirection = Direction2D.DOWN;

	// Internal state
	boolean dropdownVisible = false;

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
	protected void updateTree(ZIndexedControls zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);

		int dropdownX = 0;
		int dropdownY = 0;

		switch (dropdownDirection) {
			case UP -> dropdownY = -dropdown.area.getHeight();
			case DOWN -> dropdownY = area.getY() + area.getHeight();
			case LEFT -> dropdownX = -dropdown.area.getWidth();
			case RIGHT -> dropdownX = area.getX() + area.getWidth();
		}

		dropdown.expand = false;

		dropdown.updateTree(zIndexedControls, dropdownX, dropdownY, 999999, 999999);
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		captured = super.interact(mouseX, mouseY, deltaTime, captured);

		if (captured && dropdownVisible != dropdown.visible) {
			dropdown.visible = dropdownVisible;
		} else {
			dropdownVisible = dropdown.visible;
		}

		return captured;
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && justPressed) {
			dropdownVisible = !dropdownVisible;
		}
	}
}
