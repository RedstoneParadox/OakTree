package io.github.redstoneparadox.oaktree.util;

import io.github.redstoneparadox.oaktree.hooks.KeyboardHooks;
import net.minecraft.client.MinecraftClient;

public class InputHelper {
	private static OptionalChar lastCharTyped = OptionalChar.empty();

	static {
		((KeyboardHooks)MinecraftClient.getInstance().keyboard).onCharTyped(InputHelper::setLastCharTyped);
	}

	private static void setLastCharTyped(char c) {
		lastCharTyped = OptionalChar.of(c);
	}

	public static OptionalChar getLastCharTyped() {
		return lastCharTyped;
	}
}
