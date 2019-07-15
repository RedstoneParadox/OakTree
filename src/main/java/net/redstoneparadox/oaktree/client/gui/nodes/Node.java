package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.MinecraftClient;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.Alignment;

/**
 * The base class for all nodes.
 */
public class Node {

    public float x = 0.0f;
    public float y = 0.0f;
    public float width = 0.1f;
    public float height = 0.1f;

    Alignment drawAlignment = Alignment.TOP_LEFT;
    Alignment anchorAlignment = Alignment.TOP_LEFT;

    boolean expand = false;

    public Node setPosition(float posX, float posY) {
        x = posX;
        y = posY;
        return this;
    }

    public Node setSize(float sizeWidth, float sizeHeight) {
        width = sizeWidth;
        height = sizeHeight;
        return this;
    }

    public Node setExpand(boolean value) {
        expand = value;
        return this;
    }

    public void setup(MinecraftClient minecraftClient_1, int int_1, int int_2) {
    }

    public void preDraw(OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        if (expand) {
            x = offsetX;
            y = offsetY;
            width = containerWidth;
            height = containerHeight;
        }
    }

    public void draw(int int_1, int int_2, float float_1, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {

    }

    public Node setDrawAlignment(Alignment newAligment) {
        drawAlignment = newAligment;
        return this;
    }

    public Node setAnchorAlignment(Alignment newAligment) {
        anchorAlignment = newAligment;
        return this;
    }
}
