package io.github.redstoneparadox.oaktree.util;

import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Tooltip {
	private final List<Text> texts;
	private final Optional<TooltipData> data;
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

	public List<Text> getTexts() {
		return texts;
	}

	public Optional<TooltipData> getData() {
		return data;
	}

	public TooltipPositioner getPositioner() {
		return positioner;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
