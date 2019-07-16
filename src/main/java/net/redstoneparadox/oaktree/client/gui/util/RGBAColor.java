package net.redstoneparadox.oaktree.client.gui.util;

/**
 * A helper class for creating RGBA colors.
 */
public class RGBAColor {

    public float redChannel = 0.0f;
    public float blueChannel = 0.0f;
    public float greenChannel = 0.0f;
    public float alphaChannel = 0.0f;

    public RGBAColor(float red, float green, float blue, float alpha) {
        redChannel = red;
        greenChannel = green;
        blueChannel = blue;
        alphaChannel = alpha;
    }

    public RGBAColor(float red, float green, float blue) {
        this(red, green, blue, 1.0f);
    }

    public static RGBAColor white() {
        return  new RGBAColor(1.0f, 1.0f, 1.0f);
    }

    public static RGBAColor black() {
        return  new RGBAColor(0.0f, 0.0f, 0.0f);
    }

    public static RGBAColor red() {
        return  new RGBAColor(1.0f, 0.0f, 0.0f);
    }

    public static RGBAColor green() {
        return  new RGBAColor(0.0f, 1.0f, 0.0f);
    }

    public static RGBAColor blue() {
        return  new RGBAColor(0.0f, 0.0f, 1.0f);
    }
}
