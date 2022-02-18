package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.control.RootPanelControl;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

class ScreenHandlerTestBlock extends TestBlock implements BlockEntityProvider {
	ScreenHandlerTestBlock(boolean vanilla, Supplier<RootPanelControl> supplier) {
		super(vanilla, supplier);
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
		return new ScreenHandlerTestBlockEntity(blockPos, blockState);
	}

	@Override
	public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState blockState, World world, BlockPos blockPos) {
		BlockEntity blockEntity = world.getBlockEntity(blockPos);
		return blockEntity instanceof NamedScreenHandlerFactory ? (NamedScreenHandlerFactory) blockEntity : null;
	}
}
