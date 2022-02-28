package io.github.redstoneparadox.oaktree.networking;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class SynchronizedInventory implements Inventory {
	private final Inventory synced;

	private SynchronizedInventory(Inventory synced) {
		this.synced = synced;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStack(int i) {
		return null;
	}

	@Override
	public ItemStack removeStack(int i, int j) {
		return null;
	}

	@Override
	public ItemStack removeStack(int i) {
		return null;
	}

	@Override
	public void setStack(int i, ItemStack itemStack) {

	}

	@Override
	public void markDirty() {

	}

	@Override
	public boolean canPlayerUse(PlayerEntity playerEntity) {
		return false;
	}

	@Override
	public void clear() {

	}
}
