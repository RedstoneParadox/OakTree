package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.container.Slot;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.networking.OakTreeClientNetworking;

/**
 * A Node that acts as a special wrapper for setting
 * the position of an item slot.
 *
 * If attached to a {@link Screen} that does not
 * have a container, it will not be drawn.
 */
public class ItemSlotNode extends Node<ItemSlotNode> {

    private final int index;

    /**
     * @param index The index of the slot in your
     *              container's slot list, not the
     *              index value of the slot.
     */
    public ItemSlotNode(int index) {
        this.index = index;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        gui.getScreenContainer().ifPresent(container -> {
            super.draw(mouseX, mouseY, deltaTime, gui);
            if (index < container.slotList.size()) {
                Slot slot = container.slotList.get(index);
                slot.xPosition = (int)trueX;
                slot.yPosition = (int)trueY;

                OakTreeClientNetworking.syncSlot((int)trueX, (int)trueY, index, container.syncId);
            }
        });
    }
}
