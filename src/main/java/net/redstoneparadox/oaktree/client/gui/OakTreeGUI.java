package net.redstoneparadox.oaktree.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public interface OakTreeGUI {

    void drawString(String string, float xPos, float yPos, boolean withShadow);
}
