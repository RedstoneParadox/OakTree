package redstoneparadox.oaktree.client.gui.control;

import com.google.common.collect.Lists;
import org.spongepowered.asm.mixin.Overwrite;
import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import redstoneparadox.oaktree.client.gui.util.RGBAColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LabelControl extends Control<LabelControl> implements TextControl<LabelControl> {
    private boolean shadow = false;
    private RGBAColor fontColor = RGBAColor.white();
    private int maxLines = 1;

    private final List<String> lines = Lists.newArrayList("");

    public LabelControl text(String text) {
        lines.clear();
        List<String> split = Arrays.asList(text.split("(\r\n|\r|\n)", -1));
        lines.addAll(split);
        return this;
    }

    public LabelControl clear() {
        lines.clear();
        return this;
    }

    /**
     * Sets whether the text should be drawn with a shadow.
     *
     * @param shadow The value.
     * @return The control itself.
     */
    public LabelControl shadow(boolean shadow) {
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
    public LabelControl fontColor(RGBAColor fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Sets the maximum number of lines.
     *
     * @param maxLines The max number of lines.
     * @return The control itself.
     */
    public LabelControl maxLines(int maxLines) {
        if (maxLines > 0) this.maxLines = maxLines;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);
        int offset = 0;
        for (String line: lines) {
            drawString(line, gui, trueX, trueY + offset*10, ControlAnchor.CENTER, shadow, fontColor);
            offset += 1;
        }
    }
}
