package io.github.redstoneparadox.oaktree.client.gui.style;

import io.github.redstoneparadox.oaktree.client.gui.Color;

import java.util.HashMap;
import java.util.Map;

public class Theme {
    private Map<String, Style> styles = new HashMap<>();

    public Theme add(String controlID, String controlState, Style style) {
        styles.put(controlID + "/" + controlState, style);
        return this;
    }

    public Theme add(String controlID, Style style) {
        styles.put(controlID + "/default", style);
        return this;
    }

    public Style get(String style) {
        if (styles.containsKey(style)) {
            return styles.get(style);
        }
        return null;
    }

    public static Theme vanilla() {
        return new Theme()
                .add("item_slot", new TextureStyle("oaktree:textures/gui/ui.png")
                        .drawOrigin(18, 0)
                        .fileDimensions(256, 256)
                        .textureSize(18, 18)
                        .scale(1)
                )
                .add("base", new NinePatchStyle("oaktree:textures/gui/ui.png")
                        .widths(4, 2, 4)
                        .heights(4, 2, 4)
                        .fileDimensions(256, 256)
                        .scale(1f))
                .add("text_edit", new ColorStyle(Color.BLACK, Color.rgb(0.63f, 0.63f, 0.63f), 1))
                .add("background", new TextureStyle("textures/gui/options_background.png")
                        .textureSize(16, 16)
                        .fileDimensions(16, 16)
                        .tint(Color.rgb(64, 64, 64))
                        .tiled(true))
                .add("button", "held", new NinePatchStyle("oaktree:textures/gui/ui.png")
                        .widths(3, 12, 3)
                        .heights(3, 12, 3)
                        .drawOrigin(18, 18)
                        .fileDimensions(256, 256)
                        .scale(1))
                .add("button", new NinePatchStyle("oaktree:textures/gui/ui.png")
                        .widths(3, 12, 3)
                        .heights(3, 12, 3)
                        .drawOrigin(0, 18)
                        .fileDimensions(256, 256)
                        .scale(1))
                .add("button", "hover", new NinePatchStyle("oaktree:textures/gui/ui.png")
                        .widths(3, 12, 3)
                        .heights(3, 12, 3)
                        .drawOrigin(36, 18)
                        .fileDimensions(256, 256)
                        .scale(1))
                .add("slider", new NinePatchStyle("oaktree:textures/gui/ui.png")
                        .widths(3, 12, 3)
                        .heights(3, 12, 3)
                        .drawOrigin(18, 18)
                        .fileDimensions(256, 256)
                        .scale(1))
                .add("slider", "slider", new NinePatchStyle("oaktree:textures/gui/ui.png")
                        .widths(3, 12, 3)
                        .heights(3, 12, 3)
                        .drawOrigin(0, 18)
                        .fileDimensions(256, 256)
                        .scale(1));
    }
}
