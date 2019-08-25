package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;

public class HoverNode extends InteractiveNode<HoverNode> {

    private NodeFunction<HoverNode> mouseEnter = ((client, mouse, gui, node) -> {});
    private NodeFunction<HoverNode> mouseExit = ((client, mouse, gui, node) -> {});
    private NodeFunction<HoverNode> whileHovered = ((client, mouse, gui, node) -> {});

    private boolean mouseCurrentlyWithin = false;

    private StyleBox hoverStyle = null;

    public HoverNode setHoverStyle(StyleBox value) {
        hoverStyle = value;
        return this;
    }

    public HoverNode onMouseEnter(NodeFunction<HoverNode> listener) {
        mouseEnter = listener;
        return this;
    }

    public HoverNode onMouseExit(NodeFunction<HoverNode> listener) {
        mouseExit = listener;
        return this;
    }

    public HoverNode whileMouseHovers(NodeFunction<HoverNode> listener) {
        whileHovered = listener;
        return this;
    }

    @Override
    public void updateListeners(Mouse mouse, MinecraftClient client, OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY) {
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
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        if (mouseCurrentlyWithin && hoverStyle != null) {
            currentStyle = hoverStyle;
        }
    }
}
