package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public abstract class PaddingControl<C extends PaddingControl> extends Control<C> {
    public float topPadding = 0.0f;
    public float bottomPadding = 0.0f;
    public float leftPadding = 0.0f;
    public float rightPadding = 0.0f;

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

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, ControlGui gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
    }

    ScreenVec innerDimensions(float spaceWidth, float spaceHeight) {
        float innerWidth = spaceWidth - leftPadding - rightPadding;
        float innerHeight = spaceHeight - topPadding - bottomPadding;

        return new ScreenVec(innerWidth, innerHeight);
    }

    ScreenVec innerPosition(float spaceX, float spaceY) {
        float innerX = spaceX + leftPadding;
        float innerY = spaceY + topPadding;

        return new ScreenVec(innerX, innerY);
    }
}
