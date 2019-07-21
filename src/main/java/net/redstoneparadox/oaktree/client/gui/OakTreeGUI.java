package net.redstoneparadox.oaktree.client.gui;

import net.minecraft.container.Container;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

import java.util.Optional;

public interface OakTreeGUI {

    void drawString(String string, float xPos, float yPos, boolean withShadow, RGBAColor fontColor);

    void drawTexture(int posX, int posY, int left, int top, int width, int height);

    float getWidth();

    float getHeight();

    Optional<Container> getContainer();

    boolean mouseButtonHeld(String mouseButton);

    boolean mouseButtonJustClicked(String mouseButton);

    Optional<Character> getLastChar();
}
