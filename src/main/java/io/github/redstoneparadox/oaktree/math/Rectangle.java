package io.github.redstoneparadox.oaktree.math;

import java.util.Objects;

public class Rectangle {
	public static Rectangle DEFAULT = new Rectangle(0, 0, 1, 1);	
	private int x;
	private int y;
	private int width;
	private int height;

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean isPointWithin(int pointX, int pointY) {
		return pointX >= x && pointY >= y && pointX <= x + width && pointY <= y + height;
	}

	public Rectangle offset(int offsetX, int offsetY) {
		return new Rectangle(x + offsetX, y + offsetY, width, height);
	}

	public Rectangle copy() {
		return new Rectangle(x, y, width, height);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Rectangle rectangle = (Rectangle) o;
		return x == rectangle.x && y == rectangle.y && width == rectangle.width && height == rectangle.height;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, width, height);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
