package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.util.RenderHelper;
import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.networking.OakTreeClientNetworking;
import io.github.redstoneparadox.oaktree.networking.InventoryScreenHandlerAccess;
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
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.BiPredicate;

/**
 * <p>A {@link Control} that can be used for interacting
 * with items in an inventory. Rather than delegating to
 * a vanilla {@link net.minecraft.screen.slot.Slot}, it
 * is implemented from scratch and requires your
 * {@link net.minecraft.screen.ScreenHandler} to
 * implement {@link InventoryScreenHandlerAccess} in order to
 * work. Syncing inventory contents is also handled
 * through the same interface so avoid implementing that
 * yourself.</p>
 */
public class SlotControl extends InteractiveControl<SlotControl> implements MouseButtonListener {
	protected @NotNull Color highlightColor = Color.rgba(0.75f, 0.75f, 0.75f, 0.5f);
	protected int slotBorder = 1;
	protected @NotNull TriPredicate<ControlGui, SlotControl, ItemStack> canInsert = (gui, control, stack) -> true;
	protected @NotNull TriPredicate<ControlGui, SlotControl, ItemStack> canTake = (gui, control, stack) -> true;
	protected boolean locked = false;

	private final int slot;
	private final int inventoryID;
	private boolean leftClicked = false;
	private boolean rightClicked = false;

	/**
	 *
	 * @param slot The index of the "slot" in your
	 *             {@link Inventory} implementation.
	 * @param inventoryID The integer ID of the inventory;
	 *                    See {@link InventoryScreenHandlerAccess}
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
	 */
	public void setHighlightColor(@NotNull Color highlightColor) {
		this.highlightColor = highlightColor;
	}

	public @NotNull Color getHighlightColor() {
		return highlightColor;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SlotControl highlightColor(@NotNull Color highlightColor) {
		this.highlightColor = highlightColor;
		return this;
	}

	public void setSlotBorder(int slotBorder) {
		this.slotBorder = slotBorder;
	}

	public int getSlotBorder() {
		return slotBorder;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SlotControl slotBorder(int slotBorder) {
		this.slotBorder = slotBorder;
		return this;
	}

	public void canInsert(@NotNull BiPredicate<ControlGui, ItemStack> canInsert) {
		this.canInsert = ((controlGui, slotControl, stack) -> canInsert.test(controlGui, stack));
	}

	/**
	 * Helper method for setting a list of {@link Item }
	 * to match against when inserting into slot.
	 *
	 * @param allow Whether matching an item in the list
	 *              should allow or deny inserting a stack.
	 * @param items The items to allow/deny.
	 * @return The {@link SlotControl} for further modification.
	 */
	public SlotControl filter(boolean allow, Item... items) {
		this.canInsert = ((gui, control, stack) -> {
			for (Item item: items) {
				if (stack.getItem() == item) return allow;
			}
			return !allow;
		});
		return this;
	}

	/**
	 * Helper method for setting a list of {@link Tag<Item>}
	 * to match against when inserting into slot.
	 *
	 * @param allow Whether matching an item tag in the list
	 *              should allow or deny inserting a stack.
	 * @param tags The item tags to match against.
	 * @return The {@link SlotControl} for further modification.
	 */
	public SlotControl filter(boolean allow, Tag<Item>... tags) {
		this.canInsert = ((gui, control, stack) -> {
			for (Tag<Item> tag: tags) {
				if (tag.contains(stack.getItem())) return allow;
			}
			return !allow;
		});
		return this;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SlotControl canInsert(@NotNull TriPredicate<ControlGui, SlotControl, ItemStack> canInsert) {
		this.canInsert = canInsert;
		return this;
	}

	public void canTake(@NotNull BiPredicate<ControlGui, ItemStack> canTake) {
		this.canTake = ((controlGui, slotControl, stack) -> canTake.test(controlGui, stack));
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SlotControl canTake(@NotNull TriPredicate<ControlGui, SlotControl, ItemStack> canTake) {
		this.canTake = canTake;
		return this;
	}

	/**
	 * Used to disable or enable interactions with
	 * this {@link SlotControl}.
	 *
	 * @param locked Whether or not the slot should be locked.
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}

	@ApiStatus.ScheduledForRemoval
	@Deprecated
	public SlotControl locked(boolean locked) {
		this.locked = locked;
		return this;
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		ClientListeners.MOUSE_BUTTON_LISTENERS.add(this);
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		gui.getScreenHandler().ifPresent(handler -> {
			if (handler instanceof InventoryScreenHandlerAccess) {
				super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

				PlayerEntity player = ((InventoryScreenHandlerAccess) handler).getPlayer();
				PlayerInventory playerInventory = player.inventory;
				Inventory inventory = ((InventoryScreenHandlerAccess) handler).getInventory(inventoryID);

				if (isMouseWithin) {
					boolean stackChanged = false;
					ItemStack stackInSlot = inventory.getStack(slot);

					if (playerInventory.getCursorStack().isEmpty()) {
						if (!locked && canTake.test(gui, this, stackInSlot)) {
							if (leftClicked) {
								playerInventory.setCursorStack(inventory.removeStack(slot));
								stackChanged = true;
							}
							else if (rightClicked) {
								playerInventory.setCursorStack(inventory.removeStack(slot, stackInSlot.getCount()/2));
								stackChanged = true;
							}
						}
					}
					else {
						ItemStack cursorStack = playerInventory.getCursorStack();

						if (!locked && canInsert.test(gui, this, cursorStack)) {
							if (leftClicked) {
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
							else if (rightClicked) {
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
			if (screenHandler instanceof InventoryScreenHandlerAccess) {
				super.draw(matrices, mouseX, mouseY, deltaTime, gui);
				ItemStack stack = ((InventoryScreenHandlerAccess) screenHandler).getInventory(inventoryID).getStack(slot);
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

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			leftClicked = justPressed;
		}
		else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			rightClicked = justPressed;
		}
	}
}
