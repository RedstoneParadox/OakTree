package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Rectangle;
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
		child.setParent(this);
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
			addChild(function.apply(i));
		}
	}

	/**
	 * Gets the child at the specified index.
	 *
	 * @param index The index.
	 * @return The child control.
	 */
	public @Nullable Control getChild(int index) {
		if (index < children.size()) {
			return children.get(index);
		}

		return null;
	}

	/**
	 * Removes the child at the specified index.
	 *
	 * @param index The index.
	 * @return The child.
	 */
	public @Nullable Control removeChild(int index) {
		markDirty();
		return children.remove(index);
	}

	/**
	 * Removes all children from this panel.
	 */
	public void clearChildren() {
		children.clear();
		markDirty();
	}

	/**
	 * @return The number of children this panel has.
	 */
	public int getChildrenCount() {
		return children.size();
	}

	@Override
	protected void updateTree(List<Control> orderedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(orderedControls, containerX, containerY, containerWidth, containerHeight);

		for (int i = 0; i < children.size(); i++) {
			Control child = children.get(i);
			Rectangle childArea = getChildArea(i);

			if (child.visible) child.updateTree(orderedControls, childArea.getX(), childArea.getY(), childArea.getWidth(), childArea.getHeight());
		}
	}

	@Override
	protected void cleanup() {
		super.cleanup();

		children.forEach(Control::cleanup);
	}

	protected Rectangle getChildArea(int index) {
		// TODO: Figure out some way to cache the result
		int innerX = trueArea.getX() + leftPadding;
		int innerY = trueArea.getY() + topPadding;
		int innerWidth = trueArea.getWidth() - leftPadding - rightPadding;
		int innerHeight = trueArea.getHeight() - topPadding - bottomPadding;

		return new Rectangle(innerX, innerY, innerWidth, innerHeight);
	}
}
