package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public class ContainerNode extends Node {

    public float topMargin = 0.0f;
    public float bottomMargin = 0.0f;
    public float leftMargin = 0.0f;
    public float rightMargin = 0.0f;

    public Node child;

    public ContainerNode(float xPos, float yPos, float nodeWidth, float nodeHeight, Node childNode) {
        super(xPos, yPos, nodeWidth, nodeHeight);
        child = childNode;
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
    public void draw(int int_1, int int_2, float float_1, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {

    }
}
