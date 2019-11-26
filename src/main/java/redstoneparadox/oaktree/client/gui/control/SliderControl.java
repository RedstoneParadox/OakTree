package redstoneparadox.oaktree.client.gui.control;

import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.style.StyleBox;
import redstoneparadox.oaktree.client.gui.util.ControlDirection;
import redstoneparadox.oaktree.client.gui.util.GuiFunction;
import redstoneparadox.oaktree.client.gui.util.ScreenVec;

public class SliderControl extends InteractiveControl<SliderControl> {

    private GuiFunction<SliderControl> onDrag = (gui, node) -> {};
    private ControlDirection direction = ControlDirection.RIGHT;
    private final Control dragControl = new Control();

    public SliderControl setDragNodeStyle(StyleBox style) {
        dragControl.setDefaultStyle(style);
        return this;
    }

    public SliderControl setDragNodeSize(float width, float height) {
        dragControl.setSize(width, height);
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        ScreenVec relativeMousePos = relativeMousePosition(mouseX, mouseY);


    }
}
