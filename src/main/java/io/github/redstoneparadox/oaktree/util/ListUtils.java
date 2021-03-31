package io.github.redstoneparadox.oaktree.util;

import java.util.List;

public class ListUtils {
	public static void growList(List<?> list, int targetSize) {
		while (list.size() < targetSize) {
			list.add(null);
		}
	}
}
