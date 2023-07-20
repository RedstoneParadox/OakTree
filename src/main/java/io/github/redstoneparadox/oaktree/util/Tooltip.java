package io.github.redstoneparadox.oaktree.util;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Tooltip {
	private List<Text> texts;
	private Optional<TooltipData> data;
	private final TooltipPositioner positioner;
	private boolean visible = false;
	private int x = 0;
	private int y = 0;

	public Tooltip(List<Text> texts, TooltipData data, TooltipPositioner positioner) {
		this.texts = texts;
		this.data = Optional.of(data);
		this.positioner = positioner;
	}

	public Tooltip(List<Text> texts, TooltipPositioner positioner) {
		this.texts = texts;
		this.data = Optional.empty();
		this.positioner = positioner;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public boolean isEmpty() {
		return texts.isEmpty();
	}

	public void setTexts(List<Text> texts) {
		this.texts = texts;
	}

	public void setData(Optional<TooltipData> data) {
		this.data = data;
	}

	public List<TooltipComponent> getComponents() {
		List<TooltipComponent> list = texts.stream().map(Text::asOrderedText).map(TooltipComponent::of).collect(Collectors.toList());
		data.ifPresent(datax -> list.add(1, TooltipComponent.of(datax)));

		return list;
	}

	public TooltipPositioner getPositioner() {
		return positioner;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
