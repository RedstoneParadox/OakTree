package redstoneparadox.oaktree.util;

/**
 * Created by RedstoneParadox on 11/27/2019.
 */
@FunctionalInterface
public interface TriFunction<T, U, V, R> {

    R apply(T t, U u, V v);
}
