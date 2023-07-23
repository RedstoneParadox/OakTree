package io.github.redstoneparadox.oaktree.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that takes 0 arguments and returns nothing.
 *
 * <p>This is a functional interface whose functional method is
 * {@link #run()}.
 */
@FunctionalInterface
public interface Action {
	/**
	 * Runs this function
	 */
	void run();

	/**
	 * Returns a composed function that first applies this function to
	 * its input, and then applies the {@code after} function to the result.
	 * If evaluation of either function throws an exception, it is relayed to
	 * the caller of the composed function.
	 *
	 * @param after the function to apply after this function is applied
	 * @return a composed function that first applies this function and then
	 * applies the {@code after} function
	 * @throws NullPointerException if after is null
	 *
	 * @see #compose(Action)
	 */
	@Contract(pure = true)
	default Action andThen(@NotNull Action after){
		Objects.requireNonNull(after);
		return () -> {
			this.run();
			after.run();
		};
	}

	/**
	 * Returns a composed function that first applies the {@code before}
	 * function to its input, and then applies this function to the result.
	 * If evaluation of either function throws an exception, it is relayed to
	 * the caller of the composed function.
	 *
	 * @param before the function to apply before this function is applied
	 * @return a composed function that first applies the {@code before}
	 * function and then applies this function
	 * @throws NullPointerException if before is null
	 *
	 * @see #andThen(Action)
	 */
	@Contract(pure = true)
	default Action compose(@NotNull Action before){
		Objects.requireNonNull(before);
		return () -> {
			before.run();
			this.run();
		};
	}
}
