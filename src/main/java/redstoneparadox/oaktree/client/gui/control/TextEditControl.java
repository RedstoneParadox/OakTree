package redstoneparadox.oaktree.client.gui.control;

import com.google.common.collect.Lists;
import net.minecraft.client.font.TextRenderer;
import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import redstoneparadox.oaktree.client.gui.util.GuiFunction;
import redstoneparadox.oaktree.client.gui.util.RGBAColor;
import redstoneparadox.oaktree.client.gui.util.TypingListener;
import redstoneparadox.oaktree.mixin.client.gui.screen.ScreenAccessor;

import java.util.List;

/**
 * @apiNote  Work in Progress!
 */
public class TextEditControl extends InteractiveControl<TextEditControl> implements TextControl<TextEditControl> {

    private boolean shadow = false;
    private RGBAColor fontColor = RGBAColor.white();
    private int maxLines = 1;

    private TypingListener<TextEditControl> onCharTyped = (toType, control) -> toType;
    private GuiFunction<TextEditControl> onFocused = (gui, control) -> {};
    private GuiFunction<TextEditControl> onFocusLost = (gui, control) -> {};

    private List<String> lines = Lists.newArrayList("");
    private boolean focused = false;

    public TextEditControl onCharTyped(TypingListener<TextEditControl> onCharTyped) {
        this.onCharTyped = onCharTyped;
        return this;
    }

    public TextEditControl onFocused(GuiFunction<TextEditControl> onFocused) {
        this.onFocused = onFocused;
        return this;
    }

    public TextEditControl onFocusLost(GuiFunction<TextEditControl> onFocusLost) {
        this.onFocusLost = onFocusLost;
        return this;
    }

    /**
     * Sets whether the text should be drawn with a shadow.
     *
     * @param shadow The value.
     * @return The control itself.
     */
    public TextEditControl shadow(boolean shadow) {
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
    public TextEditControl fontColor(RGBAColor fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Sets the maximum number of lines.
     *
     * @param maxLines The max number of lines.
     * @return The control itself.
     */
    public TextEditControl maxLines(int maxLines) {
        if (maxLines > 0) this.maxLines = maxLines;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);
        int index = lines.size() - 1;
        String currentLine = lines.get(index);
        TextRenderer font = ((ScreenAccessor)gui).getFont();

        if (gui.mouseButtonJustClicked("left")) {
            if (isMouseWithin) {
                focused = true;
                onFocused.invoke(gui, this);
            }
            else  {
                focused = false;
                onFocusLost.invoke(gui, this);
            }
        }

        if (focused) {
            if (gui.getLastChar().isPresent()) {
                Character character = onCharTyped.invoke(gui.getLastChar().get(), this);
                if (character != null || font.getStringWidth(currentLine + character) < trueWidth) {
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
            if (gui.isKeyPressed("enter") && lines.size() < maxLines) lines.add("");

            if (!removed) lines.set(index, currentLine);
        }

        int offset = 0;
        for (String line: lines) {
            drawString(line, gui, trueX, trueY + offset*10, ControlAnchor.CENTER, shadow, fontColor);
            offset += 1;
        }
    }
}
