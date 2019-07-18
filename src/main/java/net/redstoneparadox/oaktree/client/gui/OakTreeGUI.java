package net.redstoneparadox.oaktree.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

import java.awt.*;
import java.util.Optional;

public interface OakTreeGUI {

    void drawString(String string, float xPos, float yPos, boolean withShadow);

    void drawTexture(int posX, int posY, int left, int top, int width, int height);

    float getWidth();

    float getHeight();

    Optional<Container> getContainer();
}
