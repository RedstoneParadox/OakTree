package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.control.ButtonControl;
import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.control.SliderControl;
import io.github.redstoneparadox.oaktree.control.SlotControl;
import io.github.redstoneparadox.oaktree.control.TextEditControl;
import io.github.redstoneparadox.oaktree.util.Color;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Theme {
	private final Map<String, Map<Control.PainterKey, Painter>> painters = new HashMap<>();

	public Theme() {
	}

	public void put(String id, Control.PainterKey painterKey, @NotNull Painter painter) {
		painters.computeIfAbsent(id, s -> new HashMap<>()).put(painterKey, painter);
	}

	public @NotNull Painter get(String id, Control.PainterKey painterKey) {
		if (painters.containsKey(id)) {
			Map<Control.PainterKey, Painter> subMap = painters.get(id);

			if (subMap.containsKey(painterKey)) {
				return subMap.get(painterKey);
			}
			return Painter.BLANK;
		}

		return Painter.BLANK;
	}

	public Theme copy() {
		Theme copy = new Theme();
		copy.painters.putAll(this.painters);
		return copy;
	}

	public static Theme vanilla() {
		Theme vanilla = new Theme();

		NinePatchPainter ninePatchUI = new NinePatchPainter("oaktree:textures/gui/ui.png");

		NinePatchPainter base = ninePatchUI.copy();
		base.setWidths(4, 2, 4);
		base.setHeights(4, 2, 4);
		vanilla.put("base", Control.DEFAULT, base);

		TexturePainter background = new TexturePainter("textures/gui/options_background.png");
		background.setTextureSize(16, 16);
		background.setFileDimensions(16, 16);
		background.setTint(Color.rgb(64, 64, 64));
		background.setTiled(true);
		vanilla.put("background", Control.DEFAULT, background);

		NinePatchPainter button = ninePatchUI.copy();
		button.setWidths(3, 12, 3);
		button.setHeights(3, 12, 3);
		button.setDrawOrigin(0, 18);
		vanilla.put("button", ButtonControl.DEFAULT, button);

		NinePatchPainter buttonHovered = button.copy();
		buttonHovered.setDrawOrigin(36, 18);
		vanilla.put("button", ButtonControl.HOVERED, buttonHovered);

		NinePatchPainter buttonHeld = button.copy();
		buttonHeld.setDrawOrigin(18, 18);
		vanilla.put("button", ButtonControl.HELD, buttonHeld);

		NinePatchPainter slider = button.copy();
		vanilla.put("slider", SliderControl.SLIDER, slider);

		NinePatchPainter sliderBackground = buttonHeld.copy();
		vanilla.put("slider", SliderControl.DEFAULT, sliderBackground);

		TexturePainter itemSlot = new TexturePainter("oaktree:textures/gui/ui.png");
		itemSlot.setDrawOrigin(18, 0);
		itemSlot.setTextureSize(18, 18);
		vanilla.put("item_slot", SlotControl.DEFAULT, itemSlot);

		ColorPainter textEdit = new ColorPainter(Color.BLACK, Color.rgb(0.63f, 0.63f, 0.63f));
		vanilla.put("text_edit", TextEditControl.DEFAULT, textEdit);

		return vanilla;
	}
}
