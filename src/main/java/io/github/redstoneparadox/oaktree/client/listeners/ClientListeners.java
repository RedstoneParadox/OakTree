package io.github.redstoneparadox.oaktree.client.listeners;

import java.util.HashSet;
import java.util.Set;

public class ClientListeners {
	public static final Set<MouseButtonListener> MOUSE_BUTTON_LISTENERS = new HashSet<>();
	public static final Set<CharTypedListener> CHAR_TYPED_LISTENERS = new HashSet<>();

	public static void onMouseButton(int button, boolean justPressed, boolean released) {
		for (MouseButtonListener listener: MOUSE_BUTTON_LISTENERS) {
			if (released) {
				listener.onMouseButton(button, false, true);
			}
			else {
				listener.onMouseButton(button, justPressed, false);
			}
		}
	}

	public static void onCharTyped(char c) {
		for (CharTypedListener listener: CHAR_TYPED_LISTENERS) {
			listener.onCharTyped(c);
		}
	}
}
