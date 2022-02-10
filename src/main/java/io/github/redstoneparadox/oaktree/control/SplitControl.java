package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Rectangle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SplitControl extends Control {
	protected int splitSize = 0;
	protected boolean vertical = false;
	protected @NotNull Control first = new Control();
	protected @NotNull Control second = new Control();

	private final Rectangle firstArea = new Rectangle(trueX, trueY, area.getWidth(), area.getHeight());
	private final Rectangle secondArea = new Rectangle(trueX + firstArea.getWidth(), trueY, 0, area.getHeight());

	public SplitControl() {
		this.id = "split";
	}

	public void setSplitSize(int splitSize) {
		this.splitSize = splitSize;
	}

	public int getSplitSize() {
		return splitSize;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setFirst(@NotNull Control first) {
		this.first = first;
		first.setParent(this);
		markDirty();
	}

	public @NotNull Control getFirst() {
		return first;
	}

	public void setSecond(@NotNull Control second) {
		this.second = second;
		second.setParent(this);
		markDirty();
	}

	public @NotNull Control getSecond() {
		return second;
	}

	@Override
	protected void updateTree(List<Control> zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);

		if (vertical) {
			int bottomY = trueArea.getY() + splitSize;
			int topHeight = trueArea.getHeight() - splitSize;

			if (first.visible) first.updateTree(zIndexedControls, trueArea.getX(), trueArea.getY(), trueArea.getWidth(), topHeight);
			if (second.visible) second.updateTree(zIndexedControls, trueArea.getX(), bottomY, trueArea.getWidth(), splitSize);
		} else {
			int rightX = trueArea.getX() + splitSize;
			int leftWidth = trueArea.getWidth() - splitSize;

			if (first.visible) first.updateTree(zIndexedControls, trueArea.getX(), trueArea.getY(), leftWidth, trueArea.getHeight());
			if (second.visible) second.updateTree(zIndexedControls, rightX, trueArea.getY(), splitSize, trueArea.getHeight());
		}
	}
}
