package io.github.redstoneparadox.oaktree.util;

import it.unimi.dsi.fastutil.chars.CharConsumer;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A container object which may or may not contain a character value.
 * If a value is present, {@code isPresent()} returns {@code true}. If no
 * value is present, {@code isPresent()} returns false.
 */
public class OptionalChar {
	private final boolean isPresent;
	private final char value;

	private OptionalChar() {
		this.value ='\0';
		this.isPresent = false;
	}

	/**
	 * Returns an empty {@code OptionalChar} instance. No value is
	 * present for this {@code OptionalChar}
	 *
	 * @return an empty {@code OptionalChar}
	 */
	public static OptionalChar empty() {
		return new OptionalChar();
	}

	private OptionalChar(char value) {
		this.value = value;
		this.isPresent = true;
	}

	/**
	 * Constructs an instance with the supplied char
	 *
	 * @param value The char
	 * @return The instance
	 */
	public static OptionalChar of(char value) {
		return new OptionalChar(value);
	}

	public char getAsChar() {
		if (!isPresent) {
			throw new NoSuchElementException("No value present");
		}

		return value;
	}

	/**
	 * If a value is present, returns true, otherwise false.
	 *
	 * @return True if a value is present, otherwise false.
	 */
	public boolean isPresent() {
		return isPresent;
	}

	/**
	 * If a value is present, runs the passed {@link CharConsumer},
	 * otherwise does nothing.
	 *
	 * @param consumer The consumer.
	 */
	public void ifPresent(CharConsumer consumer) {
		if (isPresent) consumer.accept(value);
	}

	/**
	 * If the char is present, returns it, or else returns
	 * {@code other}.
	 *
	 * @param other The char to return if this is empty
	 * @return The char if present, otherwise other
	 */
	public char orElse(char other) {
		return isPresent ? value : other;
	}

	/**
	 * If the char is present, returns it, otherwise throws an exception produced
	 * by the exception supplying function.
	 *
	 * @param exceptionSupplier the supplying function that produces an exception to be thrown
	 * @return The value, if present
	 * @throws X if no value present
	 */
	public <X extends Throwable> char orElseThrow(Supplier<X> exceptionSupplier) throws X {
		if (isPresent) {
			return value;
		} else {
			throw exceptionSupplier.get();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;

		if (!(obj instanceof OptionalChar)) {
			return false;
		}

		OptionalChar other = (OptionalChar) obj;
		return (isPresent && other.isPresent)
				? value == other.value
				: isPresent == other.isPresent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isPresent, value);
	}
}
