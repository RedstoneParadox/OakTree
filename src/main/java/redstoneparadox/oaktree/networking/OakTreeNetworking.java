package redstoneparadox.oaktree.networking;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class OakTreeNetworking {

    private static final Identifier SYNC_SLOT = new Identifier("oak_tree", "sync_slot");
    private static final Map<Integer, Container> CONTAINER_MAP = new HashMap<>();

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

    public static void addContainerForSyncing(Container container) {
        CONTAINER_MAP.put(container.syncId, container);
    }

    private static void syncSlot(int x, int y, int index, int syncID) {
        if (CONTAINER_MAP.containsKey(syncID)) {
            Container container = CONTAINER_MAP.get(syncID);
            if (index < container.slotList.size()) {
                Slot slot = container.slotList.get(index);
                slot.xPosition = x;
                slot.yPosition = y;
            }
        }
        else {
            System.out.println("Container with syncID `" + syncID + "` is not listening for updates.");
        }
    }
}
