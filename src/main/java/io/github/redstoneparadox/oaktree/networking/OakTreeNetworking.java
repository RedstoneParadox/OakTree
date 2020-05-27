package io.github.redstoneparadox.oaktree.networking;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import io.github.redstoneparadox.oaktree.mixin.container.SlotAccessor;

import java.util.HashMap;
import java.util.Map;

public class OakTreeNetworking {

    private static final Identifier SYNC_SLOT = new Identifier("oaktree", "sync_slot");
    private static final Map<Integer, ScreenHandler> CONTAINER_MAP = new HashMap<>();

    public static void initPackets() {
        ServerSidePacketRegistry.INSTANCE.register(SYNC_SLOT, ((context, buffer) -> {
            int[] data = buffer.readIntArray();

            int x = data[0];
            int y = data[1];
            int index = data[2];
            int syncID = data[3];

            syncSlot(x, y, index, syncID);
        }));
    }

    public static void addContainerForSyncing(ScreenHandler screenHandler) {
        CONTAINER_MAP.put(screenHandler.syncId, screenHandler);
    }

    private static void syncSlot(int x, int y, int index, int syncID) {
        if (CONTAINER_MAP.containsKey(syncID)) {
            ScreenHandler screenHandler = CONTAINER_MAP.get(syncID);
            if (index < screenHandler.slots.size()) {
                Slot slot = screenHandler.slots.get(index);
                ((SlotAccessor) slot).setX(x);
                ((SlotAccessor) slot).setY(y);
            }
        }
        else {
            System.out.println("Container with syncID `" + syncID + "` is not listening for updates.");
        }
    }
}
