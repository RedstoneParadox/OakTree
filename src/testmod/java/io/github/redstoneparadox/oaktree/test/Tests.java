package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.control.*;
import io.github.redstoneparadox.oaktree.style.Theme;
import io.github.redstoneparadox.oaktree.math.Direction2D;
import io.github.redstoneparadox.oaktree.networking.OakTreeServerNetworking;
import io.github.redstoneparadox.oaktree.util.InventoryScreenHandler;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Tests {

	public static void init() {

		Identifier testThree = new Identifier("oaktree:test_three");

		register(new TestBlock(true, Tests::testOne), "one");
		register(new TestBlock(true, Tests::testTwo), "two");
		register(new ContainerTestBlock(true, Tests::testThree, testThree), "three");
		register(new TestBlock(true, Tests::testFour), "four");
		register(new TestBlock(true, Tests::testFive), "five");


		ScreenProviderRegistry.INSTANCE.registerFactory(testThree, (screenHandler -> {
			return new HandledTestScreen((TestScreenHandler) screenHandler, new LiteralText(""), true, testThree());
		}));
		ContainerProviderRegistry.INSTANCE.registerFactory(testThree, (syncId, identifier, player, buf) -> new TestScreenHandler(syncId, player));

		/*
		ScreenProviderRegistry.INSTANCE.registerFactory(testFourID, (syncId, identifier, player, buf) -> {
			BlockPos pos = buf.readBlockPos();
			return new ScreenBuilder
					(
							testFour()
					)
					.container(new TestScreenHandler(syncId, player))
					.playerInventory(player.inventory)
					.text(new LiteralText("Test 4"))
					.buildContainerScreen();
		});

		ContainerProviderRegistry.INSTANCE.registerFactory(testFourID, (syncId, identifier, player, buf) -> {
			ScreenHandler screenHandler = new TestScreenHandler(syncId, player);
			OakTreeNetworking.addContainerForSyncing(screenHandler);
			return screenHandler;
		});
		 */


	}

	private static Block.Settings testSettings() {
		return FabricBlockSettings.of(Material.METAL);
	}

	private static void register(Block block, String suffix) {
		Registry.register(Registry.BLOCK, new Identifier("oaktree", "test_" + suffix), block);
	}

	static class TestBlock extends Block {
		private final Supplier<Control<?>> supplier;
		private final boolean vanilla;

		TestBlock(boolean vanilla, Supplier<Control<?>> supplier) {
			super(testSettings());
			this.vanilla = vanilla;
			this.supplier = supplier;
		}

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
			if (world.isClient) {
				MinecraftClient.getInstance().openScreen(new TestScreen(new LiteralText("test screen"), vanilla, supplier.get()));
			}
			return ActionResult.SUCCESS;
		}
	}

	static class ContainerTestBlock extends TestBlock {
		private final Identifier containerID;

		ContainerTestBlock(boolean vanilla, Supplier<Control<?>> supplier, Identifier containerID) {
			super(vanilla, supplier);
			this.containerID = containerID;
		}

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
			if (!world.isClient()) {
				ContainerProviderRegistry.INSTANCE.openContainer(containerID, player, (buf -> buf.writeBlockPos(pos)));
			}
			return ActionResult.SUCCESS;
		}
	}

	static class TestScreen extends Screen {
		private final ControlGui gui;

		protected TestScreen(Text title, boolean vanilla, Control<?> control) {
			super(title);
			this.gui = new ControlGui(this, control);
			if (vanilla) this.gui.applyTheme(Theme.vanilla());
		}

		@Override
		public void init(MinecraftClient minecraftClient, int i, int j) {
			super.init(minecraftClient, i, j);
			gui.init();
		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			super.render(matrices, mouseX, mouseY, delta);
			gui.draw(matrices, mouseX, mouseY, delta);
		}

		@Override
		public boolean isPauseScreen() {
			return false;
		}
	}

	static class HandledTestScreen extends HandledScreen<TestScreenHandler> {
		private final ControlGui gui;

		public HandledTestScreen(TestScreenHandler handler, Text title, boolean vanilla, Control<?> control) {
			super(handler, handler.player.inventory, title);
			this.gui = new ControlGui(this, control);
			if (vanilla) this.gui.applyTheme(Theme.vanilla());
		}

		@Override
		public void init(MinecraftClient minecraftClient, int i, int j) {
			super.init(minecraftClient, i, j);
			gui.init();
		}

		@Override
		protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

		}

		@Override
		public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
			super.render(matrices, mouseX, mouseY, delta);
			gui.draw(matrices, mouseX, mouseY, delta);
		}

		@Override
		public boolean isPauseScreen() {
			return super.isPauseScreen();
		}

		@Override
		public void onClose() {
			super.onClose();
			handler.close(handler.player);
		}
	}

	static class TestScreenHandler extends ScreenHandler implements InventoryScreenHandler {
		private final PlayerEntity player;
		private final List<Inventory> inventories = new ArrayList<>();

		protected TestScreenHandler(int syncId, PlayerEntity player) {
			super(null, syncId);
			this.player = player;
			inventories.add(player.inventory);

			if (!player.world.isClient) OakTreeServerNetworking.listenForStackSync(this);
			inventories.add(new SimpleInventory(ItemStack.EMPTY, ItemStack.EMPTY));
		}

		@Override
		public boolean canUse(PlayerEntity player) {
			return true;
		}

		@Override
		public @Nullable Inventory getInventory(int inventoryID) {
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

	private static Control<?> testOne() {
		DropdownControl leftDropdown = new DropdownControl()
				.dropdown(
						new ListPanelControl()
								.id("base")
								.size(80, 80)
								.children(4, Tests::itemLabel)
								.displayCount(4)
				)
				.size(40, 20)
				.id("button")
				.dropdownDirection(Direction2D.LEFT)
				.anchor(Anchor.CENTER);

		leftDropdown.onTick((controlGui) -> {
			leftDropdown.dropdownDirection(Direction2D.RIGHT);
		});

		DropdownControl rightDropdown = new DropdownControl()
				.dropdown(
						new ListPanelControl()
								.id("base")
								.size(80, 80)
								.children(4, Tests::itemLabel)
								.displayCount(4)
				)
				.size(40, 20)
				.id("button")
				.dropdownDirection(Direction2D.RIGHT)
				.anchor(Anchor.CENTER);


		return new PanelControl<>()
				.child(new DropdownControl()
						.dropdown(
								new ListPanelControl()
										.id("base")
										.size(60, 60)
										.child(leftDropdown)
										.child(rightDropdown)
										.displayCount(2)
						)
						.size(60, 20)
						.id("button")
						.anchor(Anchor.CENTER)
				)
				.size(90, 50)
				.anchor(Anchor.CENTER)
				.id("base");
	}

	private static Control<?> testTwo() {
		ListPanelControl listPanel = new ListPanelControl()
				.size(100, 100)
				.children(20, Tests::itemLabel)
				.displayCount(5)
				.anchor(Anchor.CENTER);

		SliderControl scrollBar = new SliderControl()
				.size(20, 100)
				.onSlide((gui, control) -> {
					listPanel.startIndex((int) Math.floor(((listPanel.children.size() - listPanel.getDisplayCount()) * (control.getScrollPercent())/100)));
				})
				.barLength(10)
				.anchor(Anchor.CENTER);

		return new SplitControl()
				.id("base")
				.size(140, 120)
				.splitSize(110)
				.first(scrollBar)
				.second(listPanel)
				.anchor(Anchor.CENTER);
	}

	private static Control<?> testThree() {
		GridPanelControl playerInvGrid = new GridPanelControl()
				.size(162, 72)
				.anchor(Anchor.CENTER)
				.rows(4)
				.columns(9)
				.children(36, integer -> {
					int index = integer;
					if (integer < 27) {
						index += 9;
					}
					else {
						index -= 27;
					}

					return new SlotControl(index, 0);
				});

		SlotControl slot1 = new SlotControl(0, 1)
				.filter(true, Items.ANDESITE);

		SlotControl slot2 = new SlotControl(1, 1)
				.canTake((gui, slotControl, stack) -> false);

		return new PanelControl<>()
				.id("base")
				.size(180, 120)
				.anchor(Anchor.CENTER)
				.child(playerInvGrid);
	}

	private static Control<?> itemLabel(int number) {
		return new LabelControl()
				.size(60, 20)
				.text("Item No. " + (number + 1))
				.anchor(Anchor.CENTER)
				.shadow(true);
	}

	private static Control<?> testFour() {
		return new ButtonControl()
				.id("button")
				.size(200, 20)
				.anchor(Anchor.CENTER)
				.tooltip(
						new LabelControl()
								.text("Hi!")
								.size(40, 20)
				);
	}

	private static Control<?> testFive() {
		List<Text> texts = createText(50);

		LabelControl label = new LabelControl()
				.size(100, 100)
				.maxDisplayedLines(10)
				.id("tooltip")
				.text(texts)
				.anchor(Anchor.CENTER);

		SliderControl scrollBar = new SliderControl()
				.size(20, 100)
				.onSlide((gui, control) -> label.firstLine((int) Math.floor((45) * control.getScrollPercent()/100)))
				.barLength(10)
				.anchor(Anchor.CENTER);

		return new SplitControl()
				.id("base")
				.size(140, 120)
				.splitSize(110)
				.first(scrollBar)
				.second(label)
				.anchor(Anchor.CENTER);
	}

	private static List<Text> createText(int lines) {
		List<Text> texts = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < lines; i += 1) {
			LiteralText text = new LiteralText("Text.");
			int j = random.nextInt(3);
			Text formatted;

			switch (j) {
				case 0:
					formatted = text.formatted(Formatting.BOLD);
					break;
				case 1:
					formatted = text.formatted(Formatting.ITALIC);
					break;
				case 2:
					formatted = text.formatted(Formatting.UNDERLINE);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + j);
			}

			texts.add(formatted);
		}

		return texts;
	}
}
