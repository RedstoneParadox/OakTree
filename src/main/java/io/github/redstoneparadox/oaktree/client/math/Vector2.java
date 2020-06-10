package io.github.redstoneparadox.oaktree.client.math;

import java.util.Objects;

public class Vector2 {
	public int x;
	public int y;

	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vector2() {
		this(0, 0);
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
		return x == vector2.x && y == vector2.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
