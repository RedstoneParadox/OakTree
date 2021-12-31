package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import io.github.redstoneparadox.oaktree.util.TextHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A type of {@link Control} that allows you to display
 * text in your GUIs. Fully supports {@link Text} so
 * you can apply formatting.
 */
public class LabelControl extends Control {
	protected  @NotNull Text text = LiteralText.EMPTY;
	protected boolean shadow = false;
	public @NotNull Color fontColor = Color.WHITE;
	protected int maxDisplayedLines = 1;
	protected int firstLine = 0;
	protected boolean fitText = false;

	public LabelControl() {
		this.id = "label";
	}

	/**
	 * Sets the text for this LabelControl
	 * to display.
	 *
	 * @param text The {@link Text} to display
	 */
	public void setText(@NotNull Text text) {
		this.text = text;
	}

	/**
	 * Sets the text for this LabelControl
	 * to display. Useful if you want to
	 * display a string without creating
	 * a {@link LiteralText} instance.
	 *
	 * @param text The text to display
	 */
	public void setText(String text) {
		this.text = new LiteralText(text);
	}

	/**
	 * <p>Sets the text for this {@link LabelControl}
	 * to display. Useful for when you have a
	 * {@link List<Text>} and don't want to add
	 * the newlines yourself.</p>
	 *
	 * @param texts A {@link List<Text>}
	 */
	public void setText(List<Text> texts) {
		if (fitText) {
			this.area.setWidth(0);

			for (Text text: texts) {
				this.area.setWidth(Math.max(this.area.getWidth(), TextHelper.getWidth(text)));
			}

			this.area.setWidth(this.area.getWidth() + 8);
			area.setHeight(TextHelper.getFontHeight() * texts.size() + 8);
			this.maxDisplayedLines = texts.size();
		}

		this.text = TextHelper.combine(texts, true);
	}

	/**
	 * Clears the LabelControl.
	 */
	public void clearText() {
		this.text = LiteralText.EMPTY;
	}

	public @NotNull Text getText() {
		return text;
	}

	/**
	 * Sets whether the text should be drawn with a shadow.
	 *
	 * @param shadow The value.
	 */
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	public boolean isShadow() {
		return shadow;
	}

	/**
	 * Sets the color of the font to be drawn. Note that transparency
	 * is ignored here due to Minecraft internals.
	 *
	 * @param fontColor The RGBA Color
	 */
	public void setFontColor(@NotNull Color fontColor) {
		this.fontColor = fontColor;
	}

	public @NotNull Color getFontColor() {
		return fontColor;
	}

	/**
	 * Sets the maximum number of lines.
	 *
	 * @param maxDisplayedLines The max number of lines.
	 */
	public void setMaxDisplayedLines(int maxDisplayedLines) {
		this.maxDisplayedLines = Math.max(0, maxDisplayedLines);
	}

	public int getMaxDisplayedLines() {
		return maxDisplayedLines;
	}

	public void setFirstLine(int firstLine) {
		this.firstLine = Math.max(0, firstLine);
	}

	public void moveToStart() {
		this.firstLine = 0;
	}

	public void moveToEnd() {
		this.firstLine = TextHelper.wrapText(text, area.getWidth(), 0, Integer.MAX_VALUE, shadow, true).size() - maxDisplayedLines;
	}

	public int getFirstLine() {
		return firstLine;
	}

	/**
	 * Sets whether or not this {@link LabelControl}
	 * should resize to fit its text. The only way
	 * to get newlines in this mode is to insert
	 * them yourself or pass a list to.
	 * {@link LabelControl#setText(Text)}
	 *
	 * @param fitText The value itself
	 */
	public void setFitText(boolean fitText) {
		this.fitText = fitText;
	}

	public boolean shouldFitText() {
		return fitText;
	}

	@Override
	public void oldDraw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.oldDraw(matrices, mouseX, mouseY, deltaTime, gui);

		if (maxDisplayedLines > 0) {
			List<OrderedText> lines = TextHelper.wrapText(text, area.getWidth(), firstLine, maxDisplayedLines, shadow, false);
			int yOffset = 0;

			for (OrderedText line: lines) {
				RenderHelper.drawText(matrices, line, trueX, trueY + yOffset, shadow, fontColor);
				yOffset += TextHelper.getFontHeight();
			}
		}
	}
}
