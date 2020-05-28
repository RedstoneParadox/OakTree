package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlDirection;
import io.github.redstoneparadox.oaktree.client.gui.util.GuiFunction;

import java.util.List;

public class SliderControl extends InteractiveControl<SliderControl> {

    public GuiFunction<SliderControl> onDrag = (gui, node) -> {};
    public ControlDirection direction = ControlDirection.RIGHT;
    private final Control dragControl = new Control();

    public SliderControl setDragNodeStyle(StyleBox style) {
        dragControl.defaultStyle(style);
        return this;
    }

    public SliderControl setDragNodeSize(float width, float height) {
        return this;
    }

    @Override
    public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, List<Control<?>> controlList, int mouseX, int mouseY) {
        if (!visible) return;
        super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, controlList, mouseX, mouseY);
    }
}
