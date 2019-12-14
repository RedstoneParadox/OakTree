package redstoneparadox.oaktree.client.gui.control;

import com.google.common.collect.Lists;
import net.minecraft.client.font.TextRenderer;
import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import redstoneparadox.oaktree.client.gui.util.GuiFunction;
import redstoneparadox.oaktree.client.gui.util.RGBAColor;
import redstoneparadox.oaktree.client.gui.util.TypingListener;
import redstoneparadox.oaktree.mixin.client.gui.screen.ScreenAccessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @apiNote  Work in Progress!
 */
public class TextEditControl extends InteractiveControl<TextEditControl> implements TextNode {

    private List<String> lines = Lists.newArrayList("");

    private TypingListener<TextEditControl> onCharTyped = (toType, node) -> toType;
    private GuiFunction<TextEditControl> onFocused = (gui, node) -> {};

    public TextEditControl onCharTyped(TypingListener<TextEditControl> listener) {
        onCharTyped = listener;
        return this;
    }

    public TextEditControl onFocused(GuiFunction<TextEditControl> listener) {
        onFocused = listener;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);
        int index = lines.size() - 1;
        String currentLine = lines.get(index);
        TextRenderer font = ((ScreenAccessor)gui).getFont();

        if (gui.getLastChar().isPresent()) {
            Character character = gui.getLastChar().get();
            if (font.getStringWidth(currentLine + character) < trueWidth) {
                currentLine = currentLine + character;
            }
        }

        boolean removed = false;
        if (gui.isKeyPressed("backspace")) {
            if (currentLine.length() > 1) {
                currentLine = currentLine.substring(0, currentLine.length() - 1);
            }
            else {
                currentLine = "";
                if (lines.size() > 1) {
                    lines.remove(index);
                    removed = true;
                }
            }
        }
        if (gui.isKeyPressed("enter")) lines.add("");

        if (!removed) lines.set(index, currentLine);

        int offset = 0;
        for (String line: lines) {
            drawString(line, gui, trueX, trueY + offset*10, ControlAnchor.CENTER, false, RGBAColor.red());
            offset += 1;
        }
    }
}
