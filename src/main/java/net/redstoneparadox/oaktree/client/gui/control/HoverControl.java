package net.redstoneparadox.oaktree.client.gui.control;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;

public class HoverControl extends InteractiveControl<HoverControl> {

    private NodeFunction<HoverControl> mouseEnter = ((gui, node) -> {});
    private NodeFunction<HoverControl> mouseExit = ((gui, node) -> {});
    private NodeFunction<HoverControl> whileHovered = ((gui, node) -> {});

    private boolean mouseCurrentlyWithin = false;

    private StyleBox hoverStyle = null;

    public HoverControl setHoverStyle(StyleBox value) {
        hoverStyle = value;
        return this;
    }

    public HoverControl onMouseEnter(NodeFunction<HoverControl> listener) {
        mouseEnter = listener;
        return this;
    }

    public HoverControl onMouseExit(NodeFunction<HoverControl> listener) {
        mouseExit = listener;
        return this;
    }

    public HoverControl whileMouseHovers(NodeFunction<HoverControl> listener) {
        whileHovered = listener;
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        if (!mouseCurrentlyWithin && isMouseWithin) {
            mouseCurrentlyWithin = true;
            mouseEnter.invoke(gui, this);
        }
        else if (mouseCurrentlyWithin && !isMouseWithin) {
            mouseCurrentlyWithin = false;
            mouseExit.invoke(gui, this);
        }

        if (mouseCurrentlyWithin) {
            whileHovered.invoke(gui, this);
        }

        if (mouseCurrentlyWithin && hoverStyle != null) {
            currentStyle = hoverStyle;
        }
    }
}
