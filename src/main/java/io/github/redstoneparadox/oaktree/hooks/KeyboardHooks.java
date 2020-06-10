package io.github.redstoneparadox.oaktree.hooks;

import org.jetbrains.annotations.ApiStatus;
import java.util.function.Consumer;

@Deprecated
@ApiStatus.ScheduledForRemoval
public interface KeyboardHooks {
	void onCharTyped(Consumer<Character> onCharTyped);
}
