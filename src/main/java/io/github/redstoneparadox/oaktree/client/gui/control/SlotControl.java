package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.ColorStyleBox;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import io.github.redstoneparadox.oaktree.util.InventoryScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class SlotControl extends InteractiveControl<SlotControl> {
    public StyleBox highlightStyle = new ColorStyleBox(new RGBAColor(0.75f, 0.75f, 0.75f, 0.5f));
    public int slotBorder = 1;

    private final int slot;
    private final int inventoryIndex;

    public SlotControl(int slot, int inventoryIndex) {
        this.slot = slot;
        this.inventoryIndex = inventoryIndex;
        this.id = "item_slot";
        this.size(18, 18);
    }

    @Override
    public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
        gui.getScreenHandler().ifPresent(handler -> {
            if (handler instanceof InventoryScreenHandler) {
                super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

                PlayerEntity player = ((InventoryScreenHandler) handler).getPlayer();
                PlayerInventory playerInventory = player.inventory;
                Inventory inventory = ((InventoryScreenHandler) handler).getInventory(inventoryIndex);

                if (isMouseWithin) {
                    if (playerInventory.getCursorStack().isEmpty()) {
                        if (gui.mouseButtonJustClicked("left")) {
                            playerInventory.setCursorStack(inventory.removeStack(slot));
                        }
                        else if (gui.mouseButtonJustClicked("right")) {
                            ItemStack stack = inventory.getStack(slot);
                            playerInventory.setCursorStack(inventory.removeStack(slot, stack.getCount()/2));
                        }
                    }
                    else {
                        ItemStack stackInSlot = inventory.getStack(slot);

                        if (gui.mouseButtonJustClicked("left")) {
                            if (stackInSlot.isEmpty()) {
                                inventory.setStack(slot, playerInventory.getCursorStack());
                                playerInventory.setCursorStack(ItemStack.EMPTY);
                            }
                            else {
                                ItemStack cursorStack = playerInventory.getCursorStack();
                                combineStacks(cursorStack, stackInSlot, cursorStack.getCount());
                            }
                        }
                        else if (gui.mouseButtonJustClicked("right")) {
                            if (stackInSlot.isEmpty()) {
                                inventory.setStack(slot, playerInventory.getCursorStack().split(1));
                            }
                            else {
                                ItemStack cursorStack = playerInventory.getCursorStack();
                                combineStacks(cursorStack, stackInSlot, 1);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        gui.getScreenHandler().ifPresent(screenHandler -> {
            if (screenHandler instanceof InventoryScreenHandler) {
                super.draw(matrices, mouseX, mouseY, deltaTime, gui);
                ItemStack stack = ((InventoryScreenHandler) screenHandler).getInventory(inventoryIndex).getStack(slot);
                ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();

                int itemX = (area.width - 16)/2 + trueX;
                int itemY = (area.height - 16)/2 + trueY;

                renderer.renderGuiItemIcon(stack, itemX, itemY);
                TextRenderer textRenderer = gui.getTextRenderer();
                renderer.renderGuiItemOverlay(textRenderer, stack, itemX, itemY);

                if (isMouseWithin) {
                    highlightStyle.draw(trueX + slotBorder, trueY + slotBorder, area.width - (2 * slotBorder), area.height - (2 * slotBorder), gui);
                }
            }
        });
    }

    private void combineStacks(ItemStack from, ItemStack to, int amount) {
        if (from.getCount() < amount) {
            combineStacks(from, to, from.getCount());
        }
        else if (to.getMaxCount() - to.getCount() < amount) {
            combineStacks(from, to, to.getMaxCount() - to.getCount());
        }
        else if (ItemStack.areItemsEqual(from, to)) {
            from.decrement(amount);
            to.increment(amount);
        }
    }
}
