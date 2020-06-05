package io.github.redstoneparadox.oaktree.client.gui.style;

import io.github.redstoneparadox.oaktree.client.gui.Color;

import java.util.HashMap;
import java.util.Map;

public class Theme {
	private Map<String, ControlStyle> styles = new HashMap<>();

	public Theme add(String controlID, String controlState, ControlStyle style) {
		styles.put(controlID + "/" + controlState, style);
		return this;
	}

	public Theme add(String controlID, ControlStyle style) {
		styles.put(controlID + "/default", style);
		return this;
	}

	public ControlStyle get(String style) {
		if (styles.containsKey(style)) {
			return styles.get(style);
		}
		return null;
	}

	public static Theme vanilla() {
		return new Theme()
				.add("item_slot", new TextureControlStyle("oaktree:textures/gui/ui.png")
						.drawOrigin(18, 0)
						.fileDimensions(256, 256)
						.textureSize(18, 18)
						.scale(1)
				)
				.add("base", new NinePatchControlStyle("oaktree:textures/gui/ui.png")
						.widths(4, 2, 4)
						.heights(4, 2, 4)
						.fileDimensions(256, 256)
						.scale(1f))
				.add("text_edit", new ColorControlStyle(Color.BLACK, Color.rgb(0.63f, 0.63f, 0.63f), 1))
				.add("background", new TextureControlStyle("textures/gui/options_background.png")
						.textureSize(16, 16)
						.fileDimensions(16, 16)
						.tint(Color.rgb(64, 64, 64))
						.tiled(true))
				.add("button", "held", new NinePatchControlStyle("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(18, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("button", new NinePatchControlStyle("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(0, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("button", "hover", new NinePatchControlStyle("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(36, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("slider", new NinePatchControlStyle("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(18, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("slider", "slider", new NinePatchControlStyle("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(0, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("tooltip", new NinePatchControlStyle("oaktree:textures/gui/ui.png")
						.widths(2, 1, 2)
						.heights(2, 1, 2)
						.drawOrigin(36, 0)
						.fileDimensions(256, 256)
						.scale(1)
						.tint(Color.WHITE.withAlpha(0.95f)));
	}
}
