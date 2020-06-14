package io.github.redstoneparadox.oaktree.client.listeners;

@FunctionalInterface
public interface MouseButtonListener {
	void onMouseButton(int button, boolean justPressed, boolean released);
}
