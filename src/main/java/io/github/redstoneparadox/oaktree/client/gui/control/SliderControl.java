package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlDirection;
import io.github.redstoneparadox.oaktree.client.gui.util.GuiFunction;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;

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
    public void preDraw(int mouseX, int mouseY, float deltaTime, ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        ScreenVec relativeMousePos = relativeMousePosition(mouseX, mouseY);


    }
}
