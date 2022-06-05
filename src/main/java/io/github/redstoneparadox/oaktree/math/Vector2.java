package io.github.redstoneparadox.oaktree.math;

import java.util.Objects;

public class Vector2 {
	public static final Vector2 ZERO = new Vector2(0,0);

	private final int x;
	private final int y;

	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
		this(0, 0);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Vector2 add(Vector2 right) {
		return new Vector2(this.x + right.x, this.y + right.y);
	}

	public Vector2 add(int x, int y) {
		return new Vector2(this.x + x, this.y + y);
	}

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

	public Vector2 copy() {
		return new Vector2(getX(), getY());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getX(), getY());
	}

	@Override
	public String toString() {
		return "Vector2{" +
				"x=" + x +
				", y=" + y +
				'}';
	}
}
