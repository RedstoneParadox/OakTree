package net.redstoneparadox.oaktree.client.gui.util;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.nodes.Control;

@FunctionalInterface
public interface NodeFunction<T extends Control> {

    void invoke(OakTreeGUI gui, T node);
}
