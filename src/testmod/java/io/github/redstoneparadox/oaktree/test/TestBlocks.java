package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.control.RootPanelControl;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class TestBlocks {
	public static final Block TEST_DRAW_BLOCK = new TestBlock(TestScreens::testDraw);
	public static final Block TEST_INTERACTABLES_BLOCK = new TestBlock(TestScreens::testInteractables);

	public static void init() {
		register(TEST_DRAW_BLOCK, "draw");
		register(TEST_INTERACTABLES_BLOCK, "interactables");
	}

	private static Block register(Block block, String suffix) {
		return Registry.register(Registry.BLOCK, new Identifier("oaktree", "test_" + suffix), block);
	}

	static class TestBlock extends Block {
		private final Supplier<RootPanelControl> supplier;

		TestBlock(Supplier<RootPanelControl> supplier) {
			super(testSettings());
			this.supplier = supplier;
		}

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
			if (world.isClient) {
				MinecraftClient.getInstance().setScreen(new TestScreens.TestScreen(new LiteralText("test screen"), supplier.get()));
			}
			return ActionResult.SUCCESS;
		}

		private static Settings testSettings() {
			return FabricBlockSettings.of(Material.METAL);
		}
	}

	static class TestBlockWithEntity extends TestBlock implements BlockEntityProvider {
		private final BlockEntityType<TestBlockEntities.TestBlockEntity> blockEntityType;

		TestBlockWithEntity(boolean vanilla, Supplier<RootPanelControl> supplier, BlockEntityType<TestBlockEntities.TestBlockEntity> blockEntityType) {
			super(supplier);
			this.blockEntityType = blockEntityType;
		}

		@Override
		public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
			if (!world.isClient) {
				NamedScreenHandlerFactory factory = state.createScreenHandlerFactory(world, pos);

				if (factory != null) {
					player.openHandledScreen(factory);
				}
			}
			return ActionResult.SUCCESS;
		}

		@Override
		public @Nullable BlockEntity createBlockEntity(BlockPos blockPos, BlockState blockState) {
			return new TestBlockEntities.TestBlockEntity(blockEntityType, blockPos, blockState);
		}

		@Override
		public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState blockState, World world, BlockPos blockPos) {
			BlockEntity blockEntity = world.getBlockEntity(blockPos);
			return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory) blockEntity : null;
		}
	}
}
