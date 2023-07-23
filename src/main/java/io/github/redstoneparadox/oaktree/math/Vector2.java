package io.github.redstoneparadox.oaktree.math;

import java.util.Objects;

/**
 * Represents a direction or position in 2D space.
 */
public class Vector2 {
	public static final Vector2 ZERO = new Vector2();

	private final int x;
	private final int y;

	/**
	 * Creates a new {@code Vector} with the specified
	 * x and y values.
	 *
	 * @param x The x value
	 * @param y The y value
	 */
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	private Vector2() {
		this(0, 0);
	}

	/**
	 * @return The x value of this {@code Vector2}
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return The y value of this {@code Vector2}
	 */
	public int getY() {
		return y;
	}

	/**
	 * Adds this {@code Vector2} to another {@code Vector2}
	 * and returns a new instance representing the result.
	 *
	 * @param right The {@code Vector2} to add
	 * @return The resulting {@code Vector2}
	 */
	public Vector2 add(Vector2 right) {
		return new Vector2(this.x + right.x, this.y + right.y);
	}

	/**
	 * Adds this {@code Vector2} to the pass x and y value
	 * and returns a new instance representing the result
	 *
	 * @param x The x-value to add
	 * @param y The y-value to add
	 * @return The resulting {@code Vector2}
	 */
	public Vector2 add(int x, int y) {
		return new Vector2(this.x + x, this.y + y);
	}

	/**
	 * Tests whether this {@code Vector2} is equal to
	 * another object. If the other object is a
	 * {@code Vector2}, equality is based on their x
	 * and y values. If the other object is not a
	 * {@code Vector2} They are not equal
	 *
	 * @param o The object to compare against.
	 * @return Whether the object is equal to the vector
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Vector2 vector2 = (Vector2) o;
		return getX() == vector2.getX() && getY() == vector2.getY();
	}

	/**
	 * Creates a copy of this {@code Vector2}
	 *
	 * @return The copy
	 */
	public Vector2 copy() {
		return new Vector2(getX(), getY());
	}

	/**
	 * Returns the hash-code of this {@code Vector2}, generated
	 * from its {@code x} and {@code y} values.
	 *
	 * @return The hash code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY());
	}

	/**
	 * Returns a string representation of the {@code Vector2}
	 * suitable for debugging.
	 *
	 * @return A string representation of the {@code Vector2}
	 */
	@Override
	public String toString() {
		return "Vector2{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
