package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.RenderHelper;
import io.github.redstoneparadox.oaktree.client.gui.Color;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.TypingListener;
import io.github.redstoneparadox.oaktree.util.TriFunction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @apiNote  Work in Progress!
 */
public class TextEditControl extends InteractiveControl<TextEditControl> implements TextControl<TextEditControl> {
	private final List<String> lines = new ArrayList<>();
	private int firstLine = 0;
	private final Cursor cursor = new Cursor(true);
	private final Selection selection = new Selection();
	private int backspaceTicks = 0;
	private int enterTicks = 0;
	private int arrowKeyTicks = 0;
	private boolean copied = false;
	private int pasteTicks = 0;
	private int cursorTicks = 0;
	private boolean focused = false;
	private String text = "";
	private boolean updateText = false;

	protected boolean shadow = false;
	protected Color fontColor = Color.WHITE;
	protected Color highlightColor = Color.BLUE;
	protected int maxLines = 1;
	protected int displayedLines = 1;

	protected TriFunction<ControlGui, TextEditControl, Character, @Nullable Character> onCharTyped = (gui, control, character) -> character;
	protected BiConsumer<ControlGui, TextEditControl> onFocused = (gui, control) -> {};
	protected BiConsumer<ControlGui, TextEditControl> onFocusLost = (gui, control) -> {};
	protected BiConsumer<ControlGui, TextEditControl> onEnter = (gui, control) -> {};


	public TextEditControl() {
		this.id = "text_edit";
	}

	/**
	 * Sets a {@link TriFunction} to run when a character is typed.
	 *
	 * @param onCharTyped The function.
	 * @return The control itself.
	 */
	public TextEditControl onCharTyped(TriFunction<ControlGui, TextEditControl, Character, @Nullable Character> onCharTyped) {
		this.onCharTyped = onCharTyped;
		return this;
	}

	/**
	 * Sets a {@link TypingListener} to run when a character is typed.
	 *
	 * @param onCharTyped The function.
	 * @return The control itself.
	 */
	@Deprecated
	@ApiStatus.ScheduledForRemoval
	public TextEditControl onCharTyped(TypingListener<TextEditControl> onCharTyped) {
		this.onCharTyped = ((gui, control, character) -> onCharTyped.invoke(character, control));
		return this;
	}

	/**
	 * Sets a {@link BiConsumer} to run when the TextEditControl gains focus.
	 *
	 * @param onFocused The function.
	 * @return The control itself.
	 */
	public TextEditControl onFocused(BiConsumer<ControlGui, TextEditControl> onFocused) {
		this.onFocused = onFocused;
		return this;
	}

	/**
	 * Sets a {@link BiConsumer} to run when the TextEditControl loses focus.
	 *
	 * @param onFocusLost The function.
	 * @return The control itself.
	 */
	public TextEditControl onFocusLost(BiConsumer<ControlGui, TextEditControl> onFocusLost) {
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
		updateText = true;
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
		updateText = true;
		return this;
	}

	public String getText() {
		return combine(lines, true);
	}

