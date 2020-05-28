package io.github.redstoneparadox.oaktree.hooks;

import java.util.function.Consumer;
import java.util.function.Function;

public interface KeyboardHooks {
    void onCharTyped(Consumer<Character> onCharTyped);
}
