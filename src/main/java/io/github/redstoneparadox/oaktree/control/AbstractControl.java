package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Vector2;

public abstract class AbstractControl {
	protected AbstractControl parent;

	protected void setParent(AbstractControl parent) {
		this.parent = parent;
	}

	protected void removeParent() {
		parent = null;
	}
}
