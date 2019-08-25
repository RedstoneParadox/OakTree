package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.NodeAlignment;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import net.redstoneparadox.oaktree.mixin.client.gui.screen.ScreenAccessor;

public interface TextNode {

    default void drawString(String string, OakTreeGUI gui, float x, float y, NodeAlignment alignment, boolean withShadow, RGBAColor fontColor) {
        int redInt = (int) fontColor.redChannel * 255;
        int greenInt = (int) fontColor.greenChannel * 255;
        int blueInt = (int) fontColor.blueChannel * 255;

        int colorInt = redInt << 16 | greenInt << 8 | blueInt;

        if (gui instanceof Screen) {
            TextRenderer font = ((ScreenAccessor)gui).getFont();

            if (withShadow) {
                font.drawWithShadow(string, x, y, colorInt);
            }
            else {
                font.draw(string, x, y, colorInt);
            }
        }
    }
}
