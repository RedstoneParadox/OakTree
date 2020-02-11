package io.github.redstoneparadox.oaktree.client.gui.util;

/**
 * A helper class for creating RGBA colors.
 */
public class RGBAColor {

    public float redChannel;
    public float blueChannel;
    public float greenChannel;
    public float alphaChannel;

    public RGBAColor(float red, float green, float blue, float alpha) {
        redChannel = red;
        greenChannel = green;
        blueChannel = blue;
        alphaChannel = alpha;
    }

    public RGBAColor(float red, float green, float blue) {
        this(red, green, blue, 1.0f);
    }

    public RGBAColor(int red, int green, int blue) {
        this(red/255.0f, green/255.0f, blue/255.0f);
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
