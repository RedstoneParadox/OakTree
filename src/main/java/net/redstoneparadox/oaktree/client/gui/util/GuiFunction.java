package net.redstoneparadox.oaktree.client.gui.util;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.control.Control;

@FunctionalInterface
public interface GuiFunction<C extends Control> {

    void invoke(OakTreeGUI gui, C control);
}
