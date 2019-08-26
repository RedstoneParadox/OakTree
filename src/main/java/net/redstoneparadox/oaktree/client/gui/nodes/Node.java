package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.redstoneparadox.oaktree.client.gui.OakTreeContainerScreen;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.OakTreeScreen;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeAlignment;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

/**
 * The base class for all nodes.
 */
public class Node<T extends Node> {

    public float x = 0.0f;
    public float y = 0.0f;
    float width = 0.1f;
    float height = 0.1f;

    NodeAlignment alignment = NodeAlignment.TOP_LEFT;
    NodeAlignment anchor = NodeAlignment.TOP_LEFT;

    NodeFunction<T> onTick = (gui, node) -> {};

    StyleBox defaultStyle = null;

    boolean expand = false;

    StyleBox currentStyle = null;


    public float trueX = 0.0f;
    public float trueY = 0.0f;
    public float trueWidth = 0.0f;
    public float trueHeight = 0.0f;

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

    /**
     * Sets a function to run every time this
     * node is ticked.
     *
     * @param function the function to run.
     * @return The node itself.
     */
    public T onTick(NodeFunction<T> function) {
        onTick = function;
        return (T)this;
    }

    /**
     * Helper method for taking a tree of nodes and using
     * them to automatically open a new
     * {@link OakTreeScreen}.
     *
     * @param isPauseScreen Whether the screen should pause
     *                      the game.
     * @param parentScreen The screen to open after this one
     *                     is closed. Can be null.
     */
    public void openAsScreen(boolean isPauseScreen, Screen parentScreen) {
        MinecraftClient.getInstance().openScreen(new OakTreeScreen(this, isPauseScreen, parentScreen));
    }

    /**
     * Helper method for taking a tree of nodes and using
     * them to automatically create a new
     * {@link OakTreeContainerScreen}. Unlike the previous
     * method, the screen will not be opened due to the
     * way Fabric API requires you to open container
     * screens.
     *
     * @param isPauseScreen Whether the screen should pause
     *                      the game.
     * @param parentScreen The screen to open after this
     *                     one is closed. Can be null.
     * @param container The {@link Container} of the
     *                  container screen.
     * @param playerInventory The inventory of the player
     *                        that opened the screen.
     * @param text The title of the inventory.
     * @param <C> The type of {@link Container} that uses
     *           this screen.
     * @return The resulting {@link OakTreeContainerScreen}.
     */
    public <C extends Container> AbstractContainerScreen<C> toContainerScreen(boolean isPauseScreen, Screen parentScreen, C container, PlayerInventory playerInventory, Text text) {
        return new OakTreeContainerScreen<>(this, isPauseScreen, parentScreen, container, playerInventory, text);
    }

    @Deprecated
    public void setup(MinecraftClient minecraftClient_1, int int_1, int int_2, OakTreeGUI gui) {
    }

    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        onTick.invoke(gui, (T)this);

        if (!expand) {
            ScreenVec anchorOffset = anchor.getOffset(containerWidth, containerHeight);
            ScreenVec alignmentOffset = alignment.getOffset(width, height);

            trueX = x + anchorOffset.x + offsetX - alignmentOffset.x;
            trueY = y + anchorOffset.y + offsetY - alignmentOffset.y;

            trueWidth = width;
            trueHeight = height;
        }
        else {
            trueX = offsetX;
            trueY = offsetY;

            trueWidth = containerWidth;
            trueHeight = containerHeight;
        }
        currentStyle = defaultStyle;
    }

    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (currentStyle != null) {
            currentStyle.draw(trueX, trueY, trueWidth, trueHeight, gui);
        }
    }

}
