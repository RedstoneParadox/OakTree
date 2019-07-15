package net.redstoneparadox.oaktree.test;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.redstoneparadox.oaktree.client.gui.OakTreeScreen;
import net.redstoneparadox.oaktree.client.gui.nodes.ColorRectNode;
import net.redstoneparadox.oaktree.client.gui.util.Alignment;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

public class TestOne {

    private final Block TEST_BLOCK = new TestOneBlock(FabricBlockSettings.of(Material.METAL).build());

    public void init() {
        Registry.register(Registry.BLOCK, new Identifier("oak_tree", "test_one_block"), TEST_BLOCK);
    }

    private class TestOneBlock extends Block {

        TestOneBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            ColorRectNode rect = (ColorRectNode) new ColorRectNode().setColor(RGBAColor.white()).setExpand(true);
            MinecraftClient.getInstance().openScreen(new OakTreeScreen(rect));
            return true;
        }
    }
}
