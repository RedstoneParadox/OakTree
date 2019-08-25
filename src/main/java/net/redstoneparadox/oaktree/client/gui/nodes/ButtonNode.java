package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.style.StyleBox;
import net.redstoneparadox.oaktree.client.gui.util.InteractionListener;

public class ButtonNode extends InteractiveNode<ButtonNode> {

    private InteractionListener<ButtonNode> onClick = (((client, mouse, gui, node) -> {}));
    private InteractionListener<ButtonNode> whileHeld = (((client, mouse, gui, node) -> {}));
    private InteractionListener<ButtonNode> onRelease = (((client, mouse, gui, node) -> {}));

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

    public ButtonNode onClick(InteractionListener<ButtonNode> listener) {
        onClick = listener;
        return this;
    }

    public ButtonNode whileHeld(InteractionListener<ButtonNode> listener) {
        whileHeld = listener;
        return this;
    }

    public ButtonNode onRelease(InteractionListener<ButtonNode> listener) {
        onRelease = listener;
        return this;
    }

    @Override
    public void updateListeners(Mouse mouse, MinecraftClient client, Window window, OakTreeGUI gui, boolean mouseWithin, double mouseX, double mouseY) {
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
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, window, offsetX, offsetY, containerWidth, containerHeight);
        if (held && heldStyle != null) {
            currentStyle = heldStyle;
        }
    }


}
