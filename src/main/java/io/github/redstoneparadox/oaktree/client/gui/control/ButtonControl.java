package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.GuiFunction;

public class ButtonControl extends InteractiveControl<ButtonControl> {

    public GuiFunction<ButtonControl> onClick = (((gui, node) -> {}));
    public GuiFunction<ButtonControl> whileHeld = (((gui, node) -> {}));
    public GuiFunction<ButtonControl> onRelease = (((gui, node) -> {}));

    public boolean toggleable = false;

    private boolean held = false;

    public StyleBox heldStyle = null;

    public ButtonControl() {
        this.id = "button";
    }

    public ButtonControl toggleable(boolean toggleable) {
        this.toggleable = toggleable;
        return this;
    }

    public ButtonControl heldStyle(StyleBox style) {
        heldStyle = style;
        internalTheme.add("self", "held", style);
        return this;
    }

    public ButtonControl onClick(GuiFunction<ButtonControl> listener) {
        onClick = listener;
        return this;
    }

    public ButtonControl whileHeld(GuiFunction<ButtonControl> listener) {
        whileHeld = listener;
        return this;
    }

    public ButtonControl onRelease(GuiFunction<ButtonControl> listener) {
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

    @Override
    void applyTheme(Theme theme) {
        super.applyTheme(theme);
        heldStyle = getStyle(theme, "held");
    }
}
