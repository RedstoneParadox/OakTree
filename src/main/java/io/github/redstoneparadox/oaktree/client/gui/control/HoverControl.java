package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.GuiFunction;

import java.util.List;

public class HoverControl extends InteractiveControl<HoverControl> {

    public GuiFunction<HoverControl> mouseEnter = ((gui, node) -> {});
    public GuiFunction<HoverControl> mouseExit = ((gui, node) -> {});
    public GuiFunction<HoverControl> whileHovered = ((gui, node) -> {});

    private boolean mouseCurrentlyWithin = false;

    public StyleBox hoverStyle = null;

    public HoverControl() {
        this.id = "hover";
    }

    public HoverControl hoverStyle(StyleBox value) {
        hoverStyle = value;
        internalTheme.add("self", "hover", hoverStyle);
        return this;
    }

    public HoverControl onMouseEnter(GuiFunction<HoverControl> listener) {
        mouseEnter = listener;
        return this;
    }

    public HoverControl onMouseExit(GuiFunction<HoverControl> listener) {
        mouseExit = listener;
        return this;
    }

    public HoverControl whileMouseHovers(GuiFunction<HoverControl> listener) {
        whileHovered = listener;
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, List<Control<?>> controlList) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight, controlList);

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

    @Override
    void applyTheme(Theme theme) {
        super.applyTheme(theme);
        hoverStyle = getStyle(theme, "hover");
    }
}
