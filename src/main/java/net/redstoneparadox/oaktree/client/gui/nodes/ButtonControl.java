package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;

public class ButtonControl extends InteractiveControl<ButtonControl> {

    private NodeFunction<ButtonControl> onClick = (((gui, node) -> {}));
    private NodeFunction<ButtonControl> whileHeld = (((gui, node) -> {}));
    private NodeFunction<ButtonControl> onRelease = (((gui, node) -> {}));

    private boolean toggleable = false;

    private boolean held = false;

    private StyleBox heldStyle = null;

    public ButtonControl setToggleable(boolean value) {
        toggleable = value;
        return this;
    }

    public ButtonControl setHeldStyle(StyleBox value) {
        heldStyle = value;
        return this;
    }

    public ButtonControl onClick(NodeFunction<ButtonControl> listener) {
        onClick = listener;
        return this;
    }

    public ButtonControl whileHeld(NodeFunction<ButtonControl> listener) {
        whileHeld = listener;
        return this;
    }

    public ButtonControl onRelease(NodeFunction<ButtonControl> listener) {
        onRelease = listener;
        return this;
    }


    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        if (toggleable) {
            if (isMouseWithin) {
                if (gui.mouseButtonJustClicked("left")) {
                    held = !held;

                    if (held) {
                        whileHeld.invoke(gui, this);
                    }
                    else {
                        onRelease.invoke(gui, this);
                    }
                }
                else if (gui.mouseButtonHeld("left") && held) {
                    whileHeld.invoke(gui, this);
                }
            }
        }
        else {
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
            }
            else if (held) {
                held = false;
                onRelease.invoke(gui, this);
            }
        }


        if (held && heldStyle != null) {
            currentStyle = heldStyle;
        }
    }


}
