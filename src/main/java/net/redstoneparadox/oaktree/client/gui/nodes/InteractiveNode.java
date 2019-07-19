package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public abstract class InteractiveNode extends Node {


    private int count = 0;

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, window, offsetX, offsetY, containerWidth, containerHeight);
        MinecraftClient client = MinecraftClient.getInstance();
        Mouse mouse = client.mouse;

        ScreenVec anchorOffset = anchorAlignment.getOffset(containerWidth, containerHeight);
        ScreenVec drawOffset = drawAlignment.getOffset(width, height);

        float trueX = x + anchorOffset.x + offsetX - drawOffset.x;
        float trueY = y + anchorOffset.y + offsetY - drawOffset.y;

        boolean mouseWithin = (mouseX >= x && mouseX <= (width + x)) && (mouseY >= y && mouseY <= (height + y));
        updateListeners(mouse, client, window, gui, mouseWithin);
        count++;
    }

    public abstract void updateListeners(Mouse mouse, MinecraftClient client, Window window, OakTreeGUI gui, boolean mouseWithin);

    public int keyFromName(String key) {
        String fullName = "key.keyboard." + key;

        if (key.equals("numlock")) {
            fullName = "key.keyboard.num.lock";
        }
        return InputUtil.fromName(fullName).getKeyCode();
    }

    public int mouseButtonFromName(String key) {
        String fullName = "key.mouse" + key;
        return InputUtil.fromName(fullName).getKeyCode();
    }
}
