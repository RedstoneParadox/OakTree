package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Vector2;

public abstract class ControlElement {
	protected ControlElement parent;

	protected abstract Vector2 getPosition();

	protected abstract Vector2 getSize();

	protected Vector2 getContainerSize() {
		return getSize();
	}

	protected void setParent(ControlElement parent) {
		this.parent = parent;
	}

	protected void removeParent() {
		parent = null;
	}
}
