package io.github.redstoneparadox.oaktree.client.gui.control;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.container.Slot;
import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import io.github.redstoneparadox.oaktree.client.networking.OakTreeClientNetworking;
import io.github.redstoneparadox.oaktree.mixin.container.SlotAccessor;

/**
 * A Node that acts as a special wrapper for setting
 * the position of an item slot.
 *
 * If attached to a {@link Screen} that does not
 * have a container, it will not be drawn.
 *
 * @apiNote You probably shouldn't use this at this time.
 */
public class ItemSlotControl extends Control<ItemSlotControl> {
    private final int index;

    /**
     * @param index The index of the slot in your
     *              container's slot list, not the
     *              index value of the slot.
     */
    public ItemSlotControl(int index) {
        this.id = "item_slot";
        this.index = index;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        gui.getScreenContainer().ifPresent(container -> {
            super.draw(mouseX, mouseY, deltaTime, gui);
            int slotX = (int)trueX - gui.getX() + 1;
            int slotY = (int)trueY - gui.getY() + 1;
            if (!visible) {
                slotX = -32;
                slotY = -32;
            }

            if (index < container.slots.size()) {
                Slot slot = container.slots.get(index);
                ((SlotAccessor) slot).setXPosition(slotX);
                ((SlotAccessor) slot).setYPosition(slotY);

                OakTreeClientNetworking.syncSlot(slotX, slotY, index, container.syncId);
            }
        });
    }
}
