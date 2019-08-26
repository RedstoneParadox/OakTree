package net.redstoneparadox.oaktree.client.gui.nodes;

import net.minecraft.container.Slot;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.networking.OakTreeClientNetworking;

/**
 * A Node that acts as a special wrapper for setting
 * the position of an item slot.
 */
public class ItemSlotNode extends Node<ItemSlotNode> {

    private final int index;

    public ItemSlotNode(int index) {
        this.index = index;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        super.draw(mouseX, mouseY, deltaTime, gui);

        gui.getScreenContainer().ifPresent(container -> {
            if (index < container.slotList.size()) {
                Slot slot = container.slotList.get(index);
                slot.xPosition = (int)trueX;
                slot.yPosition = (int)trueY;

                OakTreeClientNetworking.syncSlot((int)trueX, (int)trueY, index, container.syncId);
            }
        });
    }
}
