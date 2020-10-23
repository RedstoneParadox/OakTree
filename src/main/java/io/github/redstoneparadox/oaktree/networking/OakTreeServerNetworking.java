package io.github.redstoneparadox.oaktree.networking;

import io.github.redstoneparadox.oaktree.util.InventoryScreenHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class OakTreeServerNetworking {
	public static final Identifier SYNC_STACK = new Identifier("oaktree", "sync_stack");
	private static final Map<Integer, InventoryScreenHandler> LISTENER_MAP = new HashMap<>();

	public static void initPackets() {
		ServerSidePacketRegistry.INSTANCE.register(SYNC_STACK, (context, buffer) -> {
			int[] data = buffer.readIntArray();

			int slot = data[0];
			int inventoryID = data[1];
			int syncID = data[2];

			ItemStack stack = buffer.readItemStack();

			context.getTaskQueue().execute(() -> syncStack(slot, inventoryID, syncID, stack));
		});
	}

	public static void listenForStackSync(InventoryScreenHandler handler) {
		LISTENER_MAP.put(handler.getSyncID(), handler);
	}

	public static void stopListening(InventoryScreenHandler handler) {
		LISTENER_MAP.remove(handler.getSyncID());
	}

	private static void syncStack(int slot, int inventoryID, int syncID, ItemStack stack) {
		if (LISTENER_MAP.containsKey(syncID)) {
			InventoryScreenHandler handler = LISTENER_MAP.get(syncID);
			Inventory inventory = handler.getInventory(inventoryID);
			inventory.setStack(slot, stack);
			System.out.println(inventory.getStack(slot));
		}
		else {
			System.out.println("InventoryScreenHandler with syncID `" + syncID + "` is not listening for updates.");
		}
	}
}
