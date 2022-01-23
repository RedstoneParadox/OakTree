package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Util;
import io.github.redstoneparadox.oaktree.math.Vector2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * A {@link Control} containing any number of children
 * {@link Control} instances. Children are drawn
 * without any arrangement applied to them; subclasses
 * offer various arrangement options.
 */
public class PanelControl extends PaddingControl {
	public final List<@NotNull Control> children = new ArrayList<>();
	protected int scrollPosX = 0;
	protected int scrollPosY = 0;

	private int scrollSpeedX = 1;
	private int scrollSpeedY = 1;

	public PanelControl() {
		this.id = "panel";
	}

	protected PanelControl(int scrollSpeedX, int scrollSpeedY) {
		this();
		this.scrollSpeedX = scrollSpeedX;
		this.scrollSpeedY = scrollSpeedY;
	}

	/**
	 * Adds a child to this PanelControl.
	 *
	 * @param child The child control.
	 */
	public void addChild(@NotNull Control child) {
		children.add(child);
	}

	/**
	 * Adds the specified number of children to this PanelControl;
	 * children are supplied by the function which is passed the
	 * index for that child.
	 *
	 * @param count The amount of children to add.
	 * @param clear Whether existing children should be removed.
	 * @param function The function to supply children.
	 */
	public void addChildren(int count, boolean clear, Function<Integer, Control> function) {
		children.clear();
		for (int i  = 0; i < count; i++) {
			children.add(function.apply(i));
		}
	}

	public @Nullable Control getChild(int index) {
		if (index < children.size()) {
			return children.get(index);
		}

		return null;
	}

	public void scroll(int x, int y) {
		scrollPosX += Util.floorTo(x, scrollSpeedX);
		scrollPosY += Util.floorTo(y, scrollSpeedY);
	}

	@Override
	protected void updateTree(List<Control> zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);

		for (Control child: children) {
			child.updateTree(zIndexedControls, trueArea.getX(), trueArea.getY(), trueArea.getWidth(), trueArea.getHeight());
		}
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		for (Control child: children) {
			child.setup(client, gui);
		}
	}

	@Override
	public void zIndex(List<Control> controls) {
		if (!visible) return;
		controls.add(this);
		for (Control child: children) {
			if (child.isVisible()) child.zIndex(controls);
		}
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);
		arrangeChildren(gui, mouseX, mouseY);
	}

	void arrangeChildren(ControlGui gui, int mouseX, int mouseY) {
		Vector2 innerPosition = innerPosition(trueX, trueY);
		Vector2 innerDimensions = innerDimensions(area.getWidth(), area.getHeight());
		for (Control child: children) {
			if (child.isVisible()) child.preDraw(gui, innerPosition.getX(), innerPosition.getY(), innerDimensions.getX(), innerDimensions.getY(), mouseX, mouseY);
		}
	}

	@Override
	public void oldDraw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.oldDraw(matrices, mouseX, mouseY, deltaTime, gui);
		for (Control child: children) {
			if (child.isVisible() && shouldDraw(child)) child.oldDraw(matrices, mouseX, mouseY, deltaTime, gui);
		}
	}

	boolean shouldDraw(Control child) {
		return true;
	}

	private static int toInterval(int value, int nearest) {
		if (nearest == 0) {
			return 0;
		}
		if (nearest < 0) {
			throw new InvalidParameterException("Cannot floor to the nearest negative number.");
		}
		if (value < 0) {
			return value + (value % nearest);
		}

		return value - (value % nearest);
	}
}
