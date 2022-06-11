package io.github.redstoneparadox.oaktree.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class TextHelper {
	public static int getFontHeight() {
		return MinecraftClient.getInstance().textRenderer.fontHeight;
	}

	public static int getWrappedWidth(Text text) {
		String[] lines = text.getString().split("\n");
		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		int width = 0;

		for (String line: lines) {
			width = Math.max(width, textRenderer.getWidth(line));
		}

		return width;
	}

	public static int getWrappedHeight(Text text) {
		String[] lines = text.getString().split("\n");
		return lines.length * getFontHeight();
	}

	public static int getWidth(String string) { return MinecraftClient.getInstance().textRenderer.getWidth(string); }

	public static Text combine(List<Text> texts, boolean newline) {
		if (texts.isEmpty()) return Text.literal("");

		MutableText text = texts.get(0).copy();

		for (int index = 1; index < texts.size(); index += 1) {
			if (newline) text.append("\n");
			text.append(texts.get(index));
		}

		return text;
	}

	public static List<OrderedText> wrapText(Text text, int width, int start, int max, boolean shadow, boolean pastEnd) {
		if (text.getString().isEmpty()) return new ArrayList<>();

		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		List<OrderedText> lines = textRenderer.wrapLines(text, shadow ? width - 1 : width);
		int end = Math.min(max + start, lines.size());

		lines = lines.subList(start, end);

		return lines;
	}

	public static List<String> wrapLines(String string, float width, int max, boolean withShadow) {
		List<String> strings;
		if (withShadow) {
			strings = wrapLines(string, (int) (width - 1));
		}
		else {
			strings = wrapLines(string, (int) (width));
		}

		if (strings.size() > max) {
			strings = strings.subList(0, max);
		}

		return strings;
	}

	private static List<String> wrapLines(String string, int width) {
		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
		List<String> strings = new ArrayList<>();
		StringBuilder builder = new StringBuilder();

		for (char c: string.toCharArray()) {
			if (c == '\n') {
				builder.append('\n');
				strings.add(builder.toString());
				builder.setLength(0);
				continue;
			};
			if (textRenderer.getWidth(builder.toString() + c) > width) {
				strings.add(builder.toString());
				builder.setLength(0);
			}
			builder.append(c);
		}

		strings.add(builder.toString());
		return strings;
	}

	public static String combineStrings(List<String> strings, boolean addNewlines) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < strings.size() - 1; i += 1) {
			String line = strings.get(i);
			builder.append(line);
			if (addNewlines && !line.endsWith("\n")) builder.append('\n');
		}
		builder.append(strings.get(strings.size() - 1));
		return builder.toString();
	}
}
