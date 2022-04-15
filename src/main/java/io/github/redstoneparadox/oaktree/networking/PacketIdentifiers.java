package io.github.redstoneparadox.oaktree.networking;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class PacketIdentifiers {
	public static final Identifier RECEIVE_TRANSFER_RESULT = new Identifier("oaktree", "sync_stack");
	public static final Identifier UPDATE_STACKS = new Identifier("oaktree", "update_stacks");
	public static final Identifier TRANSFER_STACK = new Identifier("oaktree","transfer_stack");
}
