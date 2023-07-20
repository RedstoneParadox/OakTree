package io.github.redstoneparadox.oaktree.test;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.registry.Registries;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;

public class TestBlockEntities {
	public static final BlockEntityType<TestBlockEntity> TEST_INVENTORY = QuiltBlockEntityTypeBuilder.create(TestBlockEntity::new, TestBlocks.TEST_INVENTORY).build();

	public static void init() {
		Registry.register(
				Registries.BLOCK_ENTITY_TYPE,
				new Identifier("oaktree", "test_inventory"),
				TEST_INVENTORY
		);
	}

	static class TestBlockEntity extends BlockEntity implements NamedScreenHandlerFactory {
		public TestBlockEntity(BlockPos pos, BlockState state) {
			super(TEST_INVENTORY, pos, state);
		}

		@Override
		public Text getDisplayName() {
			return Text.empty();
		}

		@Override
		public @Nullable ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
			return new TestScreenHandlers.TestScreenHandler(i, playerEntity.getInventory());
		}
	}
}
