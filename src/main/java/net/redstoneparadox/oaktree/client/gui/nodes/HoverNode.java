package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.InteractionListener;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

import java.util.ArrayList;

public class HoverNode extends InteractiveNode{

    private ArrayList<InteractionListener> mouseEnterListeners = new ArrayList<>();
    private ArrayList<InteractionListener> mouseExitListeners = new ArrayList<>();
    private ArrayList<InteractionListener> mouseHoverListeners = new ArrayList<>();

    private boolean mouseCurrentlyWithin = false;

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

    public HoverNode addMouseEnterListener(InteractionListener listener) {
        mouseEnterListeners.add(listener);
        return this;
    }

    public HoverNode addMouseExitListener(InteractionListener listener) {
        mouseExitListeners.add(listener);
        return this;
    }

    public HoverNode addMouseHoverListener(InteractionListener listener) {
        mouseHoverListeners.add(listener);
        return this;
    }
}
