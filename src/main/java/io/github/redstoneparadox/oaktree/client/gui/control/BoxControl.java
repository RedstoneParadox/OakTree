package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

/**
 * BoxControl is a type of PaddingControl that
 * can have a single child node.
 *
 * While {@link PanelControl} could also be used,
 * BoxControl is specialized for dealing with a
 * single child.
 */
public class BoxControl extends PaddingControl<BoxControl> {
    public Control child = null;

    public BoxControl() {
        this.id = "box";
    }

    /**
     * Sets the child node of this node. The child node
     * will be drawn relative to this node within the box
     * created by the internal margins.
     *
     * @param child The node that is being added as a child.
     * @return The node itself.
     */
    public BoxControl child(Control child) {
        this.child = child;
        return this;
    }

    @Override
    public void setup(MinecraftClient client, ControlGui gui) {
        super.setup(client, gui);
        if (child != null) child.setup(client, gui);
    }

    @Override
    public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, List<Control<?>> controlList, int mouseX, int mouseY) {
        if (!visible) return;
        super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, controlList, mouseX, mouseY);
        ScreenVec innerPosition = innerPosition(trueX, trueY);
        ScreenVec innerDimensions = innerDimensions(area.width, area.height);

        if (child != null) {
            child.preDraw(gui, innerPosition.x, innerPosition.y, innerDimensions.x, innerDimensions.y, controlList, mouseX, mouseY);
        }
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        if (!visible) return;
        super.draw(matrices, mouseX, mouseY, deltaTime, gui);

        if (child != null) {
            child.draw(matrices, mouseX, mouseY, deltaTime, gui);
        }
    }
}
