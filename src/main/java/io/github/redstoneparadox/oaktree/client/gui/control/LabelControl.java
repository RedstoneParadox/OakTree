package io.github.redstoneparadox.oaktree.client.gui.control;

import net.minecraft.client.util.Texts;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;

import java.util.List;

public class LabelControl extends Control<LabelControl> implements TextControl<LabelControl> {
    private boolean shadow = false;
    private RGBAColor fontColor = RGBAColor.white();
    private int maxLines = 1;

    private Text text = new LiteralText("");

    /**
     * Sets the text for this LabelControl
     * to display.
     *
     * @param text The text to display
     * @return The control itself.
     */
    public LabelControl text(String text) {
        this.text = new LiteralText(text);
        return this;
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

    /**
     * Clears the LabelControl
     *
     * @return The control itself.
     */
    public LabelControl clear() {
        text = new LiteralText("");
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
    public LabelControl fontColor(RGBAColor fontColor) {
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

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);
        List<Text> texts = Texts.wrapLines(text, (int) trueWidth, gui.getTextRenderer(), false, false);
        if (texts.size() > maxLines) {
            texts = texts.subList(0, maxLines);
        }

        int offset = 0;
        for (Text text: texts) {
            drawString(text.getString().trim(), gui, trueX, trueY + offset*10, ControlAnchor.CENTER, shadow, fontColor);
            offset += 1;
        }
    }
}