	/**
	 * Clears the text.
	 *
	 * @return The control itself.
	 */
	public TextEditControl clear() {
		this.text("");
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

	public boolean isShadow() {
		return shadow;
	}

	/**
	 * Sets the color of the font to be drawn. Note that transparency
	 * is ignored here due to Minecraft internals.
	 *
	 * @param fontColor The RGBA Color
	 * @return The control itself.
	 */
	public TextEditControl fontColor(Color fontColor) {
		this.fontColor = fontColor;
		return this;
	}

	public Color getFontColor() {
		return this.fontColor;
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

	public int getMaxLines() {
		return maxLines;
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

	public int getDisplayedLines() {
		return displayedLines;
	}

	// Abandon hope all Ye who enter here!
	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);
		updateFocused(gui);
		if (lines.isEmpty()) lines.add("");
		try {
			if (focused) {
				if (updateText) {
					lines.clear();
					lines.addAll(wrapLines(text, gui, area.width, maxLines, shadow));
					selection.cancel();
					cursor.toEnd();
					updateText = false;
					text = "";
				}

				if (gui.getLastChar().isPresent()) {
					int oldSize = lines.size();
					if (selection.active) {
						deleteSelection(gui);
						cursor.toSelectionStart();
					}

					@Nullable Character character = onCharTyped.apply(gui, this, gui.getLastChar().get());

					if (character != null) {
						insertCharacter(character, gui);
						cursor.moveRight();
						if (oldSize + 1 == lines.size()) cursor.moveRight();
					}
				}
				long handle = MinecraftClient.getInstance().getWindow().getHandle();

				if (upKey(handle)) {
					if (arrowKeyTicks == 0 || arrowKeyTicks >= 20 && arrowKeyTicks % 2 == 0) {
						if (!selection.active && shift(handle)) selection.startHighlighting();
						cursor.moveUp();
						selection.moveToCursor(handle);
					}
					arrowKeyTicks += 1;
				}
				else if (downKey(handle)) {
					if (arrowKeyTicks == 0 || arrowKeyTicks > 20 && arrowKeyTicks % 2 == 0) {
						if (!selection.active && shift(handle)) selection.startHighlighting();
						cursor.moveDown();
						selection.moveToCursor(handle);
					}
					arrowKeyTicks += 1;
				}
				else if (leftKey(handle)) {
					if (arrowKeyTicks == 0 || arrowKeyTicks > 20 && arrowKeyTicks % 2 == 0) {
						if (!selection.active && shift(handle)) selection.startHighlighting();
						cursor.moveLeft();
						selection.moveToCursor(handle);
					}
					arrowKeyTicks += 1;
				}
				else if (rightKey(handle)) {
					if (arrowKeyTicks == 0 || arrowKeyTicks > 20 && arrowKeyTicks % 2 == 0) {
						if (!selection.active && shift(handle)) selection.startHighlighting();
						cursor.moveRight();
						selection.moveToCursor(handle);
					}
					arrowKeyTicks += 1;
				}
				else {
					arrowKeyTicks = 0;
				}

				if (backspace(handle)) {
					if (backspaceTicks == 0 || backspaceTicks > 20 && backspaceTicks % 2 == 0) {
						if (selection.active) {
							deleteSelection(gui);
							cursor.toSelectionStart();
						}
						else if (cursor.row != 0 || cursor.column != 0) {
							removeCharacter(gui);
							cursor.moveLeft();
						}
					}
					backspaceTicks += 1;
				}
				else {
					backspaceTicks = 0;
				}

				if (enter(handle)) {
					if (enterTicks == 0 || enterTicks > 20 && enterTicks % 2 == 0) {
						onEnter.accept(gui, this);
						if (selection.active) {
							deleteSelection(gui);
							cursor.toSelectionStart();
						}

						newLine(gui);
						cursor.moveRight();
						cursor.moveRight();
					}
					enterTicks += 1;
				}
				else {
					enterTicks = 0;
				}

				if (ctrlA(handle)) {
					selection.all();
					cursor.toEnd();
				}

				if (copy(handle) && selection.active) {
					if (!copied) {
						String selectedText = getSelection();
						if (selectedText != null) MinecraftClient.getInstance().keyboard.setClipboard(selectedText);
					}
					copied = true;
				} else copied = false;

				if (cut(handle)) {
					if (!copied) {
						String selectedText = getSelection();
						if (selectedText != null) MinecraftClient.getInstance().keyboard.setClipboard(selectedText);
						deleteSelection(gui);
						cursor.toSelectionStart();
					}
					copied = true;
				} else copied = false;

				if (paste(handle)) {
					if (pasteTicks == 0 || pasteTicks > 20 && pasteTicks % 2 == 0) {
						if (selection.active) {
							deleteSelection(gui);
							cursor.toSelectionStart();
						}
						String st = MinecraftClient.getInstance().keyboard.getClipboard();
						int oldSize = lines.size();
						insertString(st, gui);
						for (int i = 0; i < st.length(); i += 1) cursor.moveRight();
						if (oldSize < lines.size()) cursor.moveRight();
					}
					pasteTicks += 1;
				}
				else {
					pasteTicks = 0;
				}

				if (cursorTicks < 10) drawCursor(matrices, gui);
				cursorTicks += 1;
				if (cursorTicks >= 20) cursorTicks = 0;
			}
			else {
				selection.cancel();
				backspaceTicks = 0;
				enterTicks = 0;
			}
			if (lines.isEmpty() || (lines.size() == 1 && lines.get(0).isEmpty())) {
				selection.cancel();
			}
			drawText(matrices, gui);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean upKey(long handle) {
		return InputUtil.isKeyPressed(handle, 265);
	}

	private boolean downKey(long handle) {
		return InputUtil.isKeyPressed(handle, 264);
	}

	private boolean leftKey(long handle) {
		return InputUtil.isKeyPressed(handle, 263);
	}

	private boolean rightKey(long handle) {
		return InputUtil.isKeyPressed(handle, 262);
	}

	private boolean backspace(long handle) {
		return InputUtil.isKeyPressed(handle, 259);
	}

	private boolean enter(long handle) {
		return InputUtil.isKeyPressed(handle, 257);
	}

	private boolean ctrlA(long handle) {
		return InputUtil.isKeyPressed(handle, 65) && Screen.isSelectAll(65);
	}

	private boolean copy(long handle) {
		return InputUtil.isKeyPressed(handle, 67) && Screen.isCopy(67);
	}

	private boolean cut(long handle) {
		return InputUtil.isKeyPressed(handle, 88) && Screen.isCut(88);
	}

	private boolean paste(long handle) {
		return InputUtil.isKeyPressed(handle, 86) && Screen.isPaste(86);
	}

	private boolean shift(long handle) {
		return InputUtil.isKeyPressed(handle, 344) || InputUtil.isKeyPressed(handle, 340);
	}

	private void updateFocused(ControlGui gui) {
		if (gui.mouseButtonJustClicked("left")) {
			if (isMouseWithin && !focused) {
				focused = true;
				onFocused.accept(gui, this);
				cursorTicks = 0;
			}
			else if (focused) {
				focused = false;
				selection.cancel();
				cursor.toStart();
				onFocusLost.accept(gui, this);
			}
			else {
				selection.cancel();
			}
		}
	}

	private void newLine(ControlGui gui) {
		if (lines.size() == maxLines) return;
		insertCharacter('\n', gui);
	}

	private String getSelection() {
		if (lines.isEmpty()) return null;

		String text = combine(lines, false);
		int startPosition = getCursorPosition(selection.start());
		int endPosition = getCursorPosition(selection.end());

		if (startPosition == endPosition) return null;

		return text.substring(startPosition, endPosition);
	}

	private void deleteSelection(ControlGui gui) {
		if (lines.isEmpty()) return;

		String text = combine(lines, false);
		int startPosition = getCursorPosition(selection.start());
		int endPosition = getCursorPosition(selection.end());

		if (startPosition == endPosition) return;

		String newText = text.substring(0, startPosition) + text.substring(endPosition);
		lines.clear();
		lines.addAll(wrapLines(newText, gui, area.width, maxLines, shadow));
	}

	private void insertCharacter(char c, ControlGui gui) {
		if (lines.isEmpty()) {
			lines.add(String.valueOf(c));
			return;
		}

		String text = combine(lines, false);
		int cursorPosition = getCursorPosition(cursor);

		String newText;
		if (cursorPosition >= text.length()) {
			newText = text + c;
		} else {
			newText = text.substring(0, cursorPosition) + c + text.substring(cursorPosition);
		}

		lines.clear();
		lines.addAll(wrapLines(newText, gui, area.width, maxLines, shadow));
	}

	private void insertString(String st, ControlGui gui)  {
		if (lines.isEmpty()) {
			lines.addAll(wrapLines(st, gui, area.width, maxLines, shadow));
			return;
		}

		String text = combine(lines, false);
		int cursorPosition = getCursorPosition(cursor);

		String newText;
		if (cursorPosition >= text.length()) {
			newText = text + st;
		} else {
			newText = text.substring(0, cursorPosition) + st + text.substring(cursorPosition);
		}

		lines.clear();
		lines.addAll(wrapLines(newText, gui, area.width, maxLines, shadow));
	}

	private void removeCharacter(ControlGui gui) {
		String text = combine(lines, false);
		int cursorPosition = getCursorPosition(cursor);

		String newText;
		if (text.length() <= 1) {
			newText = "";
		}
		else if (cursorPosition == text.length()) {
			newText = text.substring(0, text.length() - 1);
		}
		else if (cursorPosition <= 0) {
			newText = text.substring(1);
		}
		else {
			newText = text.substring(0, cursorPosition - 1) + text.substring(cursorPosition);
		}

		lines.clear();
		lines.addAll(wrapLines(newText, gui, area.width, maxLines, shadow));
	}

	private int getCursorPosition(Cursor cursor) {
		int cursorPosition = 0;
		for (int i = 0; i < cursor.row; i += 1) {
			String line = lines.get(i);
			cursorPosition += line.length();
		}
		for (int i = 0; i < cursor.column; i+= 1) cursorPosition += 1;
		return cursorPosition;
	}

	private void drawText(MatrixStack matrices, ControlGui gui) {
		int length = Math.min(lines.size(), displayedLines);

		for (int row = firstLine; row < firstLine + length; row += 1) {
			String line = lines.get(row);
			if (line.endsWith("\n")) line = line.substring(0, line.length() - 1);
			int lineY = trueY + (row - firstLine) * gui.getTextRenderer().fontHeight;
			drawString(matrices, line, gui, trueX, lineY, Anchor.CENTER, shadow, fontColor);
			drawHighlights(line, gui.getTextRenderer(), lineY, row);
		}
	}

	private void drawHighlights(String line, TextRenderer renderer, int lineY, int row) {
		if (selection.isHighlighted(row)) {
			int startIndex;
			if (selection.start().row != row) startIndex = 0;
			else startIndex = selection.start().column;

			int endIndex;
			if (selection.end().row != row) endIndex = line.length();
			else endIndex = selection.end().column;

			if (startIndex == endIndex) return;

			int x = trueX + renderer.getWidth(line.substring(0, startIndex));
			String highlightedPortion = line.substring(startIndex, endIndex);
			RenderHelper.drawRectangle(x, lineY, renderer.getWidth(highlightedPortion), renderer.fontHeight, highlightColor);
		}
	}

	private void drawCursor(MatrixStack matrices, ControlGui gui) {
		if (lines.isEmpty()) {
			drawString(matrices, "_", gui, trueX, trueY, Anchor.CENTER, shadow, fontColor);
			return;
		}

		int actualRow = cursor.row - firstLine;
		String cursorLine = lines.get(cursor.row);
		TextRenderer renderer = gui.getTextRenderer();
		int cursorX = (int) (trueX + renderer.getWidth(cursorLine.substring(0, cursor.column)));
		int cursorY = (int) (trueY + actualRow * renderer.fontHeight);

		String cursorString = "_";
		if (cursor.row < lines.size() - 1 || cursor.column < cursorLine.length() || lineOccupiesFullSpace(cursorLine, renderer)) cursorString = "|";

		drawString(matrices, cursorString, gui, cursorX, cursorY, Anchor.CENTER, shadow, fontColor);
	}

	private boolean lineOccupiesFullSpace(String cursorLine, TextRenderer renderer) {
		int width = area.width - 3;
		return renderer.getWidth(cursorLine) >= width;
	}

	private class Cursor {
		// Inclusive index of current line in lines
		int row = 0;
		// Exclusive index of current line-string
		int column = 0;
		final boolean main;

		Cursor(boolean main) {
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
			if (row >= lines.size()) {
				row = lines.size() - 1;
				column = lines.get(row).length();
			}
			if (column < 0) {
				if (row == 0) column = 0;
				else {
					row -= 1;
					column = lines.get(row).length();
				}
			}

			String cursorRow = lines.get(row);
			if (cursorRow.endsWith("\n")) cursorRow = cursorRow.substring(0, cursorRow.length() - 1);

			if (column > cursorRow.length()) {
				if (row < lines.size() - 1 && amount > 0) {
					column = 0;
					row += 1;
				}
				else {
					column = cursorRow.length();
				}
			}

			adjustFirstLine();
		}

		private void moveVertical(int amount) {
			row += amount;
			if (row < 0) row = 0;
			else if (row > lines.size() - 1) row = lines.size() - 1;

			String cursorRow = lines.get(row);
			if (cursorRow.endsWith("\n")) cursorRow = cursorRow.substring(0, cursorRow.length() - 1);
			if (column > cursorRow.length()) column = cursorRow.length();

			adjustFirstLine();
		}

		private void toStart() {
			row = 0;
			column = 0;
		}

		private void toSelectionStart() {
			if (!main) return;
			row = selection.start().row;
			column = selection.start().column;
			selection.cancel();
			adjustFirstLine();
		}

		private void toEnd() {
			row = lines.size() - 1;
			column = lines.get(row).length();
			adjustFirstLine();
		}

		// TODO: Figure out how to replace these loops with proper math.
		private void adjustFirstLine() {
			if (!main) return;

			while (row < firstLine) firstLine -= 1;
			if (lines.size() >= displayedLines) while (lines.size() - firstLine < displayedLines) firstLine -= 1;
			while (row >= firstLine + displayedLines) firstLine += 1;
		}
	}

	private class Selection {
		private Cursor anchor = new Cursor(false);
		private Cursor follower = new Cursor(false);
		private boolean active = false;

		private void all() {
			anchor.toStart();
			follower.toEnd();
			cursor.toEnd();
			active = true;
		}

		private void cancel() {
			anchor.toStart();
			follower.toStart();
			active = false;
		}

		private void startHighlighting() {
			anchor.row = cursor.row;
			anchor.column = cursor.column;

			follower.row = cursor.row;
			follower.column = cursor.column;

			active = true;
		}

		private boolean isHighlighted(int row) {
			return active && ((inverted() && (anchor.row >= row && row >= follower.row)) || (anchor.row <= row && row <= follower.row));
		}

		private void moveToCursor(long handle) {
			if (!active) return;
			if (shift(handle)) {
				follower.row = cursor.row;
				follower.column = cursor.column;
			}
			else cancel();
		}

		private Cursor start() {
			if (inverted()) return follower;
			return anchor;
		}

		private Cursor end() {
			if (inverted()) return anchor;
			return follower;
		}

		private boolean inverted() {
			return follower.row < anchor.row || (follower.row == anchor.row && follower.column < anchor.column);
		}

		private int startColumn() {
			return anchor.column;
		}

		private int endColumn() {
			return follower.column;
		}
	}
}
