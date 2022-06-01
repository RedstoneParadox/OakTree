package io.github.redstoneparadox.oaktree.networking;

import io.github.redstoneparadox.oaktree.OakTree;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class PacketIdentifiers {
	public static final Identifier UPDATE_STACKS = id("update_stacks");
	public static final Identifier PICKUP_STACK = id("pickup_stack");
	public static final Identifier PLACE_STACK = id("place_stack");
	public static final Identifier MOVE_TO_HOTBAR = id("move_to_hotbar"); // used for offhand
	public static final Identifier SWITCH_INVENTORIES = id("switch_inventories");
	public static final Identifier PICK_ITEM = id("pick_item"); // creative mode

	static Identifier id(String name) {
		return new Identifier(OakTree.MODID, name);
	}
}
