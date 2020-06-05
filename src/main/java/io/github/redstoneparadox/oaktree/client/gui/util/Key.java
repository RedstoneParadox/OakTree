package io.github.redstoneparadox.oaktree.client.gui.util;

import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.ApiStatus;

@Deprecated
@ApiStatus.ScheduledForRemoval
public enum Key {
	NONE,
	BACKSPACE,
	ENTER,
	CTRL_A,
	COPY,
	CUT,
	PASTE,
	UP,
	DOWN,
	LEFT,
	RIGHT;

	public static Key fromKeycode(int keycode) {
		Key key = NONE;

		if (keycode == 259) key = BACKSPACE;
		else if (keycode == 257) key = ENTER;
		else if (Screen.isSelectAll(keycode)) key = CTRL_A;
		else if (Screen.isCopy(keycode)) key = COPY;
		else if (Screen.isCut(keycode)) key = CUT;
		else if (Screen.isPaste(keycode)) key = PASTE;
		else if (keycode == 265) key = UP;
		else if (keycode == 264) key = DOWN;
		else if (keycode == 263) key = LEFT;
		else if (keycode == 262) key  = RIGHT;

		return key;
	}
}
