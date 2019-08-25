package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.InteractionListener;

import java.util.ArrayList;

public class HoverNode extends InteractiveNode<HoverNode> {

    private InteractionListener<HoverNode> mouseEnter = ((client, mouse, gui, node) -> {});
    private InteractionListener<HoverNode> mouseExit = ((client, mouse, gui, node) -> {});
    private InteractionListener<HoverNode> whileHovered = ((client, mouse, gui, node) -> {});

    private boolean mouseCurrentlyWithin = false;

    private StyleBox hoverStyle = null;

    public HoverNode setHoverStyle(StyleBox value) {
        hoverStyle = value;
        return this;
    }

    public HoverNode onMouseEnter(InteractionListener<HoverNode> listener) {
        mouseEnter = listener;
        return this;
    }

    public HoverNode onMouseExit(InteractionListener<HoverNode> listener) {
        mouseExit = listener;
        return this;
    }

    public HoverNode whileMouseHovers(InteractionListener<HoverNode> listener) {
        whileHovered = listener;
        return this;
    }

    @Override
    public void updateListeners(Mouse mouse, MinecraftClient client, Window window, OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY) {
        if (!mouseCurrentlyWithin && mouseWithin) {
            mouseCurrentlyWithin = true;
            mouseEnter.invoke(client, mouse, gui, this);
        }
        else if (mouseCurrentlyWithin && !mouseWithin) {
            mouseCurrentlyWithin = false;
            mouseExit.invoke(client, mouse, gui, this);
        }

        if (mouseCurrentlyWithin) {
            whileHovered.invoke(client, mouse, gui, this);
        }
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, window, offsetX, offsetY, containerWidth, containerHeight);
        if (mouseCurrentlyWithin && hoverStyle != null) {
            currentStyle = hoverStyle;
        }
    }
}
