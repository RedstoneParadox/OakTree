package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Vector2;

public abstract class AbstractControl {
	protected AbstractControl parent;

	protected abstract Vector2 getPosition();

	protected abstract Vector2 getContainerOrigin(AbstractControl element);

	protected abstract Vector2 getSize();

	protected Vector2 getContainerSize() {
		return getSize();
	}

	protected void setParent(AbstractControl parent) {
		this.parent = parent;
	}

	protected void removeParent() {
		parent = null;
	}
}
