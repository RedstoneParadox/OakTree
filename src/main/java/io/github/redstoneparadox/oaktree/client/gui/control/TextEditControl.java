package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.util.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * @apiNote  Work in Progress!
 */
public class TextEditControl extends InteractiveControl<TextEditControl> implements TextControl<TextEditControl> {
    private final List<String> lines = new ArrayList<>();
    private int firstLine = 0;
    private final Cursor mainCursor = new Cursor(true);

    public boolean shadow = false;
    public RGBAColor fontColor = RGBAColor.white();
    public RGBAColor highlightColor = RGBAColor.blue();
    public int maxLines = 1;
    public int displayedLines = 1;
    public String text = "";

    public TypingListener<TextEditControl> onCharTyped = (character, control) -> character;
    public GuiFunction<TextEditControl> onFocused = (gui, control) -> {};
    public GuiFunction<TextEditControl> onFocusLost = (gui, control) -> {};

    private boolean focused = false;
    private boolean allSelected = false;

    private int cursorTicks = 0;
    private int backspaceTicks = 0;
    private int cursorPosition = 0;
    private boolean charAdded = false;

    private static final String RULER = createRulerString();

    public TextEditControl() {
        this.id = "text_edit";
    }

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

    /**
     * Set the maximum number of lines to be
     * displayed.
     *
     * @param displayedLines The max displayed lines.
     * @return The control itself.
     */
    public TextEditControl displayedLines(int displayedLines) {
        if (displayedLines > 0) this.displayedLines = displayedLines;
        return this;
    }

    private static String createRulerString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 200; i++) {
            builder.append("_");
        }

        return builder.toString();
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);
        updateFocused(gui);
        if (lines.isEmpty()) lines.add("");
        if (focused) {
            if (gui.getLastChar().isPresent()) {
                insertCharacter(gui.getLastChar().get(), gui);
                mainCursor.moveRight();
                charAdded = true;
            }

            Key pressed = gui.getKey();
            switch (pressed) {
                case NONE:
                    break;
                case BACKSPACE:
                    removeCharacter(gui);
                    mainCursor.moveLeft();
                    break;
                case ENTER:
                    newLine(gui);
                    mainCursor.moveRight();
                    break;
                case CTRL_A:
                    break;
                case COPY:
                    break;
                case CUT:
                    break;
                case PASTE:
                    break;
                case UP:
                    mainCursor.moveUp();
                    break;
                case DOWN:
                    mainCursor.moveDown();
                    break;
                case LEFT:
                    mainCursor.moveLeft();
                    break;
                case RIGHT:
                    mainCursor.moveRight();
                    break;
            }

            if (cursorTicks < 10) drawCursor(gui);
            cursorTicks += 1;
            if (cursorTicks >= 20) cursorTicks = 0;
        }
        else {
            allSelected = false;
        }
        drawText(gui);
    }

    private void updateFocused(OakTreeGUI gui) {
        if (gui.mouseButtonJustClicked("left")) {
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
    }

    private void newLine(OakTreeGUI gui) {
        if (lines.size() == maxLines) return;
        int oldLinesSize = lines.size();
        insertCharacter('\n', gui);
        if (oldLinesSize == lines.size()) lines.add("");
    }

    private void insertCharacter(char c, OakTreeGUI gui) {
        if (lines.isEmpty()) {
            lines.add(String.valueOf(c));
            return;
        }

        String text = combine(lines, true);

        int cursorPosition = 0;
        for (int i = 0; i < mainCursor.row; i += 1) {
            String line = lines.get(i);
            cursorPosition += line.length() + 1;
        }
        for (int i = 0; i < mainCursor.column; i+= 1) cursorPosition += 1;

        String newText = text.substring(0, cursorPosition) + c + text.substring(cursorPosition);
        lines.clear();
        lines.addAll(wrapLines(newText, gui, width, maxLines, shadow));
    }

    private void removeCharacter(OakTreeGUI gui) {
        String text = combine(lines, true);

        int cursorPosition = 0;
        for (int i = 0; i < mainCursor.row; i += 1) {
            String line = lines.get(i);
            cursorPosition += line.length() + 1;
        }
        for (int i = 0; i < mainCursor.column; i+= 1) cursorPosition += 1;

        String newText;
        if (text.length() <= 1) {
            newText = "";
        }
        else if (cursorPosition == text.length()) {
            newText = text.substring(0, text.length() - 1);
        }
        else {
            newText = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
        }

        lines.clear();
        lines.addAll(wrapLines(newText, gui, width, maxLines, shadow));
    }

    private void drawText(OakTreeGUI gui) {
        int lastLine;
        if (lines.size() < displayedLines) {
            lastLine = lines.size() - 1;
        }
        else {
            lastLine = firstLine + displayedLines;
        }

        for (int i = firstLine; i < lastLine; i += 1) {
            String line = lines.get(i);
            float lineY = trueY + i * gui.getTextRenderer().fontHeight;
            drawString(line, gui, trueX, lineY, ControlAnchor.CENTER, shadow, fontColor);
        }
    }

    private void drawCursor(OakTreeGUI gui) {
        if (lines.isEmpty()) {
            drawString("_", gui, trueX, trueY, ControlAnchor.CENTER, shadow, fontColor);
            return;
        }

        int actualRow = mainCursor.row - firstLine;
        String cursorLine = lines.get(mainCursor.row);
        TextRenderer renderer = gui.getTextRenderer();
        int cursorX = renderer.getStringWidth(cursorLine.substring(0, mainCursor.column));
        int cursorY = (int) ((trueY + 1) * actualRow * renderer.fontHeight);
        drawString("_", gui, cursorX, cursorY, ControlAnchor.CENTER, shadow, fontColor);
    }

    private class Cursor {
        int row = 0;
        int column = 0;
        final boolean main;

        Cursor(boolean main) {
            this.main = main;
        }

        Cursor(int row, int column, boolean main) {
            this.row = row;
            this.column = column;
            this.main = main;
        }

        void moveRight() {
            moveHorizontal(1);
        }

        void moveLeft() {
            moveHorizontal(-1);
        }

        void moveUp() {
            moveVertical(-1);
        }

        void moveDown() {
            moveVertical(1);
        }

        private void moveHorizontal(int amount) {
            column += amount;
            if (column < 0) {
                if (row == 0) column = 0;
                else {
                    row -= 1;
                    column = lines.get(row).length() - 1;
                }
            }
            if (column >= lines.get(row).length()) {
                if (row < lines.size() - 1) {
                    column = 0;
                    row += 1;
                }
                else {
                    column = lines.get(row).length();
                }
            }

            adjustFirstLine();
        }

        private void moveVertical(int amount) {
            row += amount;
            if (row < 0) row = 0;
            else if (row > lines.size() - 1) row = lines.size() - 1;

            if (column > lines.get(row).length()) column = lines.get(row).length();

            adjustFirstLine();
        }

        private void adjustFirstLine() {
            if (!main) return;

            if (row < firstLine) firstLine = row;
            else if (row >= firstLine + displayedLines) firstLine = row - displayedLines - 1;
        }
    }
}
