package io.github.redstoneparadox.oaktree.util;

import java.util.List;

public class ListUtils {
	// TODO: Fix this after the ad hoc self-typing fiasco is done with.
	public static <T extends List> T growList(T list, int targetSize) {
		while (list.size() < targetSize) {
			list.add(null);
		}
		return list;
	}
}
