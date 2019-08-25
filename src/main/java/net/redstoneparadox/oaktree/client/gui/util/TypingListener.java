package net.redstoneparadox.oaktree.client.gui.util;

@FunctionalInterface
public interface TypingListener<T> {

    Character invoke(Character toType, T node);
}
