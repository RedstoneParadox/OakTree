package io.github.redstoneparadox.oaktree.networking;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class OakTreeServerNetworking {
	private static final Identifier SYNC_STACK = new Identifier("oaktree", "sync_stack");
	private static final Map<Integer, InventoryScreenHandlerAccess> ACCESS_MAP = new HashMap<>();

	public static void initPackets() {
		ServerSidePacketRegistry.INSTANCE.register(SYNC_STACK, (context, buffer) -> {
			int[] data = buffer.readIntArray();

			int slot = data[0];
			int inventoryID = data[1];
			int syncID = data[2];

			ItemStack stack = buffer.readItemStack();

			context.getTaskQueue().execute(() -> onSyncStack(slot, inventoryID, syncID, stack));
		});
	}

	public static void listenForStackSync(InventoryScreenHandlerAccess handler) {
		ACCESS_MAP.put(handler.getSyncID(), handler);
	}

	public static void stopListening(InventoryScreenHandlerAccess handler) {
		ACCESS_MAP.remove(handler.getSyncID());
	}

	private static void onSyncStack(int slot, int inventoryID, int syncID, ItemStack stack) {
		if (ACCESS_MAP.containsKey(syncID)) {
			InventoryScreenHandlerAccess handler = ACCESS_MAP.get(syncID);
			Inventory inventory = handler.getInventory(inventoryID);
			inventory.setStack(slot, stack);
			System.out.println(inventory.getStack(slot));
		}
		else {
			System.out.println("InventoryScreenHandler with syncID `" + syncID + "` is not listening for updates.");
		}
	}
}
