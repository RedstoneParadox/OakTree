package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.class_5348;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import io.github.redstoneparadox.oaktree.client.gui.Color;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LabelControl extends Control<LabelControl> implements TextControl<LabelControl> {
    public @NotNull Text text = LiteralText.EMPTY;
    public boolean shadow = false;
    public Color fontColor = Color.WHITE;
    public int maxLines = 1;
    public boolean resizable = false;

    private @Nullable TextRenderer renderer = null;

    public LabelControl() {
        this.id = "label";
    }

    /**
     * Sets the text for this LabelControl
     * to display.
     *
     * @param text The text to display
     * @return The control itself.
     */
    public LabelControl text(String text) {
        return this.text(new LiteralText(text));
    }

    /**
     * Sets the text for this LabelControl
     * to display.
     *
     * @param text The text to display
     * @return The control itself.
     */
    public LabelControl text(Text text) {
        this.text = text;
        return this;
    }

    public LabelControl text(List<Text> texts) {
        if (resizable && renderer != null) {
            for (Text text: texts) {
                this.area.width = Math.max(this.area.width, renderer.getWidth(text));
            }

            this.area.width += 4;
            area.height = renderer.fontHeight * texts.size() + 4;
        }

        this.text = combine(texts);
        return this;
    }

    /**
     * Clears the LabelControl
     *
     * @return The control itself.
     */
    public LabelControl clear() {
        this.text = LiteralText.EMPTY;
        return this;
    }

    /**
     * Sets whether the text should be drawn with a shadow.
     *
     * @param shadow The value.
     * @return The control itself.
     */
    public LabelControl shadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    /**
     * Sets the color of the font to be drawn. Note that transparency
     * is ignored here due to Minecraft internals.
     *
     * @param fontColor The RGBA Color
     * @return The control itself.
     */
    public LabelControl fontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Sets the maximum number of lines.
     *
     * @param maxLines The max number of lines.
     * @return The control itself.
     */
    public LabelControl maxLines(int maxLines) {
        if (maxLines > 0) this.maxLines = maxLines;
        return this;
    }

    public LabelControl resizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    @Override
    public void setup(MinecraftClient client, ControlGui gui) {
        super.setup(client, gui);
        this.renderer = gui.getTextRenderer();
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        if (!visible) return;
        super.draw(matrices, mouseX, mouseY, deltaTime, gui);

        if (renderer != null) {
            List<class_5348> lines = wrapText(text, renderer, area.width, 0, maxLines, shadow);
            for (class_5348 line: lines) {
                drawText(matrices, line, renderer, trueX, trueY, shadow, fontColor);
            }
        }
    }
}
