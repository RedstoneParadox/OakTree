package io.github.redstoneparadox.oaktree.client.gui.control;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import io.github.redstoneparadox.oaktree.client.gui.util.GuiFunction;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import io.github.redstoneparadox.oaktree.client.gui.util.TypingListener;

import java.util.List;

/**
 * @apiNote  Work in Progress!
 */
public class TextEditControl extends InteractiveControl<TextEditControl> implements TextControl<TextEditControl> {

    public boolean shadow = false;
    public RGBAColor fontColor = RGBAColor.white();
    public RGBAColor highlightColor = RGBAColor.blue();
    public int maxLines = 1;
    public String text = "";

    public TypingListener<TextEditControl> onCharTyped = (character, control) -> character;
    public GuiFunction<TextEditControl> onFocused = (gui, control) -> {};
    public GuiFunction<TextEditControl> onFocusLost = (gui, control) -> {};

    private boolean focused = false;
    private boolean allSelected = false;

    private int cursorTicks = 0;
    private int backspaceTicks = 0;

    /**
     * Sets a {@link TypingListener} to run when a character is typed.
     *
     * @param onCharTyped The function.
     * @return The control itself.
     */
    public TextEditControl onCharTyped(TypingListener<TextEditControl> onCharTyped) {
        this.onCharTyped = onCharTyped;
        return this;
    }

    /**
     * Sets a {@link GuiFunction} to run when the TextEditControl gains focus.
     *
     * @param onFocused The function.
     * @return The control itself.
     */
    public TextEditControl onFocused(GuiFunction<TextEditControl> onFocused) {
        this.onFocused = onFocused;
        return this;
    }

    /**
     * Sets a {@link GuiFunction} to run when the TextEditControl loses focus.
     *
     * @param onFocusLost The function.
     * @return The control itself.
     */
    public TextEditControl onFocusLost(GuiFunction<TextEditControl> onFocusLost) {
        this.onFocusLost = onFocusLost;
        return this;
    }

    /**
     * Sets the text of this TextEditControl.
     *
     * @param text The text.
     * @return The control itself.
     */
    public TextEditControl text(String text) {
        this.text = text;
        return this;
    }

    /**
     * Sets the text of this TextEditControl.
     *
     * @param text The text.
     * @return The control itself.
     */
    public TextEditControl text(Text text) {
        this.text = text.getString();
        return this;
    }

    /**
     * Clears the text.
     *
     * @return The control itself.
     */
    public TextEditControl clear() {
        text = "";
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
        if (gui.mouseButtonJustClicked("left")  ) {
            if (isMouseWithin && !focused) {
                gui.shouldCloseOnInventoryKey(false);
                focused = true;
                onFocused.invoke(gui, this);
                cursorTicks = 0;
            }
            else if (focused) {
                gui.shouldCloseOnInventoryKey(true);
                focused = false;
                allSelected = false;
                onFocusLost.invoke(gui, this);
            }
            else {
                allSelected = false;
            }
        }

        if (focused) {
            cursorTicks += 1;
            if (cursorTicks >= 40) cursorTicks = 0;

            if (gui.getLastChar().isPresent()) {
                Character character = onCharTyped.invoke(gui.getLastChar().get(), this);
                if (character != null) {
                    if (allSelected) {
                        clear();
                        text = character.toString();
                        allSelected = false;
                    }
                    else {
                        text = text + character.toString();
                    }
                }
            }

            if (gui.isBackspaceHeld()) {
                if (backspaceTicks == 0 || (backspaceTicks > 20 && backspaceTicks % 2 == 0)) {
                    if (allSelected) {
                        clear();
                        allSelected = false;
                    }
                    else {
                        if (!text.isEmpty()) text = text.substring(0, text.length() - 1);
                    }
                    backspaceTicks += 1;
                }
                else {
                    backspaceTicks += 1;
                }
            }
            else {
                backspaceTicks = 0;
            }

            if (gui.isKeyPressed("ctrl_a")) allSelected = true;
            if (gui.isKeyPressed("enter")) text = text + "\n";
            if (gui.isKeyPressed("copy") && allSelected) MinecraftClient.getInstance().keyboard.setClipboard(text);
            if (gui.isKeyPressed("cut") && allSelected) {
                MinecraftClient.getInstance().keyboard.setClipboard(text);
                clear();
            }
            if (gui.isKeyPressed("paste")) {
                String string = MinecraftClient.getInstance().keyboard.getClipboard();
                if (allSelected) text = string;
                else text = text.concat(string);
            }
        }
        else {
            cursorTicks = 0;
        }

        if (!text.isEmpty()) {
            List<String> lines = wrapLines(text, gui, width, maxLines, shadow);
            text = combine(lines);
            if (cursorTicks < 20) {
                TextRenderer font = gui.getTextRenderer();
                int index = lines.size() - 1;
                String last = lines.get(index);
                if (font.getStringWidth(last + "_") < height) lines.set(index, last + "_");
                else if (lines.size() < maxLines) lines.add("_");
            }

            int offset = 0;
            for (String line: lines) {
                float lineY = trueY + offset*10;
                drawString(line, gui, trueX, lineY, ControlAnchor.CENTER, shadow, fontColor);
                if (allSelected) drawHighlights(line, gui, trueX, lineY, highlightColor);
                offset += 1;
            }
        }
        else if (cursorTicks < 20 && focused) drawString("_", gui, trueX, trueY, ControlAnchor.CENTER, shadow, fontColor);
    }
}
