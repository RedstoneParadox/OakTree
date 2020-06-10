package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.RenderHelper;
import io.github.redstoneparadox.oaktree.client.gui.Color;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.networking.OakTreeClientNetworking;
import io.github.redstoneparadox.oaktree.util.InventoryScreenHandler;
import io.github.redstoneparadox.oaktree.util.TriPredicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * <p>A {@link Control} that can be used for interacting
 * with items in an inventory. Rather than delegating to
 * a vanilla {@link net.minecraft.screen.slot.Slot}, it
 * is implemented from scratch and requires your
 * {@link net.minecraft.screen.ScreenHandler} to
 * implement {@link InventoryScreenHandler} in order to
 * work. Syncing inventory contents is also handled
 * through the same interface so avoid implementing that
 * yourself.</p>
 */
public class SlotControl extends InteractiveControl<SlotControl> {
	protected @NotNull Color highlightColor = Color.rgba(0.75f, 0.75f, 0.75f, 0.5f);
	protected int slotBorder = 1;
	protected @NotNull TriPredicate<ControlGui, SlotControl, ItemStack> canInsert = (gui, control, stack) -> true;
	protected @NotNull TriPredicate<ControlGui, SlotControl, ItemStack> canTake = (gui, control, stack) -> true;

	private final int slot;
	private final int inventoryID;

	/**
	 *
	 * @param slot The index of the "slot" in your
	 *             {@link Inventory} implementation.
	 * @param inventoryID The integer ID of the inventory;
	 *                    See {@link InventoryScreenHandler}
	 *                    for more details.
	 */
	public SlotControl(int slot, int inventoryID) {
		this.slot = slot;
		this.inventoryID = inventoryID;
		this.id = "item_slot";
		this.tooltip = new LabelControl().id("tooltip").shadow(true).fitText(true);
		this.size(18, 18);
	}

	/**
	 * Sets what color should be used to highlight this {@link SlotControl}
	 * when hovering over it in the GUI. Default value is
	 * {@code Color.rgba(0.75f, 0.75f, 0.75f, 0.5f)}.
	 *
	 * @param highlightColor The color to be used.
	 * @return This {@link Control} for further modification.
	 */
	public SlotControl highlightColor(@NotNull Color highlightColor) {
		this.highlightColor = highlightColor;
		return this;
	}

	public SlotControl slotBorder(int slotBorder) {
		this.slotBorder = slotBorder;
		return this;
	}

	public int getSlotBorder() {
		return slotBorder;
	}

	public SlotControl canInsert(@NotNull TriPredicate<ControlGui, SlotControl, ItemStack> canInsert) {
		this.canInsert = canInsert;
		return this;
	}

	public SlotControl filter(Item... items) {
		this.canInsert = ((gui, control, stack) -> {
			for (Item item: items) {
				if (stack.getItem() != item) return false;
			}
			return true;
		});
		return this;
	}

	public SlotControl filter(Tag<Item>... tags) {
		this.canInsert = ((gui, control, stack) -> {
			for (Tag<Item> tag: tags) {
				if (!tag.contains(stack.getItem())) return false;
			}
			return true;
		});
		return this;
	}

	public SlotControl canTake(@NotNull TriPredicate<ControlGui, SlotControl, ItemStack> canTake) {
		this.canTake = canTake;
		return this;
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		if (tooltip != null) tooltip.setup(client, gui);
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		gui.getScreenHandler().ifPresent(handler -> {
			if (handler instanceof InventoryScreenHandler) {
				super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

				PlayerEntity player = ((InventoryScreenHandler) handler).getPlayer();
				PlayerInventory playerInventory = player.inventory;
				Inventory inventory = ((InventoryScreenHandler) handler).getInventory(inventoryID);

				if (isMouseWithin && inventory != null) {
					boolean stackChanged = false;
					ItemStack stackInSlot = inventory.getStack(slot);

					if (playerInventory.getCursorStack().isEmpty()) {
						if (canTake.test(gui, this, stackInSlot)) {
							if (leftMouseClicked) {
								playerInventory.setCursorStack(inventory.removeStack(slot));
								stackChanged = true;
							}
							else if (rightMouseClicked) {
								playerInventory.setCursorStack(inventory.removeStack(slot, stackInSlot.getCount()/2));
								stackChanged = true;
							}
						}
					}
					else {
						ItemStack cursorStack = playerInventory.getCursorStack();

						if (canInsert.test(gui, this, cursorStack)) {
							if (leftMouseClicked) {
								if (stackInSlot.isEmpty()) {
									inventory.setStack(slot, playerInventory.getCursorStack());
									playerInventory.setCursorStack(ItemStack.EMPTY);
									stackChanged = true;
								}
								else {
									combineStacks(cursorStack, stackInSlot, cursorStack.getCount());
									stackChanged = true;
								}
							}
							else if (rightMouseClicked) {
								if (stackInSlot.isEmpty()) {
									inventory.setStack(slot, playerInventory.getCursorStack().split(1));
									stackChanged = true;
								}
								else {
									combineStacks(cursorStack, stackInSlot, 1);
									stackChanged = true;
								}
							}
						}

					}

					stackInSlot = inventory.getStack(slot);

					if (stackChanged) {
						OakTreeClientNetworking.syncStack(slot, inventoryID, handler.syncId, inventory.getStack(slot));
					}
					if (tooltip instanceof LabelControl) {
						if (!stackInSlot.isEmpty()) {
							List<Text> texts = stackInSlot.getTooltip(player, TooltipContext.Default.NORMAL);
							((LabelControl) tooltip).text(texts);
						}
						else {
							((LabelControl) tooltip).clear();
							tooltip.visible(false);
						}
					}

					if (stackInSlot.isEmpty() && tooltip != null) tooltip.visible = false;
				}
			}
		});
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		gui.getScreenHandler().ifPresent(screenHandler -> {
			if (screenHandler instanceof InventoryScreenHandler) {
				super.draw(matrices, mouseX, mouseY, deltaTime, gui);
				ItemStack stack = ((InventoryScreenHandler) screenHandler).getInventory(inventoryID).getStack(slot);
				RenderHelper.drawItemStackCentered(trueX, trueY, area.width, area.height, stack);

				if (isMouseWithin) {
					RenderHelper.setzOffset(200.0);
					RenderHelper.drawRectangle(trueX + slotBorder, trueY + slotBorder, area.width - (2 * slotBorder), area.height - (2 * slotBorder), highlightColor);
					RenderHelper.setzOffset(0.0);
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
