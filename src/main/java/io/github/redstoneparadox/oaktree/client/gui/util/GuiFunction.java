package io.github.redstoneparadox.oaktree.client.gui.util;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;

@FunctionalInterface
public interface GuiFunction<C extends Control> {

    void invoke(ControlGui gui, C control);
}
