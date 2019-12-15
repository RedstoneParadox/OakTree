package redstoneparadox.oaktree.client.gui.control;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import redstoneparadox.oaktree.client.gui.util.RGBAColor;
import redstoneparadox.oaktree.mixin.client.gui.screen.ScreenAccessor;

public interface TextControl<TC extends TextControl> {

    default void drawString(String string, OakTreeGUI gui, float x, float y, ControlAnchor alignment, boolean withShadow, RGBAColor fontColor) {
        int redInt = (int) fontColor.redChannel * 255;
        int greenInt = (int) fontColor.greenChannel * 255;
        int blueInt = (int) fontColor.blueChannel * 255;

        int colorInt = redInt << 16 | greenInt << 8 | blueInt;

        if (gui instanceof Screen) {
            TextRenderer font = ((ScreenAccessor)gui).getFont();

            if (withShadow) {
                font.drawWithShadow(string, x + 1, y + 1, colorInt);
            }
            else {
                font.draw(string, x + 1, y + 1, colorInt);
            }
        }
    }
}
