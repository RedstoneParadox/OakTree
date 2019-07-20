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
import net.redstoneparadox.oaktree.client.gui.style.ColorStyleBox;
import net.redstoneparadox.oaktree.client.gui.util.NodeAlignment;
import net.redstoneparadox.oaktree.client.gui.util.NodeDirection;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

import javax.swing.*;

public class Tests {

    private final Block TEST_ONE_BLOCK = new TestOneBlock(testSettings());
    private final Block TEST_TWO_BLOCK = new TestTwoBlock(testSettings());
    private final Block TEST_THREE_BLOCK = new TestThreeBlock(testSettings());
    private final Block TEST_FOUR_BLOCK = new TestFourBlock(testSettings());

    public void init() {
        register(TEST_ONE_BLOCK, "one");
        register(TEST_TWO_BLOCK, "two");
        register(TEST_THREE_BLOCK, "three");
        register(TEST_FOUR_BLOCK, "four");
    }

    private Block.Settings testSettings() {
        return FabricBlockSettings.of(Material.METAL).build();
    }

    private void register(Block block, String testNum) {
        Registry.register(Registry.BLOCK, new Identifier("oak_tree", "test_" + testNum + "_block"), block);
    }

    private class TestOneBlock extends Block {

        TestOneBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                SplitBoxNode root = (SplitBoxNode) new SplitBoxNode().addLeftChild(
                        new TextureRectNode("textures/gui/container/shulker_box.png").setSize(176.0f, 166.0f).setAnchorAlignment(NodeAlignment.CENTER).setDrawAlignment(NodeAlignment.CENTER)
                ).addRightChild(
                        new SplitBoxNode().setLeftMargin(10.0f).setRightMargin(10.0f).setVertical(true).addRightChild(
                                new ColorRectNode().setColor(RGBAColor.blue()).setExpand(true)
                        ).addLeftChild(
                                new LabelNode().setText("Hello, Oak Tree!").setDefaultStyle(new ColorStyleBox(RGBAColor.black()))
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
                        new TextureRectNode("textures/gui/container/shulker_box.png").setSize(176.0f, 166.0f).setAnchorAlignment(NodeAlignment.CENTER).setDrawAlignment(NodeAlignment.CENTER)
                ).setExpand(true);
                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }

    private class TestThreeBlock extends Block {

        TestThreeBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                Node root = new SplitBoxNode().addLeftChild(
                        new ButtonNode()
                                .setHeldStyle(new ColorStyleBox(RGBAColor.black()))
                                .setDefaultStyle(new ColorStyleBox(RGBAColor.white()))
                                .setSize(50.0f, 50.0f)
                                .setAnchorAlignment(NodeAlignment.CENTER)
                                .setDrawAlignment(NodeAlignment.CENTER)
                ).addRightChild(
                        new ButtonNode()
                                .setToggleable(true)
                                .setHeldStyle(new ColorStyleBox(RGBAColor.blue()))
                                .setDefaultStyle(new ColorStyleBox(RGBAColor.red()))
                                .setSize(50.0f, 50.0f)
                                .setAnchorAlignment(NodeAlignment.CENTER)
                                .setDrawAlignment(NodeAlignment.CENTER)
                        ).setExpand(true);
                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }

    private class TestFourBlock extends Block {

        TestFourBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                Node root = new SplitBoxNode().addLeftChild(
                        new SplitBoxNode()
                            .setVertical(true)
                            .addLeftChild(
                                new ProgressBarNode()
                                    .setPercent(50.0f)
                                    .setBarSize(116.0f, 16.0f)
                                    .setBarStyle(new ColorStyleBox(RGBAColor.red()))
                                    .setDefaultStyle(new ColorStyleBox(RGBAColor.black()))
                                    .setSize(120.0f, 20.0f)
                                    .setDrawAlignment(NodeAlignment.CENTER)
                                    .setAnchorAlignment(NodeAlignment.CENTER)
                            ).addRightChild(
                                new ProgressBarNode()
                                    .setPercent(50.0f)
                                    .setBarDirection(NodeDirection.LEFT)
                                    .setBarSize(116.0f, 16.0f)
                                    .setBarStyle(new ColorStyleBox(RGBAColor.red()))
                                    .setDefaultStyle(new ColorStyleBox(RGBAColor.black()))
                                    .setSize(120.0f, 20.0f)
                                    .setDrawAlignment(NodeAlignment.CENTER)
                                    .setAnchorAlignment(NodeAlignment.CENTER)
                            ).setExpand(true)
                ).addRightChild(
                        new SplitBoxNode()
                            .setVertical(true)
                            .addLeftChild(
                                new ProgressBarNode()
                                    .setPercent(50.0f)
                                    .setBarDirection(NodeDirection.DOWN)
                                    .setBarSize(16.0f, 116.0f)
                                    .setBarStyle(new ColorStyleBox(RGBAColor.red()))
                                    .setDefaultStyle(new ColorStyleBox(RGBAColor.black()))
                                    .setSize(20.0f, 120.0f)
                                        .setDrawAlignment(NodeAlignment.CENTER)
                                        .setAnchorAlignment(NodeAlignment.CENTER)
                            ).addRightChild(
                                new ProgressBarNode()
                                    .setPercent(50.0f)
                                    .setBarDirection(NodeDirection.UP)
                                    .setBarSize(16.0f, 116.0f)
                                    .setBarStyle(new ColorStyleBox(RGBAColor.red()))
                                    .setDefaultStyle(new ColorStyleBox(RGBAColor.black()))
                                    .setSize(20.0f, 120.0f)
                                        .setDrawAlignment(NodeAlignment.CENTER)
                                        .setAnchorAlignment(NodeAlignment.CENTER)
                            ).setExpand(true)
                ).setExpand(true);
                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }

}
