package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.InteractionListener;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;
import net.redstoneparadox.oaktree.client.gui.util.TypingListener;

public class TextEditNode extends InteractiveNode<TextEditNode> {

    private String defaultText = "";
    private String text = "";

    private TypingListener<TextEditNode> onCharTyped = (toType, node) -> toType;
    private InteractionListener<TextEditNode> onFocused = (client, mouse, gui, node) -> {};

    public TextEditNode onCharTyped(TypingListener<TextEditNode> listener) {
        onCharTyped = listener;
        return this;
    }

    public TextEditNode onFocused(InteractionListener<TextEditNode> listener) {
        onFocused = listener;
        return this;
    }

    @Override
    public void updateListeners(Mouse mouse, MinecraftClient client, Window window, OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY) {
        gui.getLastChar().ifPresent((character -> {
            Character toType = onCharTyped.invoke(character, this);
            if (toType != null) {
                text = text + toType;
            }
        }));
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.draw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        ScreenVec anchorOffset = anchor.getOffset(containerWidth, containerHeight);
        ScreenVec alignmentOffset = alignment.getOffset(width, height);

        float trueX = x + anchorOffset.x + offsetX - alignmentOffset.x;
        float trueY = y + anchorOffset.y + offsetY - alignmentOffset.y;

        gui.drawString(text, trueX, trueY, false, RGBAColor.red());
    }
}
