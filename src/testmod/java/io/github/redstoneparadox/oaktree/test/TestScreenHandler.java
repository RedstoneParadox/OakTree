package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.networking.InventoryScreenHandlerAccess;
import io.github.redstoneparadox.oaktree.networking.OakTreeServerNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class TestScreenHandler extends ScreenHandler implements InventoryScreenHandlerAccess {
	private final PlayerEntity player;
	private final List<Inventory> inventories = new ArrayList<>();

	protected TestScreenHandler(int syncId, PlayerEntity player) {
		super(Tests.handlerType, syncId);
		this.player = player;
		inventories.add(player.getInventory());

		if (!player.world.isClient) OakTreeServerNetworking.listenForStackSync(this);
		inventories.add(new SimpleInventory(ItemStack.EMPTY, ItemStack.EMPTY));
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return true;
	}

	@NotNull
	@Override
	public Inventory getInventory(int inventoryID) {
		return inventories.get(inventoryID);
	}

	@Override
	public @NotNull PlayerEntity getPlayer() {
		return player;
	}

	@Override
	public int getSyncID() {
		return syncId;
	}

	@Override
	public void close(PlayerEntity player) {
		super.close(player);
		OakTreeServerNetworking.stopListening(this);
	}
}
