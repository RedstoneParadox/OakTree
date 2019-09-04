package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import net.redstoneparadox.oaktree.client.gui.util.TypingListener;

public class TextEditNode extends InteractiveNode<TextEditNode> implements TextNode {

    private String defaultText = "";
    private String text = "";

    private TypingListener<TextEditNode> onCharTyped = (toType, node) -> toType;
    private NodeFunction<TextEditNode> onFocused = (gui, node) -> {};

    public TextEditNode onCharTyped(TypingListener<TextEditNode> listener) {
        onCharTyped = listener;
        return this;
    }

    public TextEditNode onFocused(NodeFunction<TextEditNode> listener) {
        onFocused = listener;
        return this;
    }

    @Override
    public void updateListeners(OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY, float containerX, float containerY, float containerWidth, float containerHeight) {
        gui.getLastChar().ifPresent((character -> {
            Character toType = onCharTyped.invoke(character, this);
            if (toType != null) {
                text = text + toType;
            }
        }));
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        super.draw(mouseX, mouseY, deltaTime, gui);
        drawString(text, gui, trueX, trueY, null, false, RGBAColor.red());
    }
}
