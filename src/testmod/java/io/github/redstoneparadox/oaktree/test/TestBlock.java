package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.control.Control;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

class TestBlock extends Block {
	private final Supplier<Control> supplier;
	private final boolean vanilla;

	TestBlock(boolean vanilla, Supplier<Control> supplier) {
		super(testSettings());
		this.vanilla = vanilla;
		this.supplier = supplier;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (world.isClient) {
			MinecraftClient.getInstance().setScreen(new TestScreen(new LiteralText("test screen"), vanilla, supplier.get()));
		}
		return ActionResult.SUCCESS;
	}

	private static Block.Settings testSettings() {
		return FabricBlockSettings.of(Material.METAL);
	}
}
