package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.networking.InventoryScreenHandlerAccess;
import io.github.redstoneparadox.oaktree.networking.OakTreeServerNetworking;
import io.github.redstoneparadox.oaktree.util.BackingSlot;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TestScreenHandlers {
	public static final ScreenHandlerType<TestScreenHandler> TEST_INVENTORY_SCREEN_HANDLER;

	static {
		TEST_INVENTORY_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(new Identifier(""), (TestScreenHandler::new));
	}

	public static void init() {

	}

	static class TestScreenHandler extends ScreenHandler {
		public final PlayerInventory playerInventory;
		private final List<BackingSlot> backingSlots = new ArrayList<>();

		protected TestScreenHandler(int syncId, PlayerInventory playerInventory) {
			super(TEST_INVENTORY_SCREEN_HANDLER, syncId);
			this.playerInventory = playerInventory;

			for (int i = 0; i < 36; i++) addSlot(new BackingSlot(playerInventory, 0));
		}

		@Override
		protected Slot addSlot(Slot slot) {
			if (slot instanceof BackingSlot) backingSlots.add((BackingSlot) slot);
			return super.addSlot(slot);
		}

		public List<BackingSlot> getBackingSlots() {
			return new ArrayList<>(backingSlots);
		}

		@Override
		public boolean canUse(PlayerEntity player) {
			return true;
		}

		@Override
		public void close(PlayerEntity player) {
			super.close(player);
		}
	}
}
