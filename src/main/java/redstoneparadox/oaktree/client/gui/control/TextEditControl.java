package redstoneparadox.oaktree.client.gui.control;

import net.minecraft.client.font.TextRenderer;
import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.util.GuiFunction;
import redstoneparadox.oaktree.client.gui.util.RGBAColor;
import redstoneparadox.oaktree.client.gui.util.TypingListener;
import redstoneparadox.oaktree.mixin.client.gui.screen.ScreenAccessor;

/**
 * @apiNote  Work in Progress!
 */
public class TextEditControl extends InteractiveControl<TextEditControl> implements TextNode {

    private String defaultText = "";
    private String text = "";

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
        gui.getLastChar().ifPresent((character -> {
            Character typed = onCharTyped.invoke(character, this);
            if (typed != null) {
                String str = text + typed;
                TextRenderer font = ((ScreenAccessor)gui).getFont();
                int stringWidth = font.getStringWidth(str);

                if (stringWidth <= trueWidth) {
                    text = str;
                }
            }
        }));

        if (gui.isKeyPressed("backspace") && text.length() > 0) {
            text = text.substring(0, text.length() - 1);
        }

        drawString(text, gui, trueX, trueY, null, false, RGBAColor.red());
    }
}
