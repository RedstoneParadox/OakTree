package io.github.redstoneparadox.oaktree.client.gui.control;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.Texts;
import net.minecraft.text.LiteralText;
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

    private boolean shadow = false;
    private RGBAColor fontColor = RGBAColor.white();
    private RGBAColor highlightColor = RGBAColor.blue();
    private int maxLines = 1;

    private TypingListener<TextEditControl> onCharTyped = (character, control) -> character;
    private GuiFunction<TextEditControl> onFocused = (gui, control) -> {};
    private GuiFunction<TextEditControl> onFocusLost = (gui, control) -> {};

    private Text text = new LiteralText("");
    private boolean focused = false;
    private boolean allSelected = false;

    private int ticks = 0;
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
        this.text = new LiteralText(text);
        return this;
    }

    /**
     * Sets the text of this TextEditControl.
     *
     * @param text The text.
     * @return The control itself.
     */
    public TextEditControl text(Text text) {
        this.text = text;
        return this;
    }

    /**
     * Clears the text.
     *
     * @return The control itself.
     */
    public TextEditControl clear() {
        text = new LiteralText("");
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
                focused = true;
                onFocused.invoke(gui, this);
                ticks = 0;
            }
            else if (focused) {
                focused = false;
                allSelected = false;
                onFocusLost.invoke(gui, this);
            }
            else {
                allSelected = false;
            }
        }
        if (focused) {
            ticks += 1;
            if (ticks >= 40) ticks = 0;

            if (gui.getLastChar().isPresent()) {
                Character character = onCharTyped.invoke(gui.getLastChar().get(), this);
                if (character != null) {
                    if (allSelected) {
                        clear();
                        text(character.toString());
                        allSelected = false;
                    }
                    else {
                        text.append(character.toString());
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
                        String string = text.getString();
                        if (!string.isEmpty()) text(string.substring(0, string.length() - 1));
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
            if (gui.isKeyPressed("enter")) text.append("\n");
            if (gui.isKeyPressed("copy") && allSelected) MinecraftClient.getInstance().keyboard.setClipboard(text.getString());
            if (gui.isKeyPressed("cut") && allSelected) {
                MinecraftClient.getInstance().keyboard.setClipboard(text.getString());
                clear();
            }
            if (gui.isKeyPressed("paste")) {
                String string = MinecraftClient.getInstance().keyboard.getClipboard();
                if (allSelected) text(string);
                else text.append(string);
            }
        }

        TextRenderer font = gui.getTextRenderer();
        List<Text> texts = Texts.wrapLines(text, (int) trueWidth, gui.getTextRenderer(), false, false);
        if (texts.size() > maxLines) {
            texts = texts.subList(0, maxLines);
        }

        int offset = 0;

        clear();
        for (Text text: texts) {
            String line = text.getString();
            this.text.append(line);
            float lineY = trueY + offset * 10;
            if (ticks < 20 && focused && offset + 1 == texts.size() && font.getStringWidth(line + "_") < trueWidth) drawString(line + "_", gui, trueX, lineY, ControlAnchor.CENTER, shadow, fontColor);
            else drawString(line, gui, trueX, lineY, ControlAnchor.CENTER, shadow, fontColor);
            if (allSelected) drawHighlights(line, gui, trueX, lineY, highlightColor);
            offset += 1;
        }
    }
}
