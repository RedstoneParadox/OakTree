package io.github.redstoneparadox.oaktree.listeners;

@FunctionalInterface
public interface MouseButtonListener {
	void onMouseButton(int button, boolean justPressed, boolean released);
}
