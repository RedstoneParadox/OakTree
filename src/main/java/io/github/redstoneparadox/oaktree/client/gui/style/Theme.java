package io.github.redstoneparadox.oaktree.client.gui.style;

import java.util.HashMap;
import java.util.Map;

public class Theme {
    private Map<String, StyleBox> styles = new HashMap<>();

    public Theme add(String controlID, String controlState, StyleBox style) {
        styles.put(controlID + "/" + controlState, style);
        return this;
    }
}
