package io.github.redstoneparadox.oaktree.client.gui.style;

import java.util.HashMap;
import java.util.Map;

public class Theme {
    private Map<String, StyleBox> styles = new HashMap<>();

    public Theme add(String controlID, String controlState, StyleBox style) {
        styles.put(controlID + "/" + controlState, style);
        return this;
    }

    public Theme add(String controlID, StyleBox style) {
        styles.put(controlID + "/default", style);
        return this;
    }

    public StyleBox get(String style) {
        if (styles.containsKey(style)) {
            return styles.get(style);
        }
        return null;
    }

    public static Theme vanilla() {
        return new Theme()
                .add("item_slot_control", new TextureStyleBox("oaktree:gui/ui"));
    }
}
