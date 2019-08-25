package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.gui.screen.Screen;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public class LabelNode extends Node<LabelNode> {
    public String text = "";
    public RGBAColor fontColor = RGBAColor.white();

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
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.draw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        ScreenVec anchorOffset = anchor.getOffset(containerWidth, containerHeight);
        ScreenVec drawOffset = alignment.getOffset(width, height);

        float trueX = x + anchorOffset.x + offsetX - drawOffset.x;
        float trueY = y + anchorOffset.y + offsetY - drawOffset.y;
        gui.drawString(text, trueX, trueY, false, fontColor);
    }
}
