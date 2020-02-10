package io.github.redstoneparadox.oaktree.client.gui.control;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.ScreenBuilder;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import io.github.redstoneparadox.oaktree.client.gui.util.GuiFunction;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;

/**
 * The base class for all controls.
 */
public class Control<C extends Control> {

    public float x = 0.0f;
    public float y = 0.0f;
    public float width = 0.1f;
    public float height = 0.1f;

    boolean visible = true;

    public ControlAnchor anchor = ControlAnchor.TOP_LEFT;

    public GuiFunction<C> onTick = (gui, control) -> {};

    public StyleBox defaultStyle = null;

    public boolean expand = false;

    StyleBox currentStyle = null;

    public String id;

    float trueX = 0.0f;
    float trueY = 0.0f;

    public Control() {
        this.id = "control";
    }

    public C id(String id) {
        this.id = id;
        return (C)this;
    }

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
    public C position(float posX, float posY) {
        x = posX;
        y = posY;
        return (C)this;
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
    public C size(float width, float height) {
        this.width = width;
        this.height = height;
        return (C)this;
    }

    /**
     * Sets whether or not this node should be visible. Any nodes that
     * are not visible will not be drawn, cannot be interacted with,
     * and will cause their children to not be drawn (if they have any).
     *
     * @param value Whether or not this node should be visible.
     * @return The node itself.
     */
    public C visible(boolean value) {
        this.visible = value;
        return (C)this;
    }

    /**
     * Sets whether or not this should automatically expand to the
     * size of the parent node. If set to true, any size and position settings
     * will be ignored when drawing the node.
     *
     * @param value
     * @return The node itself.
     */
    public C expand(boolean value) {
        expand = value;
        return (C)this;
    }

    /**
     * Sets the default {@link StyleBox} for this node. For most
     * nodes, this is the only style, but some will have multiple
     * styles so it is considered the default style.
     *
     * @param style The StyleBox for this node.
     * @return The node itself.
     */
    public C defaultStyle(StyleBox style) {
        defaultStyle = style;
        return (C)this;
    }

    /**
     * Anchors the position of this node relative to the parent using
     * a {@link ControlAnchor}. For example, a value of
     * {@link ControlAnchor#CENTER} and a position of (10, 0)
     * will result in the node being placed 10 pixels to the left of
     * the parent node's center.
     *
     * @param anchor The {@link ControlAnchor} to anchor to.
     * @return The node itself.
     */
    public C anchor(ControlAnchor anchor) {
        this.anchor = anchor;
        return (C)this;
    }

    /**
     * Sets a function to run every time this
     * node is ticked.
     *
     * @param function the function to run.
     * @return The node itself.
     */
    public C onTick(GuiFunction<C> function) {
        onTick = function;
        return (C)this;
    }

    @Deprecated
    public void setup(MinecraftClient minecraftClient_1, int int_1, int int_2, OakTreeGUI gui) {
    }

    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;

        onTick.invoke(gui, (C)this);

        if (!expand) {
            ScreenVec anchorOffset = anchor.getOffset(containerWidth, containerHeight);
            ScreenVec drawOffset = anchor.getOffset(width, height);

            trueX = x + anchorOffset.x + offsetX - drawOffset.x;
            trueY = y + anchorOffset.y + offsetY - drawOffset.y;
        }
        else {
            trueX = offsetX;
            trueY = offsetY;

            width = containerWidth;
            height = containerHeight;
        }
        currentStyle = defaultStyle;
    }

    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;

        if (currentStyle != null) {
            currentStyle.draw(trueX, trueY, width, height, gui);
        }
    }

}
