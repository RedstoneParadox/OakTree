package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public class ContainerNode extends Node {

    public float topMargin = 0.0f;
    public float bottomMargin = 0.0f;
    public float leftMargin = 0.0f;
    public float rightMargin = 0.0f;

    public Node child;

    public ContainerNode() {
        super();
    }

    public ContainerNode setMargin(float margin) {
        if (margin <= 0.0f) {
            margin = 0.0f;
        }

        topMargin = margin;
        bottomMargin = margin;
        leftMargin = margin;
        rightMargin = margin;
        return this;
    }

    @Override
    public void preDraw(OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight);
        float actualLeft = x + leftMargin + offsetX;
        float actualTop = y + topMargin + offsetY;

        float actualWidth = width - leftMargin - rightMargin;
        float actualHeight = height - topMargin - bottomMargin;

        child.preDraw(gui, actualLeft, actualTop, actualWidth, actualHeight);
    }

    @Override
    public void draw(int int_1, int int_2, float float_1, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        float actualLeft = x + leftMargin + offsetX;
        float actualTop = y + topMargin + offsetY;

        float actualWidth = width - leftMargin - rightMargin;
        float actualHeight = height - topMargin - bottomMargin;

        child.draw(int_1, int_2, float_1, gui, actualLeft, actualTop, actualWidth, actualHeight);
    }
}
