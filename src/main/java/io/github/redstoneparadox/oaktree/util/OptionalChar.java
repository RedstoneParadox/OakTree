package io.github.redstoneparadox.oaktree.util;

import it.unimi.dsi.fastutil.chars.CharConsumer;

import java.util.NoSuchElementException;

public class OptionalChar {
	private final char c;
	private final boolean empty;

	private OptionalChar(char c) {
		this.c = c;
		this.empty = false;
	}

	private OptionalChar() {
		this.c ='\0';
		this.empty = true;
	}

	public static OptionalChar of(char c) {
		return new OptionalChar(c);
	}

	public static OptionalChar empty() {
		return new OptionalChar();
	}

	public boolean isPresent() {
		return !empty;
	}

	public char get() {
		if (empty) {
			throw new NoSuchElementException();
		}

		return c;
	}

	public void ifPresent(CharConsumer consumer) {
		if (!empty) {
			consumer.accept(c);
		}
	}
}
