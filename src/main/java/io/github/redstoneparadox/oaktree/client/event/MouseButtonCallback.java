package io.github.redstoneparadox.oaktree.client.event;

public interface MouseButtonCallback {
	void onMouseButton(MouseButton button, boolean released);

	enum MouseButton {
		LEFT,
		RIGHT,
		CENTER
	}
}
