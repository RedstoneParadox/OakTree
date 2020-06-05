package io.github.redstoneparadox.oaktree.hooks;

import java.util.function.Consumer;

public interface KeyboardHooks {
	void onCharTyped(Consumer<Character> onCharTyped);
}
