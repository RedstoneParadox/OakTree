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
	 * For example, adding a {@code Painter} with a name of "foobar" and
	 * a {@code PainterKey} {@link ButtonControl#HELD} will only draw it
	 * for {@link ButtonControl} instances that are held down and are
	 * named "foobar"
	 *
	 * @param name The {@link Control} name
	 * @param painterKey The painter key from the {@link Control} class
	 * @param painter The {@link Painter}
	 *
	 * @see Control#setName(String)
	 */
	public void put(String name, PainterKey painterKey, @NotNull Painter painter) {
		painters.computeIfAbsent(name, s -> new HashMap<>()).put(painterKey, painter);
	}

	/**
	 * Returns a {@link Painter} for the given {@link Control}
	 * name and {@link PainterKey}. If no {@code Painter}
	 * is found, {@link Painter#BLANK} is returned instead.
	 *
	 * @param name The {@link Control} name
	 * @param painterKey The {@link PainterKey}
	 * @return The {@link Painter}
	 */
	public @NotNull Painter get(String name, PainterKey painterKey) {
		if (painters.containsKey(name)) {
			Map<PainterKey, Painter> subMap = painters.get(name);

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

		TexturePainter base = new TexturePainter("oaktree:background/ui_base");
		vanilla.put("base", Control.DEFAULT, base);

		TexturePainter button = new TexturePainter("widget/button");
		vanilla.put("button", ButtonControl.DEFAULT, button);

		TexturePainter buttonHovered = new TexturePainter("widget/button_highlighted");
		vanilla.put("button", ButtonControl.HOVERED, buttonHovered);

		TexturePainter buttonHeld = new TexturePainter("widget/button_disabled");
		vanilla.put("button", ButtonControl.HELD, buttonHeld);

		TexturePainter slider = new TexturePainter("widget/slider_handle");
		vanilla.put("slider", SliderControl.THUMB, slider);

		TexturePainter sliderBackground = new TexturePainter("widget/slider");
		vanilla.put("slider", SliderControl.DEFAULT, sliderBackground);

		TexturePainter itemSlot = new TexturePainter("container/slot");
		vanilla.put("item_slot", SlotControl.DEFAULT, itemSlot);

		TexturePainter textEdit = new TexturePainter("widget/text_edit");
		vanilla.put("text_edit", TextEditControl.DEFAULT, textEdit);

		return vanilla;
	}
}
