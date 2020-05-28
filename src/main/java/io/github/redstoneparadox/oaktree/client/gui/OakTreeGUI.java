package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.screen.ScreenHandler;

import java.util.Optional;

/**
 * @deprecated Use a {@link ControlGui} instead.
 */
@Deprecated
public interface OakTreeGUI {

    @Deprecated
    default Optional<ScreenHandler> getScreenContainer() {
        return Optional.empty();
    }

    @Deprecated
    default boolean mouseButtonHeld(String mouseButton){
        return false;
    }

    @Deprecated
    default boolean mouseButtonJustClicked(String mouseButton) {
        return false;
    }

    @Deprecated
    default Optional<Character> getLastChar() {
        return Optional.empty();
    }

    @Deprecated
    default TextRenderer getTextRenderer() {
        return null;
    }

    default void shouldCloseOnInventoryKey(boolean value) {

    }

    @Deprecated
    default int getX() {
        return 0;
    }

    @Deprecated
    default int getY() {
        return 0;
    }

    @Deprecated
    default Theme getTheme() {
        return null;
    }

    @Deprecated
    default void applyTheme(Theme theme) {

    }
}
