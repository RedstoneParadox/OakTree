package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.control.Anchor;
import io.github.redstoneparadox.oaktree.control.RootPanelControl;
import io.github.redstoneparadox.oaktree.networking.InventoryScreenHandlerAccess;
import io.github.redstoneparadox.oaktree.networking.OakTreeServerNetworking;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestScreens {
	public static RootPanelControl testOne() {
		RootPanelControl root = new RootPanelControl();

		root.setAnchor(Anchor.CENTER);
		root.setSize(50, 50);
		root.setId("base");

		return root;
	}

	private static List<Text> createText(int lines) {
		List<Text> texts = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < lines; i += 1) {
			LiteralText text = new LiteralText("Text.");
			int j = random.nextInt(3);
			Text formatted = switch (j) {
				case 0 -> text.formatted(Formatting.BOLD);
				case 1 -> text.formatted(Formatting.ITALIC);
				case 2 -> text.formatted(Formatting.UNDERLINE);
				default -> throw new IllegalStateException("Unexpected value: " + j);
			};

			texts.add(formatted);
		}

		return texts;
	}

	static class TestScreen extends Screen {
		private final RootPanelControl root;

		protected TestScreen(Text title, RootPanelControl root) {
			super(title);
			this.root = root;
		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			super.render(matrices, mouseX, mouseY, delta);
			root.render(matrices, mouseX, mouseY, delta);
		}

		@Override
		public boolean isPauseScreen() {
			return false;
		}
	}

	static class HandledTestScreen extends HandledScreen<TestScreenHandler> {
		private final RootPanelControl root;

		public HandledTestScreen(TestScreenHandler handler, Text title, RootPanelControl root) {
			super(handler, handler.getPlayer().getInventory(), title);
			this.root = root;
		}

		@Override
		protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			super.render(matrices, mouseX, mouseY, delta);
			root.render(matrices, mouseX, mouseY, delta);
		}

		@Override
		public boolean isPauseScreen() {
			return super.isPauseScreen();
		}

		@Override
		public void onClose() {
			super.onClose();
			handler.close(handler.getPlayer());
		}
	}

	static class TestScreenHandler extends ScreenHandler implements InventoryScreenHandlerAccess {
		private final PlayerEntity player;
		private final List<Inventory> inventories = new ArrayList<>();

		protected TestScreenHandler(int syncId, PlayerEntity player) {
			super(null, syncId);
			this.player = player;
			inventories.add(player.getInventory());

			if (!player.world.isClient) OakTreeServerNetworking.listenForStackSync(this);
			inventories.add(new SimpleInventory(ItemStack.EMPTY, ItemStack.EMPTY));
		}

		@Override
		public boolean canUse(PlayerEntity player) {
			return true;
		}

		@NotNull
		@Override
		public Inventory getInventory(int inventoryID) {
			return inventories.get(inventoryID);
		}

		@Override
		public @NotNull PlayerEntity getPlayer() {
			return player;
		}

		@Override
		public int getSyncID() {
			return syncId;
		}

		@Override
		public void close(PlayerEntity player) {
			super.close(player);
			OakTreeServerNetworking.stopListening(this);
		}
	}
}
