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
import net.redstoneparadox.oaktree.client.gui.nodes.*;
import net.redstoneparadox.oaktree.client.gui.util.Alignment;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

public class Tests {

    private final Block TEST_ONE_BLOCK = new TestOneBlock(FabricBlockSettings.of(Material.METAL).build());
    private final Block TEST_TWO_BLOCK = new TestTwoBlock(FabricBlockSettings.of(Material.METAL).build());

    public void init() {
        Registry.register(Registry.BLOCK, new Identifier("oak_tree", "test_one_block"), TEST_ONE_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier("oak_tree", "test_two_block"), TEST_TWO_BLOCK);
    }

    private class TestOneBlock extends Block {

        TestOneBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                SplitBoxNode root = (SplitBoxNode) new SplitBoxNode().addLeftChild(
                        new TextureRectNode("textures/gui/container/shulker_box.png").setSize(176.0f, 166.0f).setAnchorAlignment(Alignment.CENTER).setDrawAlignment(Alignment.CENTER)
                ).addRightChild(
                        new SplitBoxNode().setLeftMargin(10.0f).setRightMargin(10.0f).setVertical(true).addRightChild(
                                new ColorRectNode().setColor(RGBAColor.blue()).setExpand(true)
                        ).addLeftChild(
                                new HoverNode().addMouseHoverListener((client, mouse, gui) -> System.out.println("Hello, world!")).setExpand(true)
                        ).setSplitPercent(30.0f).setExpand(true)
                ).setLeftMargin(10.0f).setSplitPercent(65.0f).setExpand(true);
                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }

    private class TestTwoBlock extends Block {

        public TestTwoBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                BoxNode root = (BoxNode) new BoxNode().addChild(
                        new TextureRectNode("textures/gui/container/shulker_box.png").setSize(176.0f, 166.0f).setAnchorAlignment(Alignment.CENTER).setDrawAlignment(Alignment.CENTER)
                ).setExpand(true);
                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }
}
