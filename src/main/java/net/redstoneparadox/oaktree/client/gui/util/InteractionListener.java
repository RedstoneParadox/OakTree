package net.redstoneparadox.oaktree.client.gui.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.nodes.InteractiveNode;

@FunctionalInterface
public interface InteractionListener<T extends InteractiveNode> {

    void invoke(MinecraftClient client, Mouse mouse, OakTreeGUI gui, T node);
}
