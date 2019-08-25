package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;

public class ButtonNode extends InteractiveNode<ButtonNode> {

    private NodeFunction<ButtonNode> onClick = (((gui, node) -> {}));
    private NodeFunction<ButtonNode> whileHeld = (((gui, node) -> {}));
    private NodeFunction<ButtonNode> onRelease = (((gui, node) -> {}));

    private boolean toggleable = false;

    private boolean held = false;

    private StyleBox heldStyle = null;

    public ButtonNode setToggleable(boolean value) {
        toggleable = value;
        return this;
    }

    public ButtonNode setHeldStyle(StyleBox value) {
        heldStyle = value;
        return this;
    }

    public ButtonNode onClick(NodeFunction<ButtonNode> listener) {
        onClick = listener;
        return this;
    }

    public ButtonNode whileHeld(NodeFunction<ButtonNode> listener) {
        whileHeld = listener;
        return this;
    }

    public ButtonNode onRelease(NodeFunction<ButtonNode> listener) {
        onRelease = listener;
        return this;
    }

    @Override
    public void updateListeners(OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY) {
        if (toggleable) {
            if (mouseWithin) {
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
            if (mouseWithin) {
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
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        if (held && heldStyle != null) {
            currentStyle = heldStyle;
        }
    }


}
