package redstoneparadox.oaktree.client.gui.control;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.container.Slot;
import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.networking.OakTreeClientNetworking;
import redstoneparadox.oaktree.mixin.container.SlotAccessor;

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
                ((SlotAccessor) slot).setXPosition(slotX);
                ((SlotAccessor) slot).setYPosition(slotY);

                OakTreeClientNetworking.syncSlot(slotX, slotY, index, container.syncId);
            }
        });
    }
}
