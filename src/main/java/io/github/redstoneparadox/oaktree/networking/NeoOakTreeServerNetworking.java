package io.github.redstoneparadox.oaktree.networking;

import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

public class NeoOakTreeServerNetworking {
	private static final Int2ObjectArrayMap<List<SynchronizedInventory>> INVENTORIES = new Int2ObjectArrayMap<>();

	public static void initPackets() {
		ServerPlayNetworking.registerGlobalReceiver(PacketIdentifiers.TRANSFER_STACK, ((server, player, handler, buf, responseSender) -> {
			int syncID = buf.readInt();
			int inventoryID = buf.readInt();
			int slot = buf.readInt();
			int count = buf.readInt();

			var list = INVENTORIES.get(syncID);
			var inventory = list.get(inventoryID);
			inventory.transferStack(slot, count);
		}));
	}

	public static void updateSlots(ServerPlayerEntity player, int[] slots, ItemStack[] stacks) {
		PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
		buffer.writeIntArray(slots);
		buffer.writeInt(stacks.length);

		for (ItemStack stack : stacks) buffer.writeItemStack(stack);

		ServerPlayNetworking.send(player, PacketIdentifiers.UPDATE_STACKS, buffer);
	}

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
