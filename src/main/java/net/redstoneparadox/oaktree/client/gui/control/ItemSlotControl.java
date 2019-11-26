package net.redstoneparadox.oaktree.client.gui.control;

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
public class ItemSlotControl extends Control<ItemSlotControl> {

    private final int index;

    /**
     * @param index The index of the slot in your
     *              container's slot list, not the
     *              index value of the slot.
     */
    public ItemSlotControl(int index) {
        this.index = index;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        gui.getScreenContainer().ifPresent(container -> {
            super.draw(mouseX, mouseY, deltaTime, gui);
            int slotX = (int)trueX;
            int slotY = (int)trueY;
            if (!visible) {
                slotX = -32;
                slotY = -32;
            }

            if (index < container.slotList.size()) {
                Slot slot = container.slotList.get(index);
                slot.xPosition = slotX;
                slot.yPosition = slotY;

                OakTreeClientNetworking.syncSlot((int)trueX, (int)trueY, index, container.syncId);
            }
        });
    }
}
