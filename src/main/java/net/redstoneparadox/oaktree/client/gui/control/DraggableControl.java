package net.redstoneparadox.oaktree.client.gui.control;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.GuiFunction;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

/**
 * @apiNote You probably shouldn't use this at this time.
 */
public class DraggableControl extends InteractiveControl<DraggableControl> {

    private GuiFunction<DraggableControl> onClick = (gui, node) -> {};
    private GuiFunction<DraggableControl> whileHeld = (gui, node) -> {};
    private GuiFunction<DraggableControl> onRelease = (((gui, node) -> {}));

    private boolean held = false;

    public DraggableControl onClick(GuiFunction<DraggableControl> listener) {
        onClick = listener;
        return this;
    }

    public DraggableControl whileHeld(GuiFunction<DraggableControl> listener) {
        whileHeld = listener;
        return this;
    }

    public DraggableControl onRelease(GuiFunction<DraggableControl> listener) {
        onRelease = listener;
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        if (isMouseWithin) {
            ScreenVec mousePos = relativeMousePosition(mouseX, mouseY);

            if (isMouseWithin) {
                if (gui.mouseButtonHeld("left") && !held) {
                    held = true;
                    onClick.invoke(gui, this);
                }
                else if (held && gui.mouseButtonHeld("left")) {
                    whileHeld.invoke(gui, this);
                }
                else if (held && !gui.mouseButtonHeld("left")) {
                    held = false;
                    onRelease.invoke(gui, this);
                }

                if (held) {
                    x = clampPosition(mousePos.x, trueWidth);
                    y = clampPosition(mousePos.y, trueHeight);
                }
            }
            else if (held) {
                held = false;
                onRelease.invoke(gui, this);
            }
        }
    }

    private float clampPosition(float target, float highest) {
        if ((int)target <= 0) return 0;
        else if ((int)target >= (int)highest) return highest;
        return target;
    }
}
