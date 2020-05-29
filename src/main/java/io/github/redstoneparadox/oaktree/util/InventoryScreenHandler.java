package io.github.redstoneparadox.oaktree.util;

import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface InventoryScreenHandler {
    void pickupStack(int index, int inventory, boolean full);

    void placeStack(int index, int inventory, boolean full);

    @NotNull ItemStack getStack(int index, int inventory);

    boolean isCursorEmpty();
}
