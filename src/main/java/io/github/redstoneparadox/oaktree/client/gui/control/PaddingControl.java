package io.github.redstoneparadox.oaktree.client.gui.control;

public abstract class PaddingControl<C extends Control> extends Control<C> {
    float topPadding = 0.0f;
    float bottomPadding = 0.0f;
    float leftPadding = 0.0f;
    float rightPadding = 0.0f;

    /**
     * Sets the padding for all 4 sides.
     *
     * @param padding The padding value.
     * @return The control itself.
     */
    public C padding(float padding) {
        this.topPadding = padding;
        this.bottomPadding = padding;
        this.leftPadding = padding;
        this.rightPadding = padding;
        return (C) this;
    }

    /**
     * Sets the padding for the top.
     *
     * @param topPadding The padding value.
     * @return The control itself.
     */
    public C topPadding(float topPadding) {
        this.topPadding = topPadding;
        return (C) this;
    }

    /**
     * Sets the padding for the bottom.
     *
     * @param bottomPadding The padding value.
     * @return The control itself.
     */
    public C bottomPadding(float bottomPadding) {
        this.bottomPadding = bottomPadding;
        return (C) this;
    }

    /**
     * Sets the padding for the left side.
     *
     * @param leftPadding The padding value.
     * @return The control itself.
     */
    public C leftPadding(float leftPadding) {
        this.leftPadding = leftPadding;
        return (C) this;
    }

    /**
     * Sets the padding for the right side.
     *
     * @param rightPadding The padding value.
     * @return The control itself.
     */
    public C rightPadding(float rightPadding) {
        this.rightPadding = rightPadding;
        return (C) this;
    }
}
