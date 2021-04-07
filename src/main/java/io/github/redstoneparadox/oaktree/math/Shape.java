package io.github.redstoneparadox.oaktree.math;

public interface Shape {
	boolean isPointWithin(int x, int y);

	default boolean isPointWithin(Vector2 vector2) {
		return isPointWithin(vector2.x, vector2.y);
	}
}
