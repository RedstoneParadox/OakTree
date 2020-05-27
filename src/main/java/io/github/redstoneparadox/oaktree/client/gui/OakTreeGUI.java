package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.screen.ScreenHandler;

import java.util.Optional;

public interface OakTreeGUI {

    @Deprecated
    Optional<ScreenHandler> getScreenContainer();

    boolean mouseButtonHeld(String mouseButton);

    boolean mouseButtonJustClicked(String mouseButton);

    Optional<Character> getLastChar();

    @Deprecated
    TextRenderer getTextRenderer();

    default void shouldCloseOnInventoryKey(boolean value) {

    }

    @Deprecated
    int getX();

    @Deprecated
    int getY();

    Theme getTheme();

    void applyTheme(Theme theme);
}
