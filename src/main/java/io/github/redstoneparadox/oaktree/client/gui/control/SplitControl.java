package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.geometry.Rectangle;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SplitControl extends Control<SplitControl> {
	private int splitSize = 0;
	private boolean vertical = false;
	private @NotNull Control<?> first = new Control<>();
	private @NotNull Control<?> second = new Control<>();

	private final Rectangle firstArea = new Rectangle(trueX, trueY, area.width, area.height);
	private final Rectangle secondArea = new Rectangle(trueX + firstArea.width, trueY, 0, area.height);

	public SplitControl() {
		this.id = "split";
	}

	public SplitControl splitSize(int splitSize) {
		this.splitSize = splitSize;
		return this;
	}

	public int getSplitSize() {
		return splitSize;
	}

	public SplitControl vertical(boolean vertical) {
		this.vertical = vertical;
		return this;
	}

	public boolean isVertical() {
		return vertical;
	}

	public SplitControl first(@NotNull Control<?> first) {
		this.first = first;
		return this;
	}

	public @NotNull Control<?> getFirst() {
		return first;
	}

	public SplitControl second(@NotNull Control<?> second) {
		this.second = second;
		return this;
	}

	public @NotNull Control<?> getSecond() {
		return second;
	}

	@Override
	public void zIndex(List<Control<?>> controls) {
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

		firstArea.x = trueX;
		firstArea.y = trueY;

		if (vertical) {
			firstArea.width = area.width;
			firstArea.height = area.height - splitSize;
			secondArea.x = trueX;
			secondArea.y = trueY + firstArea.height;
			secondArea.width = area.width;
			secondArea.height = splitSize;
		}
		else {
			firstArea.width = area.width - splitSize;
			firstArea.height = area.height;
			secondArea.x = trueX + firstArea.width;
			secondArea.y = trueY;
			secondArea.width = splitSize;
			secondArea.height = area.height;
		}

		if (first.visible) first.preDraw(gui, firstArea.x, firstArea.y, firstArea.width, firstArea.height, mouseX, mouseY);
		if (second.visible) second.preDraw(gui, secondArea.x, secondArea.y, secondArea.width, secondArea.height, mouseX, mouseY);
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);
		if (first.visible) first.draw(matrices, mouseX, mouseY, deltaTime, gui);
		if (second.visible && splitSize > 0) second.draw(matrices, mouseX, mouseY, deltaTime, gui);
	}
}
