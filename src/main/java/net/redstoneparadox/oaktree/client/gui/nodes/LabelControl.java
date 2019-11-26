package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

public class LabelControl extends Control<LabelControl> implements TextNode {
    private String text = "";
    private RGBAColor fontColor = RGBAColor.white();

    public LabelControl setText(String value) {
        text = value;
        return this;
    }

    public LabelControl clear() {
        text = "";
        return this;
    }

    /**
     * Sets the color of the font to be drawn. Note that transparency
     * is ignored here due to Minecraft internals.
     *
     * @param fontColor The RGBA Color
     */
    public LabelControl setFontColor(RGBAColor fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);
        drawString(text, gui, trueX, trueY, null, false, fontColor);
    }
}
