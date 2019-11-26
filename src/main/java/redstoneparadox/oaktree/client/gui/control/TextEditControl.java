package redstoneparadox.oaktree.client.gui.control;

import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.util.GuiFunction;
import redstoneparadox.oaktree.client.gui.util.RGBAColor;
import redstoneparadox.oaktree.client.gui.util.TypingListener;

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
            Character toType = onCharTyped.invoke(character, this);
            if (toType != null) {
                text = text + toType;
            }
        }));
        drawString(text, gui, trueX, trueY, null, false, RGBAColor.red());
    }
}
