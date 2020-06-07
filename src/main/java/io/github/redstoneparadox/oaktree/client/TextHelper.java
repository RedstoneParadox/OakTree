package io.github.redstoneparadox.oaktree.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.class_5348;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class TextHelper {
	public static int getFontHeight() {
		return MinecraftClient.getInstance().textRenderer.fontHeight;
	}

	public static int getWidth(String string) { return MinecraftClient.getInstance().textRenderer.getWidth(string); }

	public static int getWidth(Text text) {
		return MinecraftClient.getInstance().textRenderer.getWidth(text);
	}

	public static Text combine(List<Text> texts, boolean newline) {
		if (texts.isEmpty()) return new LiteralText("");

		MutableText text = texts.get(0).shallowCopy();

		for (int index = 1; index < texts.size(); index += 1) {
			if (newline) text.append(new LiteralText("\n"));
			text.append(texts.get(index));
		}

		return text;
	}

	public static List<class_5348> wrapText(class_5348 text, int width, int start, int max, boolean shadow, boolean pastEnd) {
		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

		List<class_5348> lines;

		if (shadow) {
			lines = textRenderer.wrapLines(text, width - 1);
		} else {
			lines = textRenderer.wrapLines(text, width);
		}

		if (!pastEnd && start + max >= lines.size()) {
			start = lines.size() - max;
		}

		if (start + max <= lines.size()) {
			return lines.subList(start, start + max);
		} else if (start < lines.size()) {
			return lines.subList(start, lines.size());
		}

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
