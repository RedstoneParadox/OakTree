package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.InteractionListener;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

import java.util.ArrayList;

public class HoverNode extends InteractiveNode{

    private ArrayList<InteractionListener> mouseEnterListeners = new ArrayList<>();
    private ArrayList<InteractionListener> mouseExitListeners = new ArrayList<>();
    private ArrayList<InteractionListener> mouseHoverListeners = new ArrayList<>();

    private boolean mouseCurrentlyWithin = false;

    private StyleBox hoverStyle = null;

    public HoverNode setHoverStyle(StyleBox value) {
        hoverStyle = value;
        return this;
    }

    public HoverNode onMouseEnter(InteractionListener listener) {
        mouseEnterListeners.add(listener);
        return this;
    }

    public HoverNode onMouseExit(InteractionListener listener) {
        mouseExitListeners.add(listener);
        return this;
    }

    public HoverNode whileMouseHovers(InteractionListener listener) {
        mouseHoverListeners.add(listener);
        return this;
    }

    @Override
    public void updateListeners(Mouse mouse, MinecraftClient client, Window window, OakTreeGUI gui, boolean mouseWithin) {
        if (!mouseCurrentlyWithin && mouseWithin) {
            mouseCurrentlyWithin = true;
            mouseEnterListeners.iterator().forEachRemaining(listener -> listener.invoke(client, mouse, gui));
        }
        else if (mouseCurrentlyWithin && !mouseWithin) {
            mouseCurrentlyWithin = false;
            mouseEnterListeners.iterator().forEachRemaining(listener -> listener.invoke(client, mouse, gui));
        }

        if (mouseCurrentlyWithin) {
            mouseHoverListeners.iterator().forEachRemaining(listener -> listener.invoke(client, mouse, gui));
        }
    }

    @Override
    public void preDraw(OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(gui, window, offsetX, offsetY, containerWidth, containerHeight);
        if (mouseCurrentlyWithin && hoverStyle != null) {
            currentStyle = hoverStyle;
        }
    }
}
