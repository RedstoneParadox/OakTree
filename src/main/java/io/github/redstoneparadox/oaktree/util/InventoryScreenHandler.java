package io.github.redstoneparadox.oaktree.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface InventoryScreenHandler {
   @Nullable Inventory getInventory(int inventoryID);

   @NotNull PlayerEntity getPlayer();

   int getSyncID();
}
