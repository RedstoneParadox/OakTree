package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.control.ButtonControl;
import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.control.Control.PainterKey;
import io.github.redstoneparadox.oaktree.control.RootPanelControl;
import io.github.redstoneparadox.oaktree.control.SliderControl;
import io.github.redstoneparadox.oaktree.control.SlotControl;
import io.github.redstoneparadox.oaktree.control.TextEditControl;
import io.github.redstoneparadox.oaktree.util.Color;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@code Theme} is a collection of {@link Painter} instances.
 *
 * @see RootPanelControl
 */
public class Theme {
	private final Map<String, Map<PainterKey, Painter>> painters = new HashMap<>();

	public Theme() {
	}

	/**
	 * Adds a new {@link Painter} to this theme, stored by a unique id
	 * and a {@link PainterKey} specific to the {@link Control} class.
	 * For example, adding a {@code Painter} with an id of "foobar" and
	 * a {@code PainterKey} {@link ButtonControl#HELD} will only draw it
	 * for {@link ButtonControl} instances that are held down and have
	 * "foobar" as their id.
	 *
	 * @param id The {@link Control} id
	 * @param painterKey The painter key from the {@link Control} class
	 * @param painter The {@link Painter}
	 *
	 * @see Control#setId(String)
	 */
	public void put(String id, PainterKey painterKey, @NotNull Painter painter) {
		painters.computeIfAbsent(id, s -> new HashMap<>()).put(painterKey, painter);
	}

	public @NotNull Painter get(String id, PainterKey painterKey) {
		if (painters.containsKey(id)) {
			Map<PainterKey, Painter> subMap = painters.get(id);

			if (subMap.containsKey(painterKey)) {
				return subMap.get(painterKey);
			}
			return Painter.BLANK;
		}

		return Painter.BLANK;
	}

	/**
	 * Creates a copy of this theme
	 *
	 * @return The copy
	 */
	public Theme copy() {
		Theme copy = new Theme();
		copy.painters.putAll(this.painters);
		return copy;
	}

	/**
	 * Creates a theme containing {@link Painter} instances
	 * that draw vanilla textures. Useful if you want to make
	 * a vanilla-themed gui without much effort.
	 *
	 * @return The vanilla theme
	 */
	public static Theme vanilla() {
		Theme vanilla = new Theme();

		NinePatchPainter ninePatchUI = new NinePatchPainter("oaktree:textures/gui/ui.png");

		NinePatchPainter base = ninePatchUI.copy();
		base.setWidths(4, 2, 4);
		base.setHeights(4, 2, 4);
		vanilla.put("base", Control.DEFAULT, base);

		TexturePainter background = new TexturePainter("textures/gui/options_background.png");
		background.setRegionSize(16, 16);
		background.setTextureSize(16, 16);
		background.setTint(Color.rgb(64, 64, 64));
		background.setTiled(true);
		vanilla.put("background", Control.DEFAULT, background);

		NinePatchPainter button = ninePatchUI.copy();
		button.setWidths(3, 12, 3);
		button.setHeights(3, 12, 3);
		button.setOrigin(0, 18);
		vanilla.put("button", ButtonControl.DEFAULT, button);

		NinePatchPainter buttonHovered = button.copy();
		buttonHovered.setOrigin(36, 18);
		vanilla.put("button", ButtonControl.HOVERED, buttonHovered);

		NinePatchPainter buttonHeld = button.copy();
		buttonHeld.setOrigin(18, 18);
		vanilla.put("button", ButtonControl.HELD, buttonHeld);

		NinePatchPainter slider = button.copy();
		vanilla.put("slider", SliderControl.THUMB, slider);

		NinePatchPainter sliderBackground = buttonHeld.copy();
		vanilla.put("slider", SliderControl.DEFAULT, sliderBackground);

		TexturePainter itemSlot = new TexturePainter("oaktree:textures/gui/ui.png");
		itemSlot.setOrigin(18, 0);
		itemSlot.setRegionSize(18, 18);
		vanilla.put("item_slot", SlotControl.DEFAULT, itemSlot);

		ColorPainter textEdit = new ColorPainter(Color.BLACK, Color.rgb(0.63f, 0.63f, 0.63f));
		vanilla.put("text_edit", TextEditControl.DEFAULT, textEdit);

		return vanilla;
	}
}
