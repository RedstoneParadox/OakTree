package net.redstoneparadox.oaktree.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.LiteralText;
import net.redstoneparadox.oaktree.client.gui.nodes.Node;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

import java.awt.*;
import java.util.Optional;

public class OakTreeScreen extends Screen implements OakTreeGUI {

    Node root;

    boolean shouldPause;

    public OakTreeScreen(Node treeRoot, boolean pause) {
        super(new LiteralText("gui"));
        root = treeRoot;
    }

    @Override
    public boolean isPauseScreen() {
        return shouldPause;
    }

    @Override
    public void init(MinecraftClient minecraftClient_1, int int_1, int int_2) {
        super.init(minecraftClient_1, int_1, int_2);
        root.setup(minecraftClient_1, int_1, int_2);
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        root.preDraw(this, minecraft.window, 0, 0, width, height);
        root.draw(int_1, int_2, float_1, this,0, 0, width, height);

    }

    @Override
    public void drawString(String string, float xPos, float yPos, boolean withShadow, RGBAColor fontColor) {

        int redInt = ((int) fontColor.redChannel * 255) << 16;
        int greenInt = ((int) fontColor.greenChannel * 255) << 8;
        int blueInt = (int) fontColor.blueChannel * 255;

        int colorInt = redInt + greenInt + blueInt;

        if (withShadow) {
            font.drawWithShadow(string, xPos, yPos, colorInt);
        }
        else {
            font.draw(string, xPos, yPos, colorInt);
        }
    }

    @Override
    public void drawTexture(int posX, int posY, int left, int top, int width, int height) {
        blit(posX, posY, left, top, width, height);
    }

    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public Optional<Container> getContainer() {
        return Optional.empty();
    }
}
