package io.github.redstoneparadox.oaktree.control;

import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public abstract class AbstractControl {
	protected AbstractControl parent;

	protected void setParent(AbstractControl parent) {
		this.parent = parent;
	}

	protected void removeParent() {
		parent = null;
	}

	protected void updateTree(List<Control> zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {

	}

	// Capture the mouse and
	protected boolean interact(int mouseX, int mouseY, float deltaTime) {
		return false;
	}

	// Update current
	protected void prepare() {

	}

	// Draw
	protected void draw(MatrixStack matrices) {

	}
}
