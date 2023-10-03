package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.control.RootPanelControl;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;

import java.util.function.Supplier;

public class TestBlocks {
	public static final Block TEST_DRAW_BLOCK = new TestBlock(TestScreens::testTutorialOne);
	public static final Block TEST_INTERACTABLES_BLOCK = new TestBlock(TestScreens::testInteractables);
	public static final Block TEST_COLORS_BLOCK = new TestBlock(TestScreens::testColors);
	public static final Block TEST_INVENTORY = new TestBlockWithEntity();

	public static void init() {
		register(TEST_DRAW_BLOCK, "draw");
		register(TEST_INTERACTABLES_BLOCK, "interactables");
		register(TEST_COLORS_BLOCK, "colors");
		register(TEST_INVENTORY, "inventory");
	}

	private static void register(Block block, String suffix) {
		Registry.register(Registries.BLOCK, new Identifier("oaktree", "test_" + suffix), block);
	}

	private static AbstractBlock.Settings testSettings() {
		return QuiltBlockSettings.create();
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
				MinecraftClient.getInstance().setScreen(new TestScreens.TestScreen(Text.literal("test screen"), supplier.get()));
			}
			return ActionResult.SUCCESS;
		}
	}

	static class TestBlockWithEntity extends BlockWithEntity {
		TestBlockWithEntity() {
			super(testSettings());
		}

		@Override
		public BlockRenderType getRenderType(BlockState blockState) {
			return BlockRenderType.MODEL;
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
			return new TestBlockEntities.TestBlockEntity(blockPos, blockState);
		}
	}
}
