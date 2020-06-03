package io.github.redstoneparadox.oaktree.client.gui.control;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.class_5348;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import io.github.redstoneparadox.oaktree.client.geometry.Vector2D;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    default void drawString(MatrixStack matrices, String string, ControlGui gui, float x, float y, ControlAnchor alignment, boolean withShadow, RGBAColor fontColor) {
        int redInt = (int) fontColor.redChannel * 255;
        int greenInt = (int) fontColor.greenChannel * 255;
        int blueInt = (int) fontColor.blueChannel * 255;

        int colorInt = redInt << 16 | greenInt << 8 | blueInt;

        TextRenderer textRenderer = gui.getTextRenderer();

        if (withShadow) textRenderer.drawWithShadow(matrices, string, x + 2, y + 2, colorInt);
        else textRenderer.draw(matrices, string, x + 2, y + 2, colorInt);
    }

    default Text combine(List<Text> texts) {
        if (texts.isEmpty()) return new LiteralText("");

        MutableText text = texts.get(0).shallowCopy();
        for (int index = 1; index < texts.size(); index += 1) {
            text.append(texts.get(index));
        }

        return text;
    }

    default List<class_5348> wrapText(class_5348 text, @NotNull TextRenderer renderer, int width, int start, int max, boolean shadow) {
        List<class_5348> lines;
        if (shadow) {
            lines = renderer.wrapLines(text, width - 1);
        }
        else {
            lines = renderer.wrapLines(text, width);
        }

        if (start + max <= lines.size()) return lines.subList(start, max);
        else if (start < lines.size()) return lines.subList(start, lines.size());
        return lines;
    }

    default void drawText(MatrixStack matrices, class_5348 text, TextRenderer renderer, int x, int y, boolean shadow, RGBAColor fontColor) {
        int redInt = (int) fontColor.redChannel * 255;
        int greenInt = (int) fontColor.greenChannel * 255;
        int blueInt = (int) fontColor.blueChannel * 255;

        int colorInt = redInt << 16 | greenInt << 8 | blueInt;

        if (shadow) renderer.drawWithShadow(matrices, text, x + 2, y + 2, colorInt);
        else renderer.draw(matrices, text, x + 2, y + 2, colorInt);
    }

    default void drawHighlights(String string, TextRenderer renderer, int x, int y, RGBAColor highlightColor) {
        int width = renderer.getWidth(string);
        int height = renderer.fontHeight;

        Vector2D vert1 = new Vector2D(x + 1, y + 1);
        Vector2D vert2 = new Vector2D(x + 1, y + 3 + height);
        Vector2D vert3 = new Vector2D(x + 3 + width, y + 3 + height);
        Vector2D vert4 = new Vector2D(x + 3 + width, y + 1);

        float red = highlightColor.redChannel * 255;
        float green = highlightColor.greenChannel * 255;
        float blue = highlightColor.blueChannel * 255;
        float alpha = highlightColor.alphaChannel * 255;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();
        RenderSystem.color4f(red, green, blue, alpha);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        builder.begin(7, VertexFormats.POSITION);
        builder.vertex(vert1.x, vert1.y, 0.0).next();
        builder.vertex(vert2.x, vert2.y, 0.0).next();
        builder.vertex(vert3.x, vert3.y, 0.0).next();
        builder.vertex(vert4.x, vert4.y, 0.0).next();
        tessellator.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }
}
