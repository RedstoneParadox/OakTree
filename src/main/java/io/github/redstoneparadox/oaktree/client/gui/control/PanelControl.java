package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Control} containing any number of children
 * {@link Control} instances. Children are drawn
 * without any arrangement applied to them; subclasses
 * offer various arrangement options.
 *
 * @param <C> The {@link PanelControl} type.
 */
public class PanelControl<C extends PanelControl> extends PaddingControl<C> {

    public final List<Control> children = new ArrayList<>();

    public C child(Control child) {
        children.add(child);
        return (C) this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (!visible) return;
        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        preDrawChildren(mouseX, mouseY, deltaTime, gui);
    }

    void preDrawChildren(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        for (Control child: children) {
            if (child != null) child.preDraw(mouseX, mouseY, deltaTime, gui, innerX, innerY, innerWidth, innerHeight);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);
        for (Control child: children) {
            if (child != null) child.draw(mouseX, mouseY, deltaTime, gui);
        }
    }
}
