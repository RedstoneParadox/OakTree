package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

public class LabelNode extends Node<LabelNode> implements TextNode {
    private String text = "";
    private RGBAColor fontColor = RGBAColor.white();

    public LabelNode setText(String value) {
        text = value;
        return this;
    }

    public LabelNode clear() {
        text = "";
        return this;
    }

    /**
     * Sets the color of the font to be drawn. Note that transparency
     * is ignored here due to Minecraft internals.
     *
     * @param fontColor The RGBA Color
     */
    public LabelNode setFontColor(RGBAColor fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        super.draw(mouseX, mouseY, deltaTime, gui);
        drawString(text, gui, trueX, trueY, null, false, fontColor);
    }
}
