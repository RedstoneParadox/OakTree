package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.Key;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.container.Container;

import java.util.Optional;

public interface OakTreeGUI {

    Optional<Container> getScreenContainer();

    boolean mouseButtonHeld(String mouseButton);

    boolean mouseButtonJustClicked(String mouseButton);

    Optional<Character> getLastChar();

    @Deprecated
    default boolean isKeyPressed(String key) {
        return false;
    }

    @Deprecated
    default Key getKey() {
        return Key.NONE;
    };

    TextRenderer getTextRenderer();

    @Deprecated
    default boolean isBackspaceHeld() {
        return false;
    }

    default void shouldCloseOnInventoryKey(boolean value) {

    }

    int getX();

    int getY();

    Theme getTheme();

    void applyTheme(Theme theme);

    default boolean isLeftKey(int keycode) {
        return keycode == 263;
    }

    default boolean isRightKey(int keycode) {
        return keycode == 262;
    }

    default boolean isUpKey(int keycode) {
        return keycode == 265;
    }

    default boolean isDownKey(int keycode) {
        return keycode == 264;
    }
}
