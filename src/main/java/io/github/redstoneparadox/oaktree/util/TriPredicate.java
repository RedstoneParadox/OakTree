package io.github.redstoneparadox.oaktree.util;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.ScheduledForRemoval
@Deprecated
@FunctionalInterface
public interface TriPredicate<T, U, V> {
	boolean test(T t, U u, V v);
}
