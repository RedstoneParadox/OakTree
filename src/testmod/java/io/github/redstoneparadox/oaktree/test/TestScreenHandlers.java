package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.networking.InventoryScreenHandlerAccess;
import io.github.redstoneparadox.oaktree.networking.OakTreeServerNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
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

		protected TestScreenHandler(int syncId, PlayerInventory playerInventory) {
			super(TEST_INVENTORY_SCREEN_HANDLER, syncId);
			this.playerInventory = playerInventory;
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
