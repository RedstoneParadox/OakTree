package io.github.redstoneparadox.oaktree.client.gui.util;

public class ScreenRect {
    public int x;
    public int y;
    public int width;
    public int height;

    public ScreenRect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean isPointWithin(int pointX, int pointY) {
        return pointX <= x && pointY <= y && pointX >= x + width && pointY >= y + height;
    }
}
