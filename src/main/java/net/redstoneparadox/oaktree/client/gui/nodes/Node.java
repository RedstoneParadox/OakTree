package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeAlignment;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

/**
 * The base class for all nodes.
 */
public class Node<T extends Node> {

    public float x = 0.0f;
    public float y = 0.0f;
    public float width = 0.1f;
    public float height = 0.1f;

    NodeAlignment alignment = NodeAlignment.TOP_LEFT;
    NodeAlignment anchor = NodeAlignment.TOP_LEFT;

    StyleBox defaultStyle = null;

    boolean expand = false;

    StyleBox currentStyle = null;

    /**
     * Sets the position of the node on the screen relative to the parent.
     * Values are in pixels. Due to the way screen coordinates work,
     * positive y is down so a position of (10, 10) will place the node 10
     * pixels to the left and 10 pixels below the top-left corner of the
     * screen.
     *
     * @param posX The new x position in pixels.
     * @param posY The new y position in pixels.
     * @return The node itself.
     */
    public T setPosition(float posX, float posY) {
        x = posX;
        y = posY;
        return (T)this;
    }

    /**
     * Sets the width and height of this node. Values are in pixels.
     * Due to the way screen coordinates work, positive y is down so a
     * size of (10, 10) would mean that the node extends 10 pixels to the
     * left of and 10 pixels down from it's position.
     *
     * @param width The new width of this node in pixels.
     * @param height The new height of this node in pixels.
     * @return The node itself.
     */
    public T setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return (T)this;
    }

    /**
     * Sets whether or not this should automatically expand to the
     * size of the parent node. If set to true, any size and position settings
     * will be ignored when drawing the node.
     *
     * @param value
     * @return The node itself.
     */
    public T setExpand(boolean value) {
        expand = value;
        return (T)this;
    }

    /**
     * Sets the default {@link StyleBox} for this node. For most
     * nodes, this is the only style, but some will have multiple
     * styles so it is considered the default style.
     *
     * @param style The StyleBox for this node.
     * @return The node itself.
     */
    public T setDefaultStyle(StyleBox style) {
        defaultStyle = style;
        return (T)this;
    }

    /**
     * Sets the alignment of the node relative to it's position
     * using a {@link NodeAlignment}. For example, a value of
     * {@link NodeAlignment#CENTER} will cause the node to be
     * drawn centered on its position.
     *
     * @param alignment
     * @return
     */
    public T setAlignment(NodeAlignment alignment) {
        this.alignment = alignment;
        return (T)this;
    }

    /**
     * Anchors the position of this node relative to the parent using
     * a {@link NodeAlignment}. For example, a value of
     * {@link NodeAlignment#CENTER} and a position of (10, 0)
     * will result in the node being placed 10 pixels to the left of
     * the parent node's center.
     *
     * @param anchor The {@link NodeAlignment} to anchor to.
     * @return The node itself.
     */
    public T setAnchor(NodeAlignment anchor) {
        this.anchor = anchor;
        return (T)this;
    }

    public void setup(MinecraftClient minecraftClient_1, int int_1, int int_2, OakTreeGUI gui) {
    }

    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (expand) {
            width = containerWidth;
            height = containerHeight;
        }
        currentStyle = defaultStyle;
    }

    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (currentStyle != null) {
            ScreenVec anchorOffset = anchor.getOffset(containerWidth, containerHeight);
            ScreenVec alignmentOffset = alignment.getOffset(width, height);

            float trueX = x + anchorOffset.x + offsetX - alignmentOffset.x;
            float trueY = y + anchorOffset.y + offsetY - alignmentOffset.y;

            currentStyle.draw(trueX, trueY, width, height, gui);
        }
    }

}
