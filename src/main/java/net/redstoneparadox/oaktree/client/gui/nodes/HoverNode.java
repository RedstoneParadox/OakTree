package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.InteractionListener;

import java.util.ArrayList;

public class HoverNode extends InteractiveNode{

    private ArrayList<InteractionListener<HoverNode>> mouseEnterListeners = new ArrayList<>();
    private ArrayList<InteractionListener<HoverNode>> mouseExitListeners = new ArrayList<>();
    private ArrayList<InteractionListener<HoverNode>> mouseHoverListeners = new ArrayList<>();

    private boolean mouseCurrentlyWithin = false;

    private StyleBox hoverStyle = null;

    public HoverNode setHoverStyle(StyleBox value) {
        hoverStyle = value;
        return this;
    }

    public HoverNode onMouseEnter(InteractionListener<HoverNode> listener) {
        mouseEnterListeners.add(listener);
        return this;
    }

    public HoverNode onMouseExit(InteractionListener<HoverNode> listener) {
        mouseExitListeners.add(listener);
        return this;
    }

    public HoverNode whileMouseHovers(InteractionListener<HoverNode> listener) {
        mouseHoverListeners.add(listener);
        return this;
    }

    @Override
    public void updateListeners(Mouse mouse, MinecraftClient client, Window window, OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY) {
        if (!mouseCurrentlyWithin && mouseWithin) {
            mouseCurrentlyWithin = true;
            mouseEnterListeners.iterator().forEachRemaining(listener -> listener.invoke(client, mouse, gui, this));
        }
        else if (mouseCurrentlyWithin && !mouseWithin) {
            mouseCurrentlyWithin = false;
            mouseEnterListeners.iterator().forEachRemaining(listener -> listener.invoke(client, mouse, gui, this));
        }

        if (mouseCurrentlyWithin) {
            mouseHoverListeners.iterator().forEachRemaining(listener -> listener.invoke(client, mouse, gui, this));
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
