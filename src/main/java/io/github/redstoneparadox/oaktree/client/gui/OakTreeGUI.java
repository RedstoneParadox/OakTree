package io.github.redstoneparadox.oaktree.client.gui;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.container.Container;

import java.util.Optional;

public interface OakTreeGUI {

    Optional<Container> getScreenContainer();

    boolean mouseButtonHeld(String mouseButton);

    boolean mouseButtonJustClicked(String mouseButton);

    Optional<Character> getLastChar();

    boolean isKeyPressed(String key);

    TextRenderer getTextRenderer();

    boolean isBackspaceHeld();

    default void shouldCloseOnInventoryKey(boolean value) {

    }

    int getX();

    int getY();
}
