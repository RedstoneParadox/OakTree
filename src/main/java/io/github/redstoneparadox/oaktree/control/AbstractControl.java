package io.github.redstoneparadox.oaktree.control;

import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public abstract class AbstractControl {
	protected AbstractControl parent;

	protected void setParent(AbstractControl parent) {
		this.parent = parent;
	}

	protected AbstractControl getParent() {
		return parent;
	}

	protected RootControl getTreeRoot() {
		if (parent instanceof RootControl) {
			return (RootControl) parent;
		}
		return parent.getTreeRoot();
	}

	protected void markDirty() {
		parent.markDirty();
	}

	protected void updateTree(List<Control> zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {

	}

	// Capture the mouse and
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		return false;
	}

	// Update current
	protected void prepare() {

	}

	// Draw
	protected void draw(MatrixStack matrices) {

	}
}
