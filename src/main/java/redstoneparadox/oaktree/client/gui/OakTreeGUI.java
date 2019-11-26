package redstoneparadox.oaktree.client.gui;

import net.minecraft.container.Container;

import java.util.Optional;

public interface OakTreeGUI {

    Optional<Container> getScreenContainer();

    boolean mouseButtonHeld(String mouseButton);

    boolean mouseButtonJustClicked(String mouseButton);

    Optional<Character> getLastChar();
}
