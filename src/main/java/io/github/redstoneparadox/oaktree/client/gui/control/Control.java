package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenRect;
import net.minecraft.client.MinecraftClient;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import io.github.redstoneparadox.oaktree.client.gui.util.GuiFunction;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

/**
 * The base class for all controls.
 */
public class Control<C extends Control> {
    public final ScreenRect area = new ScreenRect(0, 0, 1, 1);

    public boolean visible = true;
    public ControlAnchor anchor = ControlAnchor.TOP_LEFT;
    public GuiFunction<C> onTick = (gui, control) -> {};
    public StyleBox defaultStyle = null;
    public boolean expand = false;
    public String id;

    StyleBox currentStyle = null;
    Theme internalTheme = new Theme();

    int trueX = 0;
    int trueY = 0;

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
     * @param x The new x position in pixels.
     * @param y The new y position in pixels.
     * @return The node itself.
     */
    public C position(int x, int y) {
        area.x = x;
        area.y = y;
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
    public C size(int width, int height) {
        area.width = width;
        area.height = height;
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
        internalTheme.add("self", style);
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

    public void zIndex(List<Control<?>> controls) {
        controls.add(this);
    }

    public void setup(MinecraftClient client, ControlGui gui) {
        applyTheme(gui.getTheme());
    }

    public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
        if (!visible) return;

        onTick.invoke(gui, (C)this);

        if (!expand) {
            ScreenVec anchorOffset = anchor.getOffset(containerWidth, containerHeight);
            ScreenVec drawOffset = anchor.getOffset(area.width, area.height);

            trueX = area.x + anchorOffset.x + offsetX - drawOffset.x;
            trueY = area.y + anchorOffset.y + offsetY - drawOffset.y;
        }
        else {
            trueX = offsetX;
            trueY = offsetY;

            area.width = (int)containerWidth;
            area.height = (int)containerHeight;
        }
        currentStyle = defaultStyle;
    }

    void applyTheme(Theme theme) {
        defaultStyle = getStyle(theme, "default");
    }

    final StyleBox getStyle(Theme theme, String name) {
        StyleBox style = internalTheme.get("self/" + name);
        if (style == null && theme != null) style = theme.get(id + "/" + name);
        return style;
    }

    public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        if (!visible) return;

        if (currentStyle != null) {
            currentStyle.draw(trueX, trueY, area.width, area.height, gui);
        }
    }

}
