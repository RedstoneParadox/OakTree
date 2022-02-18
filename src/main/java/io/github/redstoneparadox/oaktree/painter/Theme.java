package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.util.Color;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Theme {
	public static final Theme EMPTY = new Theme(true);

	private final Map<String, @NotNull Painter> styles = new HashMap<>();
	private final Map<String, Map<Control.PainterKey, Painter>> painters = new HashMap<>();
	private final boolean empty;

	public Theme() {
		this.empty = false;
	}

	private Theme(boolean empty) {
		this.empty = empty;
	}

	public void put(String id, Control.PainterKey painterKey, @NotNull Painter painter) {
		if (empty) return;
		Map<Control.PainterKey, Painter> subMap;
		if (painters.containsKey(id)) {
			subMap = painters.get(id);
		} else {
			subMap = painters.put(id, new HashMap<>());
		}

		assert subMap != null;
		subMap.put(painterKey, painter);
	}

	public @NotNull Painter get(String id, Control.PainterKey painterKey) {
		if (empty) return Painter.BLANK;

		if (painters.containsKey(id)) {
			Map<Control.PainterKey, Painter> subMap = painters.get(id);

			if (subMap.containsKey(painterKey)) {
				return subMap.get(painterKey);
			}
			return Painter.BLANK;
		}

		return Painter.BLANK;
	}

	@Deprecated
	public Theme add(String controlID, String controlState, @NotNull Painter style) {
		if (!empty) styles.put(controlID + "/" + controlState, style);
		return this;
	}

	@Deprecated
	public Theme add(String controlID, @NotNull Painter style) {
		if (!empty) styles.put(controlID + "/base", style);
		return this;
	}

	@Deprecated
	public @NotNull Painter get(String style) {
		if (!empty && styles.containsKey(style)) {
			return styles.get(style);
		}
		return Painter.BLANK;
	}

	public Theme copy() {
		Theme copy = new Theme();

		for (Map.Entry<String, @NotNull Painter> entry: styles.entrySet()) {
			copy.add(entry.getKey(), entry.getValue());
		}

		return copy;
	}

	public boolean containsStyle(String styleID) {
		return styles.containsKey(styleID);
	}

	public static Theme vanilla() {
		Theme vanilla = new Theme();

		return vanilla;
		/*
		return new Theme()
				.add("item_slot", new TexturePainter("oaktree:textures/gui/ui.png")
						.drawOrigin(18, 0)
						.fileDimensions(256, 256)
						.textureSize(18, 18)
						.scale(1)
				)
				.add("base", new NinePatchPainter("oaktree:textures/gui/ui.png")
						.widths(4, 2, 4)
						.heights(4, 2, 4)
						.fileDimensions(256, 256)
						.scale(1f))
				.add("text_edit", new ColorPainter(Color.BLACK, Color.rgb(0.63f, 0.63f, 0.63f), 1))
				.add("background", new TexturePainter("textures/gui/options_background.png")
						.textureSize(16, 16)
						.fileDimensions(16, 16)
						.tint(Color.rgb(64, 64, 64))
						.tiled(true))
				.add("button", "held", new NinePatchPainter("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(18, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("button", new NinePatchPainter("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(0, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("button", "hover", new NinePatchPainter("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(36, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("slider", new NinePatchPainter("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(18, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("slider", "slider", new NinePatchPainter("oaktree:textures/gui/ui.png")
						.widths(3, 12, 3)
						.heights(3, 12, 3)
						.drawOrigin(0, 18)
						.fileDimensions(256, 256)
						.scale(1))
				.add("tooltip", new NinePatchPainter("oaktree:textures/gui/ui.png")
						.widths(2, 1, 2)
						.heights(2, 1, 2)
						.drawOrigin(36, 0)
						.fileDimensions(256, 256)
						.scale(1)
						.tint(Color.WHITE.withAlpha(0.95f)));
		 */
	}
}
