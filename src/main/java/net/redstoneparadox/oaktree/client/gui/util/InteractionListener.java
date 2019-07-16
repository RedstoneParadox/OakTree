package net.redstoneparadox.oaktree.client.gui.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

@FunctionalInterface
public interface InteractionListener {

    void invoke(MinecraftClient client, Mouse mouse, OakTreeGUI gui);
}
