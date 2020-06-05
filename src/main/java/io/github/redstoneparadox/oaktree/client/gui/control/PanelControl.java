package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.geometry.Rectangle;
import io.github.redstoneparadox.oaktree.client.geometry.ScreenPos;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A {@link Control} containing any number of children
 * {@link Control} instances. Children are drawn
 * without any arrangement applied to them; subclasses
 * offer various arrangement options.
 *
 * @param <C> The {@link PanelControl} type.
 */
public class PanelControl<C extends PanelControl<C>> extends PaddingControl<C> {
	public final List<@NotNull Control<?>> children = new ArrayList<>();

	public PanelControl() {
		this.id = "panel";
	}

	/**
	 * Adds a child to this PanelControl.
	 *
	 * @param child The child control.
	 * @return The panel control itself.
	 */
	public C child(@NotNull Control<?> child) {
		children.add(child);
		return (C) this;
	}

	/**
	 * Adds the specified number of children to this PanelControl;
	 * children are supplied by the function which is passed the
	 * index for that child.
	 *
	 * @param count The amount of children to add.
	 * @param function The function to supply children.
	 * @return The PanelControl itself.
	 */
	public C children(int count, Function<Integer, Control<?>> function) {
		children.clear();
		for (int i  = 0; i < count; i++) {
			children.add(function.apply(i));
		}
		return (C) this;
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		for (Control<?> child: children) {
			child.setup(client, gui);
		}
	}

	@Override
	public void zIndex(List<Control<?>> controls) {
		if (!visible) return;
		controls.add(this);
		for (Control<?> child: children) {
			if (child.isVisible()) child.zIndex(controls);
		}
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);
		arrangeChildren(gui, mouseX, mouseY);
	}

	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		ScreenPos innerPosition = innerPosition(trueX, trueY);
		ScreenPos innerDimensions = innerDimensions(area.width, area.height);
		for (Control<?> child: children) {
			if (child.isVisible()) child.preDraw(gui, innerPosition.x, innerPosition.y, innerDimensions.x, innerDimensions.y, mouseX, mouseY);
		}
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);
		for (Control<?> child: children) {
			if (child.isVisible() && shouldDraw(child)) child.draw(matrices, mouseX, mouseY, deltaTime, gui);
		}
	}

	boolean shouldDraw(Control<?> child) {
		return true;
	}
}
