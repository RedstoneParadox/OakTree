package net.redstoneparadox.oaktree.client.gui.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.nodes.Node;

@FunctionalInterface
public interface NodeFunction<T extends Node> {

    void invoke(MinecraftClient client, Mouse mouse, OakTreeGUI gui, T node);
}
