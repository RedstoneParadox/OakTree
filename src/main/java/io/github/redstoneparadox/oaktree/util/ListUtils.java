package io.github.redstoneparadox.oaktree.util;

import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
public class ListUtils {
	public static void growList(List<?> list, int targetSize) {
		while (list.size() < targetSize) {
			list.add(null);
		}
	}
}
