package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.Style;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;

import java.util.function.BiConsumer;

public class HoverControl extends InteractiveControl<HoverControl> {

    public BiConsumer<ControlGui, HoverControl> mouseEnter = (gui, node) -> {};
    public BiConsumer<ControlGui, HoverControl> mouseExit = (gui, node) -> {};
    public BiConsumer<ControlGui, HoverControl> whileHovered = (gui, node) -> {};

    private boolean mouseCurrentlyWithin = false;

    public Style hoverStyle = null;

    public HoverControl() {
        this.id = "hover";
    }

    public HoverControl hoverStyle(Style value) {
        hoverStyle = value;
        internalTheme.add("self", "hover", hoverStyle);
        return this;
    }

    public HoverControl onMouseEnter(BiConsumer<ControlGui, HoverControl> listener) {
        mouseEnter = listener;
        return this;
    }

    public HoverControl onMouseExit(BiConsumer<ControlGui, HoverControl> listener) {
        mouseExit = listener;
        return this;
    }

    public HoverControl whileMouseHovers(BiConsumer<ControlGui, HoverControl> listener) {
        whileHovered = listener;
        return this;
    }

    @Override
    public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
        if (!visible) return;
        super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

        if (!mouseCurrentlyWithin && isMouseWithin) {
            mouseCurrentlyWithin = true;
            mouseEnter.accept(gui, this);
        }
        else if (mouseCurrentlyWithin && !isMouseWithin) {
            mouseCurrentlyWithin = false;
            mouseExit.accept(gui, this);
        }

        if (mouseCurrentlyWithin) {
            whileHovered.accept(gui, this);
        }

        if (mouseCurrentlyWithin && hoverStyle != null) {
            currentStyle = hoverStyle;
        }
    }

    @Override
    void applyTheme(Theme theme) {
        super.applyTheme(theme);
        hoverStyle = getStyle(theme, "hover");
    }
}
