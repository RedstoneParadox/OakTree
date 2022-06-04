package io.github.redstoneparadox.oaktree.util;

import it.unimi.dsi.fastutil.chars.CharConsumer;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;

public class OptionalChar {
	private final boolean isPresent;
	private final char value;

	private OptionalChar() {
		this.value ='\0';
		this.isPresent = false;
	}

	public static OptionalChar empty() {
		return new OptionalChar();
	}

	private OptionalChar(char value) {
		this.value = value;
		this.isPresent = true;
	}

	public static OptionalChar of(char value) {
		return new OptionalChar(value);
	}

	public char getAsChar() {
		if (!isPresent) {
			throw new NoSuchElementException("No value present");
		}

		return value;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void ifPresent(CharConsumer consumer) {
		if (isPresent) consumer.accept(value);
	}

	public char orElse(char other) {
		return isPresent ? value : other;
	}

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
