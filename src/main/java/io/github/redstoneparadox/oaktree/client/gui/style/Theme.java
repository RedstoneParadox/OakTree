package io.github.redstoneparadox.oaktree.client.gui.style;

import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;

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
                .add("item_slot", new TextureStyleBox("oaktree:textures/gui/ui.png").drawOrigin(18, 0))
                .add("base", new NinePatchStyleBox("oaktree:textures/gui/ui.png")
                        .widths(5, 1, 5)
                        .heights(5, 1, 5))
                .add("text_edit", new ColorStyleBox(RGBAColor.black(), new RGBAColor(0.63f, 0.63f, 0.63f), 1))
                .add("background", new TextureStyleBox("textures/gui/options_background.png")
                        .textureSize(16, 16)
                        .tint(new RGBAColor(64, 64, 64))
                        .magicNumber(2)
                        .tiled(true));
    }
}
