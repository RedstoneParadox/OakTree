package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.gui.screen.Screen;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public class LabelNode extends Node {
    public String text = "";

    public LabelNode setText(String value) {
        text = value;
        return this;
    }

    public LabelNode clear() {
        text = "";
        return this;
    }

    @Override
    public void draw(int int_1, int int_2, float float_1, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.draw(int_1, int_2, float_1, gui, offsetX, offsetY, containerWidth, containerHeight);
        float actualX = offsetX + x;
        float actualY = offsetY + y;
        gui.drawString(text, actualX, actualY, false);
    }
}
