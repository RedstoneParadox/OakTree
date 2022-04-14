package io.github.redstoneparadox.oaktree.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

import java.util.List;

public final class SynchronizedInventory implements Inventory {
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
}
