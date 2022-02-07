package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Rectangle;
import io.github.redstoneparadox.oaktree.math.Vector2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

	public PanelControl() {
		this.id = "panel";
	}

	/**
	 * Adds a child to this PanelControl.
	 *
	 * @param child The child control.
	 */
	public void addChild(@NotNull Control child) {
		children.add(child);
		markDirty();
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
		if (clear) {
			children.clear();
		}

		for (int i  = 0; i < count; i++) {
			children.add(function.apply(i));
		}
		markDirty();
	}

	public @Nullable Control getChild(int index) {
		if (index < children.size()) {
			return children.get(index);
		}

		return null;
	}

	public @Nullable Control removeChild(int index) {
		markDirty();
		return children.remove(index);
	}

	public void clearChildren() {
		children.clear();
		markDirty();
	}

	public int getChildrenCount() {
		return children.size();
	}

	@Override
	protected void updateTree(List<Control> zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);

		for (int i = 0; i < children.size(); i++) {
			Control child = children.get(i);
			Rectangle childArea = getChildArea(i);

			if (child.visible) child.updateTree(zIndexedControls, childArea.getX(), childArea.getY(), childArea.getWidth(), childArea.getHeight());
		}
	}

	protected Rectangle getChildArea(int index) {
		// TODO: Figure out some way to cache the result
		int innerX = trueArea.getX() + leftPadding;
		int innerY = trueArea.getY() + topPadding;
		int innerWidth = trueArea.getWidth() + leftPadding + rightPadding;
		int innerHeight = trueArea.getHeight() + topPadding + bottomPadding;

		return new Rectangle(innerX, innerY, innerWidth, innerHeight);
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

	@Deprecated
	boolean shouldDraw(Control child) {
		return true;
	}
}
