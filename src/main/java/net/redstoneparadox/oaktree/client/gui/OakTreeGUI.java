package net.redstoneparadox.oaktree.client.gui;

import net.minecraft.container.Container;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

import java.util.Optional;

public interface OakTreeGUI {

    Optional<Container> getScreenContainer();

    boolean mouseButtonHeld(String mouseButton);

    boolean mouseButtonJustClicked(String mouseButton);

    Optional<Character> getLastChar();
}
