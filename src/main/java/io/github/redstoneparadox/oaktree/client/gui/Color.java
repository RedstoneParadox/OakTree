package io.github.redstoneparadox.oaktree.client.gui;

import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

/**
 * A class for storing color values.
 */
public final class Color {
    public static final Color BLACK = rgb(0.0f, 0.0f, 0.0f);
    public static final Color DARK_BLUE = rgb(170);
    public static final Color DARK_GREEN = rgb(43520);
    public static final Color DARK_AQUA = rgb(43690);
    public static final Color DARK_RED = rgb(11141120);
    public static final Color DARK_PURPLE = rgb(11141290);
    public static final Color GOLD = rgb(16755200);
    public static final Color GREY = rgb(11184810);
    public static final Color DARK_GREY = rgb(5592405);
    public static final Color BLUE = rgb(0.0f, 0.0f, 1.0f);
    public static final Color GREEN = rgb(0.0f, 1.0f, 0.0f);
    public static final Color AQUA = rgb(5636095);
    public static final Color RED = rgb(1.0f, 0.0f, 0.0f);
    public static final Color LIGHT_PURPLE = rgb(16733695);
    public static final Color YELLOW = rgb(16777045);
    public static final Color WHITE = rgb(1.0f, 1.0f, 1.0f);

    public final float red;
    public final float green;
    public final float blue;
    public final float alpha;

    private Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public static Color rgb(int red, int green, int blue) {
        return rgba(red, green, blue, 1.0f);
    }

    public static Color rgb(float red, float green, float blue) {
        return rgba(red, green, blue, 1.0f);
    }

    public static Color rgba(int red, int green, int blue, float alpha) {
        return rgba(red/255.0f, green/255.0f, blue/255.0f, alpha);
    }

    public static Color rgba(float red, float green, float blue, float alpha) {
        return new Color(clamp(red), clamp(green), clamp(blue), clamp(alpha));
    }

    public static Color hsv(float hue, int saturation, int value) {
        return hsv(hue, saturation, value, 1.0f);
    }

    public static Color hsv(float hue, int saturation, int value, float alpha) {
        hue = Math.max(0.0f, Math.min(hue, 360.0f));
        saturation = Math.max(0, Math.min(saturation, 100));
        value = Math.max(0, Math.min(value, 100));

        int hi = (int) Math.floor(hue/60.0) % 6;
        double f = (hue/60.0) - Math.floor(hue/60.0);

        double p = value * (1.0 - saturation);
        double q = value * (1.0 - (f * saturation));
        double t = value * (1.0 - ((1.0 - f) * saturation));

        Color ret;

        switch (hi)
        {
            case 0:
                ret = toRGBA(value, t, p, alpha);
                break;
            case 1:
                ret = toRGBA(q, value, p, alpha);
                break;
            case 2:
                ret = toRGBA(p, value, t, alpha);
                break;
            case 3:
                ret = toRGBA(p, q, value, alpha);
                break;
            case 4:
                ret = toRGBA(t, p, value, alpha);
                break;
            case 5:
                ret = toRGBA(value, p, q, alpha);
                break;
            default:
                ret = rgba(0, 0, 0, alpha);
                break;
        }
        return ret;
    }

    public static Color rgb(int integer) {
        int red = (integer >> 16) & 0xff;
        int green = (integer >> 8) & 0xff;
        int blue = (integer) & 0xff;

        return rgb(red, green, blue);
    }

    private static Color toRGBA(double r, double b, double g, float a) {
        return rgba((byte)(r*255.0) + 128, (byte)(r*255.0) + 128, (byte)(r*255.0) + 128, a);
    }

    public Color withAlpha(float alpha) {
        return new Color(this.red, this.blue, this.green, alpha);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static Color white() { return WHITE; }

    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static Color black() {
        return BLACK;
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static Color red() {
        return RED;
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static Color green() {
        return GREEN;
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval
    public static Color blue() { return BLUE; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return Float.compare(color.red, red) == 0 &&
                Float.compare(color.green, green) == 0 &&
                Float.compare(color.blue, blue) == 0 &&
                Float.compare(color.alpha, alpha) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue, alpha);
    }

    private static float clamp(float value) {
        if (value < 0.0f) return 0.0f;
        else if (value > 1.0f) return 1.0f;
        return value;
    }
}
