package net.redstoneparadox.oaktree.util;

import java.util.List;

public class ListUtils {

    public static <T extends List> T growList(T list, int targetSize) {
        while (list.size() < targetSize) {
            list.add(null);
        }
        return list;
    }
}
