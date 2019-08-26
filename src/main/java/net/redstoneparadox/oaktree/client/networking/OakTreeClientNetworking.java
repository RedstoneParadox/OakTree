package net.redstoneparadox.oaktree.client.networking;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class OakTreeClientNetworking {

    private static final Identifier SYNC_SLOT = new Identifier("oak_tree", "sync_slot");

    public static void syncSlot(int x, int y, int index, int syncID) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        buf.writeIntArray(new int[]{x, y, index, syncID});
        ClientSidePacketRegistry.INSTANCE.sendToServer(SYNC_SLOT, buf);
    }
}
