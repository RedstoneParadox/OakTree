package redstoneparadox.oaktree.client.gui.util;

@FunctionalInterface
public interface TypingListener<T> {

    Character invoke(Character character, T node);
}
