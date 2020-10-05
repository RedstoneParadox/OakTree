package io.github.redstoneparadox.oaktree.client.gui.style;

import io.github.redstoneparadox.oaktree.client.gui.Color;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Theme {
	public static final Theme EMPTY = new Theme(true);

	private Map<String, @NotNull ControlStyle> styles = new HashMap<>();
	private final boolean empty;

	public Theme() {
		this.empty = false;
	}

	private Theme(boolean empty) {
		this.empty = empty;
	}

	public Theme add(String controlID, String controlState, @NotNull ControlStyle style) {
		if (!empty) styles.put(controlID + "/" + controlState, style);
		return this;
	}

	public Theme add(String controlID, @NotNull ControlStyle style) {
		if (!empty) styles.put(controlID + "/base", style);
		return this;
	}

	public @NotNull ControlStyle get(String style) {
		if (!empty && styles.containsKey(style)) {
			return styles.get(style);
		}
		return ControlStyle.BLANK;
	}

	public Theme copy() {
		Theme copy = new Theme();

		for (Map.Entry<String, @NotNull ControlStyle> entry: styles.entrySet()) {
			copy.add(entry.getKey(), entry.getValue());
		}

		return copy;
	}

	public boolean containsStyle(String styleID) {
		return styles.containsKey(styleID);
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
