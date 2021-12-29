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
	}

	public @NotNull Control getFirst() {
		return first;
	}

	public void setSecond(@NotNull Control second) {
		this.second = second;
	}

	public @NotNull Control getSecond() {
		return second;
	}

	@Override
	public void zIndex(List<Control> controls) {
		super.zIndex(controls);
		controls.add(first);
		controls.add(second);
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		first.setup(client, gui);
		second.setup(client, gui);
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

		firstArea.setX(trueX);
		firstArea.setY(trueY);

		if (vertical) {
			firstArea.setWidth(area.getWidth());
			firstArea.setHeight(area.getHeight() - splitSize);
			secondArea.setX(trueX);
			secondArea.setY(trueY + firstArea.getHeight());
			secondArea.setWidth(area.getWidth());
			secondArea.setHeight(splitSize);
		}
		else {
			firstArea.setWidth(area.getWidth() - splitSize);
			firstArea.setHeight(area.getHeight());
			secondArea.setX(trueX + firstArea.getWidth());
			secondArea.setY(trueY);
			secondArea.setWidth(splitSize);
			secondArea.setHeight(area.getHeight());
		}

		if (first.isVisible()) first.preDraw(gui, firstArea.getX(), firstArea.getY(), firstArea.getWidth(), firstArea.getHeight(), mouseX, mouseY);
		if (second.isVisible()) second.preDraw(gui, secondArea.getX(), secondArea.getY(), secondArea.getWidth(), secondArea.getHeight(), mouseX, mouseY);
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);
		if (first.isVisible()) first.draw(matrices, mouseX, mouseY, deltaTime, gui);
		if (second.isVisible() && splitSize > 0) second.draw(matrices, mouseX, mouseY, deltaTime, gui);
	}
}
