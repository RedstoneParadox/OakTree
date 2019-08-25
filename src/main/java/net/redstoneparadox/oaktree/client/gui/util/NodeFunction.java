package net.redstoneparadox.oaktree.client.gui.util;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.nodes.Node;

@FunctionalInterface
public interface NodeFunction<T extends Node> {

    void invoke(OakTreeGUI gui, T node);
}
