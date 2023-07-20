package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.util.BackingSlot;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.feature_flags.FeatureFlags;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class TestScreenHandlers {
	public static final ScreenHandlerType<TestScreenHandler> TEST_INVENTORY_SCREEN_HANDLER;

	static {
		ScreenHandlerType<TestScreenHandler> type = new ScreenHandlerType<>(TestScreenHandler::new, FeatureFlags.DEFAULT_SET);
		TEST_INVENTORY_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER_TYPE, new Identifier(""), type);
	}

	public static void init() {

	}

	static class TestScreenHandler extends ScreenHandler {
		public final PlayerInventory playerInventory;
		private final List<BackingSlot> backingSlots = new ArrayList<>();

		protected TestScreenHandler(int syncId, PlayerInventory playerInventory) {
			super(TEST_INVENTORY_SCREEN_HANDLER, syncId);
			this.playerInventory = playerInventory;

			for (int i = 0; i < 36; i++) addSlot(new BackingSlot(playerInventory, i));
		}

		@Override
		protected Slot addSlot(Slot slot) {
			if (slot instanceof BackingSlot) backingSlots.add((BackingSlot) slot);
			return super.addSlot(slot);
		}

		@Override
		public ItemStack quickTransfer(PlayerEntity player, int fromIndex) {
			return null;
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
