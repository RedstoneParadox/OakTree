package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeFunction;

public class ButtonNode extends InteractiveNode<ButtonNode> {

    private NodeFunction<ButtonNode> onClick = (((client, mouse, gui, node) -> {}));
    private NodeFunction<ButtonNode> whileHeld = (((client, mouse, gui, node) -> {}));
    private NodeFunction<ButtonNode> onRelease = (((client, mouse, gui, node) -> {}));

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
    public void updateListeners(Mouse mouse, MinecraftClient client, OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY) {
        if (toggleable) {
            if (mouseWithin) {
                if (gui.mouseButtonJustClicked("left")) {
                    held = !held;

                    if (held) {
                        whileHeld.invoke(client, mouse, gui, this);
                    }
                    else {
                        onRelease.invoke(client, mouse, gui, this);
                    }
                }
                else if (gui.mouseButtonHeld("left") && held) {
                    whileHeld.invoke(client, mouse, gui, this);
                }
            }
        }
       else {
            if (mouseWithin) {
                if (gui.mouseButtonHeld("left") && !held) {
                    held = true;
                    onClick.invoke(client, mouse, gui, this);
                }
                else if (held && gui.mouseButtonHeld("left")) {
                    whileHeld.invoke(client, mouse, gui, this);
                }
                else if (held && !gui.mouseButtonHeld("left")) {
                    held = false;
                    onRelease.invoke(client, mouse, gui, this);
                }
            }
            else if (held) {
                held = false;
                onRelease.invoke(client, mouse, gui, this);
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
