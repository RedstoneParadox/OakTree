package io.github.redstoneparadox.oaktree.client.gui.util;

import org.jetbrains.annotations.ApiStatus;

@FunctionalInterface
@Deprecated
@ApiStatus.ScheduledForRemoval
public interface TypingListener<T> {

    Character invoke(Character character, T node);
}
