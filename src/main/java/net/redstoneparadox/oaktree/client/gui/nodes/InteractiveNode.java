package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public abstract class InteractiveNode extends Node {

    @Override
    public void preDraw(OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight);
        MinecraftClient client = MinecraftClient.getInstance();
        Window window = client.window;
        Mouse mouse = client.mouse;

        ScreenVec anchorOffset = anchorAlignment.getOffset(containerWidth, containerHeight);
        ScreenVec drawOffset = drawAlignment.getOffset(width, height);

        float trueX = x + anchorOffset.x + offsetX - drawOffset.x;
        float trueY = y + anchorOffset.y + offsetY - drawOffset.y;

        if (mouseWithin((float) mouse.getX(), window.getWidth(), gui.getWidth(), trueX, width) && mouseWithin((float) mouse.getY(), window.getHeight(), gui.getHeight(), trueY, height)) {
            updateListeners(mouse, client, window);
            InputUtil.fromName("").getKeyCode();
        }
    }

    private boolean mouseWithin(float mouseCoord, float windowSize, float screenSize, float nodeCoord, float nodeLength) {
        float trueMouseCoord = (mouseCoord * screenSize)/windowSize;
        return trueMouseCoord > nodeCoord && trueMouseCoord < nodeLength;
    }

    public abstract void updateListeners(Mouse mouse, MinecraftClient client, Window window);

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
