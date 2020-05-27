package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.Key;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.screen.ScreenHandler;

import java.util.Optional;

public interface OakTreeGUI {

    Optional<ScreenHandler> getScreenContainer();

    boolean mouseButtonHeld(String mouseButton);

    boolean mouseButtonJustClicked(String mouseButton);

    Optional<Character> getLastChar();

    TextRenderer getTextRenderer();

    default void shouldCloseOnInventoryKey(boolean value) {

    }

    int getX();

    int getY();

    Theme getTheme();

    void applyTheme(Theme theme);
}
