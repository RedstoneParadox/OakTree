package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.InputUtil;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public abstract class InteractiveNode<T extends InteractiveNode> extends Node<T> {

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        MinecraftClient client = MinecraftClient.getInstance();
        Mouse mouse = client.mouse;

        boolean mouseWithin = (mouseX >= trueX && mouseX <= (trueWidth + trueX)) && (mouseY >= trueY && mouseY <= (trueHeight + trueY));
        updateListeners(mouse, client, gui, mouseWithin, mouseX, mouseY);
    }

    public abstract void updateListeners(Mouse mouse, MinecraftClient client, OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY);

    public int keyFromName(String key) {
        String fullName = "key.keyboard." + key;

        if (key.equals("numlock")) {
            fullName = "key.keyboard.num.lock";
        }
        return InputUtil.fromName(fullName).getKeyCode();
    }

    public int mouseButtonFromName(String key) {
        String fullName = "key.mouse." + key;
        return InputUtil.fromName(fullName).getKeyCode();
    }
}
