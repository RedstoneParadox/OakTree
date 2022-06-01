package io.github.redstoneparadox.oaktree.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

public final class SynchronizedInventory implements Inventory {
	public boolean active = true;
	private final int syncID;
	private final int inventoryID;
	private final Inventory synced;
	private final PlayerEntity player;

	private SynchronizedInventory(int syncID, int inventoryID, Inventory synced, PlayerEntity player) {
		this.syncID = syncID;
		this.inventoryID = inventoryID;
		this.synced = synced;
		this.player = player;
	}

	/**
	 * Creates a new {@link SynchronizedInventory}
	 *
	 * @param syncID The sync id of your {@link ScreenHandler}
	 * @param synced The {@link Inventory} to sync
	 * @param player The player using this inventory
	 * @return A new {@link SynchronizedInventory}
	 */
	public static SynchronizedInventory create(int syncID, Inventory synced, PlayerEntity player) {
		SynchronizedInventory inventory;

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			int inventoryID = NeoOakTreeClientNetworking.getNextInventoryID(syncID);
			inventory = new SynchronizedInventory(syncID, inventoryID, synced, player);

			NeoOakTreeClientNetworking.addInventory(syncID, inventory);
		} else {
			int inventoryID = NeoOakTreeServerNetworking.getNextInventoryID(syncID);
			inventory = new SynchronizedInventory(syncID, inventoryID, synced, player);

			NeoOakTreeServerNetworking.addInventory(syncID, inventory);
		}

		return inventory;
	}

	@Override
	public int size() {
		return synced.size();
	}

	@Override
	public boolean isEmpty() {
		return synced.isEmpty();
	}

	@Override
	public ItemStack getStack(int i) {
		return synced.getStack(i);
	}

	@Override
	public ItemStack removeStack(int i, int j) {
		return synced.removeStack(i, j);
	}

	@Override
	public ItemStack removeStack(int i) {
		return synced.removeStack(i);
	}

	@Override
	public void setStack(int i, ItemStack itemStack) {
		if (i == -2) {
			player.currentScreenHandler.setCursorStack(itemStack);
		}

		synced.setStack(i, itemStack);
	}

	@Override
	public void markDirty() {
		synced.markDirty();
	}

	@Override
	public boolean canPlayerUse(PlayerEntity playerEntity) {
		return true;
	}

	@Override
	public void clear() {
		synced.clear();
	}

	@Override
	public void onClose(PlayerEntity playerEntity) {
		Inventory.super.onClose(playerEntity);
		synced.onClose(playerEntity);

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			NeoOakTreeClientNetworking.removeInventories(syncID);
		} else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
			NeoOakTreeServerNetworking.removeInventories(syncID);
		}
	}

	@Environment(EnvType.CLIENT)
	public void setActive(boolean active) {
		this.active = active;
	}

	@ApiStatus.Internal
	@Environment(EnvType.CLIENT)
	public void grabStack(int slot, int count) {
		NeoOakTreeClientNetworking.grabStack(syncID, inventoryID, slot, count);
	}

	@ApiStatus.Internal
	@Environment(EnvType.SERVER)
	public void transferStack(int slot, int count) {
		ScreenHandler handler = player.currentScreenHandler;
		ItemStack cursorStack = handler.getCursorStack();
		ItemStack slotStack = synced.getStack(slot);

		if (cursorStack.isEmpty() && !slotStack.isEmpty()) {
			handler.setCursorStack(synced.removeStack(slot, count));
		} else if (!cursorStack.isEmpty() && slotStack.isEmpty()) {
			synced.setStack(slot, cursorStack.split(count));
		} else if (!cursorStack.isEmpty() && slotStack.getItem() == cursorStack.getItem()) {
			if (cursorStack.getCount() + slotStack.getCount() < 64) {
				slotStack.setCount(cursorStack.getCount() + slotStack.getCount());
				handler.setCursorStack(ItemStack.EMPTY);
			} else if (slotStack.getCount() < 64){
				int remaining = 64 - slotStack.getCount();
				cursorStack.decrement(remaining);
				slotStack.setCount(64);
			}
		}

		int[] slots = new int[] { -2, slot };
		ItemStack[] stacks = new ItemStack[] { handler.getCursorStack(), synced.getStack(slot) };
		NeoOakTreeServerNetworking.updateSlots((ServerPlayerEntity) player, syncID, inventoryID, slots, stacks);
	}
}
