package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.RenderHelper;
import io.github.redstoneparadox.oaktree.client.TextHelper;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.class_5348;
import net.minecraft.client.font.TextRenderer;
import io.github.redstoneparadox.oaktree.client.gui.Color;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@ApiStatus.ScheduledForRemoval
public interface TextControl<TC extends TextControl> {

    default List<String> wrapLines(String string, ControlGui gui, float width, int max, boolean withShadow) {
        List<String> strings;
        TextRenderer renderer = gui.getTextRenderer();
        if (withShadow) {
            strings = wrapLines(string, renderer, (int) (width - 1));
        }
        else {
            strings = wrapLines(string, renderer, (int) (width));
        }

        if (strings.size() > max) {
            strings = strings.subList(0, max);
        }

        return strings;
    }

    default List<String> wrapLines(String string, TextRenderer renderer, int width) {
        List<String> strings = new ArrayList<>();
        StringBuilder builder = new StringBuilder();

        for (char c: string.toCharArray()) {
            if (c == '\n') {
                builder.append('\n');
                strings.add(builder.toString());
                builder.setLength(0);
                continue;
            };
            if (renderer.getWidth(builder.toString() + c) > width) {
                strings.add(builder.toString());
                builder.setLength(0);
            }
            builder.append(c);
        }

        strings.add(builder.toString());
        return strings;
    }

    default String combine(List<String> strings, boolean addNewlines) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < strings.size() - 1; i += 1) {
            String line = strings.get(i);
            builder.append(line);
            if (addNewlines && !line.endsWith("\n")) builder.append('\n');
        }
        builder.append(strings.get(strings.size() - 1));
        return builder.toString();
    }

    default void drawString(MatrixStack matrices, String string, ControlGui gui, float x, float y, Anchor alignment, boolean withShadow, Color fontColor) {
        int redInt = (int) fontColor.red * 255;
        int greenInt = (int) fontColor.green * 255;
        int blueInt = (int) fontColor.blue * 255;

        int colorInt = redInt << 16 | greenInt << 8 | blueInt;

        TextRenderer textRenderer = gui.getTextRenderer();

        if (withShadow) textRenderer.drawWithShadow(matrices, string, x + 2, y + 2, colorInt);
        else textRenderer.draw(matrices, string, x + 2, y + 2, colorInt);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval
    default void drawHighlights(String string, TextRenderer renderer, int x, int y, Color highlightColor) {
        int width = renderer.getWidth(string);
        int height = renderer.fontHeight;

        RenderHelper.drawRectangle(x, y, width, height, highlightColor);
    }
}
