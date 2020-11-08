package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.util.RenderHelper;
import io.github.redstoneparadox.oaktree.util.TextHelper;
import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.ControlGui;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A type of {@link Control} that allows you to display
 * text in your GUIs. Fully supports {@link Text} so
 * you can apply formatting.
 */
public class LabelControl extends Control<LabelControl> {
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
			this.area.width = 0;

			for (Text text: texts) {
				this.area.width = Math.max(this.area.width, TextHelper.getWidth(text));
			}

			this.area.width += 8;
			area.height = TextHelper.getFontHeight() * texts.size() + 8;
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

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl text(Text text) {
		this.text = text;
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl text(String text) {
		return this.text(new LiteralText(text));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl text(List<Text> texts) {
		if (fitText) {
			this.area.width = 0;

			for (Text text: texts) {
				this.area.width = Math.max(this.area.width, TextHelper.getWidth(text));
			}

			this.area.width += 8;
			area.height = TextHelper.getFontHeight() * texts.size() + 8;
			this.maxDisplayedLines = texts.size();
		}

		this.text = TextHelper.combine(texts, true);
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl clear() {
		this.text = LiteralText.EMPTY;
		return this;
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

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl shadow(boolean shadow) {
		this.shadow = shadow;
		return this;
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

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl fontColor(@NotNull Color fontColor) {
		this.fontColor = fontColor;
		return this;
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

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl maxDisplayedLines(int maxDisplayedLines) {
		this.maxDisplayedLines = Math.max(0, maxDisplayedLines);
		return this;
	}

	public void setFirstLine(int firstLine) {
		this.firstLine = Math.max(0, firstLine);
	}

	public void moveToStart() {
		this.firstLine = 0;
	}

	public void moveToEnd() {
		this.firstLine = TextHelper.wrapText(text, area.width, 0, Integer.MAX_VALUE, shadow, true).size() - maxDisplayedLines;
	}

	public int getFirstLine() {
		return firstLine;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl firstLine(int firstLine) {
		this.firstLine = Math.max(0, firstLine);
		return this;
	}

	/**
	 * Sets whether or not this {@link LabelControl}
	 * should resize to fit its text. The only way
	 * to get newlines in this mode is to insert
	 * them yourself or pass a list to.
	 * {@link LabelControl#text(List)}
	 *
	 * @param fitText The value itself
	 */
	public void setFitText(boolean fitText) {
		this.fitText = fitText;
	}

	public boolean shouldFitText() {
		return fitText;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl fitText(boolean fitText) {
		this.fitText = fitText;
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public boolean isFitText() {
		return fitText;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl toStart() {
		this.firstLine = 0;
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public LabelControl toEnd() {
		this.firstLine = TextHelper.wrapText(text, area.width, 0, Integer.MAX_VALUE, shadow, true).size() - maxDisplayedLines;
		return this;
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);

		if (maxDisplayedLines > 0) {
			List<OrderedText> lines = TextHelper.wrapText(text, area.width, firstLine, maxDisplayedLines, shadow, false);
			int yOffset = 0;

			for (OrderedText line: lines) {
				RenderHelper.drawText(matrices, line, trueX, trueY + yOffset, shadow, fontColor);
				yOffset += TextHelper.getFontHeight();
			}
		}
	}
}
