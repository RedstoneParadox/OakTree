package io.github.redstoneparadox.oaktree.networking;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class NeoOakTreeClientNetworking {
	private static final Int2ObjectArrayMap<List<SynchronizedInventory>> INVENTORIES = new Int2ObjectArrayMap<>();

	public static void initPackets() {
		ClientPlayNetworking.registerGlobalReceiver(PacketIdentifiers.UPDATE_STACKS, ((client, handler, buf, responseSender) -> {
			int syncID = buf.readInt();
			int inventoryID = buf.readInt();
			int[] slots = buf.readIntArray();
			int length = buf.readInt();

			var list = INVENTORIES.get(syncID);
			var inventory = list.get(inventoryID);

			for (int i = 0; i < length; i++) {
				int slot = slots[i];
				ItemStack stack = buf.readItemStack();

				inventory.setStack(slot, stack);
			}
		}));
	}

	@ApiStatus.Internal
	public static void pickupStack(int syncID, int inventoryID, int slot, int count) {
		PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());

		buffer.writeInt(syncID);
		buffer.writeInt(inventoryID);
		buffer.writeInt(slot);
		buffer.writeInt(count);

		ClientPlayNetworking.send(PacketIdentifiers.PICKUP_STACK, buffer);
	}

	@ApiStatus.Internal
	public static void placeStack(int syncID, int inventoryID, int slot, int count) {
		PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());

		buffer.writeInt(syncID);
		buffer.writeInt(inventoryID);
		buffer.writeInt(slot);
		buffer.writeInt(count);

		ClientPlayNetworking.send(PacketIdentifiers.PLACE_STACK, buffer);
	}

	@ApiStatus.Internal
	public static int getNextInventoryID(int syncID) {
		var list = get(syncID);

		return list.size();
	}

	@ApiStatus.Internal
	public static void addInventory(int syncID, SynchronizedInventory inventory) {
		var list = get(syncID);
		list.add(inventory);
	}

	@ApiStatus.Internal
	public static void removeInventories(int syncID) {
		if (INVENTORIES.containsKey(syncID)) INVENTORIES.remove(syncID);
	}

	private static List<SynchronizedInventory> get(int syncID) {
		if (!INVENTORIES.containsKey(syncID)) {
			INVENTORIES.put(syncID, new ArrayList<>());
		}

		return INVENTORIES.get(syncID);
	}
}
