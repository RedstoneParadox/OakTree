package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

/**
 * BoxNodes can have a child and margins.
 */
public class BoxNode extends Node {

    public float topMargin = 0.0f;
    public float bottomMargin = 0.0f;
    public float leftMargin = 0.0f;
    public float rightMargin = 0.0f;

    public Node child = null;

    public BoxNode setMargin(float margin) {
        if (margin <= 0.0f) {
            margin = 0.0f;
        }

        topMargin = margin;
        bottomMargin = margin;
        leftMargin = margin;
        rightMargin = margin;
        return this;
    }

    public BoxNode addChild(Node newChild) {
        child = newChild;
        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, Window window, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.preDraw(mouseX, mouseY, deltaTime, gui, window, offsetX, offsetY, containerWidth, containerHeight);
        ScreenVec anchorOffset = anchorAlignment.getOffset(containerWidth, containerHeight);
        ScreenVec drawOffset = drawAlignment.getOffset(width, height);

        float trueX = x + leftMargin + anchorOffset.x + offsetX - drawOffset.x;
        float trueY = y + topMargin + anchorOffset.y + offsetY - drawOffset.y;

        float actualWidth = width - leftMargin - rightMargin;
        float actualHeight = height - topMargin - bottomMargin;

        if (child != null) {
            child.preDraw(mouseX, mouseY, deltaTime, gui, window, trueX, trueY, actualWidth, actualHeight);
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        super.draw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);
        float actualWidth = width - leftMargin - rightMargin;
        float actualHeight = height - topMargin - bottomMargin;

        float actualX = x + leftMargin + offsetX;
        float actualY = y + topMargin + offsetY;

        if (child != null) {
            child.draw(mouseX, mouseY, deltaTime, gui, actualX, actualY, actualWidth, actualHeight);
        }
    }
}
