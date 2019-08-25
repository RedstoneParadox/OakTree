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

    private static class TestOneBlock extends Block {

        TestOneBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                BoxNode root = new BoxNode()
                        .setChild(new Node()
                            .setExpand(true)
                            .setDefaultStyle(new ColorStyleBox(RGBAColor.red())))
                        .setExpand(true)
                        .setMargin(15.0f);


                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }

    private static class TestTwoBlock extends Block {

        TestTwoBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                SplitBoxNode root = new SplitBoxNode()
                        .setExpand(true)
                        .setSplitPercent(50.0f)
                        .setLeftMargin(10.0f)
                        .setRightMargin(10.0f)
                        .setLeftChild(new Node()
                                        .setExpand(true)
                                        .setDefaultStyle(new ColorStyleBox(RGBAColor.red())))
                        .setRightChild(new Node()
                                        .setExpand(true)
                                        .setDefaultStyle(new ColorStyleBox(RGBAColor.blue())));

                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }

    private static class TestThreeBlock extends Block {

        TestThreeBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                SplitBoxNode root = new SplitBoxNode()
                        .setExpand(true)
                        .setSplitPercent(50.0f)
                        .setLeftMargin(10.0f)
                        .setRightMargin(10.0f)
                        .setLeftChild(new ProgressBarNode()
                                .setDefaultStyle(new ColorStyleBox(RGBAColor.black()))
                                .setBarStyle(new ColorStyleBox(RGBAColor.red()))
                                .setSize(20.0f, 100.0f)
                                .setBarSize(16.0f, 96.0f)
                                .setAlignment(NodeAlignment.CENTER)
                                .setAnchor(NodeAlignment.CENTER)
                                .setBarDirection(NodeDirection.DOWN)
                                .setPercent(50.0f))
                        .setRightChild(new Node()
                                .setExpand(true)
                                .setDefaultStyle(new ColorStyleBox(RGBAColor.blue())));

                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }

    private static class TestFourBlock extends Block {

        TestFourBlock(Settings block$Settings_1) {
            super(block$Settings_1);
        }

        @Override
        public boolean activate(BlockState blockState_1, World world_1, BlockPos blockPos_1, PlayerEntity playerEntity_1, Hand hand_1, BlockHitResult blockHitResult_1) {
            if (world_1.isClient()) {
                ProgressBarNode bar = new ProgressBarNode();

                SplitBoxNode root = new SplitBoxNode()
                        .setExpand(true)
                        .setSplitPercent(50.0f)
                        .setLeftMargin(10.0f)
                        .setRightMargin(10.0f)
                        .setRightChild(bar
                                .setDefaultStyle(new ColorStyleBox(RGBAColor.black()))
                                .setBarStyle(new ColorStyleBox(RGBAColor.red()))
                                .setSize(20.0f, 100.0f)
                                .setBarSize(16.0f, 96.0f)
                                .setAlignment(NodeAlignment.CENTER)
                                .setAnchor(NodeAlignment.CENTER)
                                .setBarDirection(NodeDirection.DOWN)
                                .setPercent(0.0f))
                        .setLeftChild(new ButtonNode()
                                .setExpand(true)
                                .onClick(((gui, node) -> {
                                    bar.percentFilled += 10.0f;
                                    if (bar.percentFilled > 100.0f) {
                                        bar.percentFilled = 0.0f;
                                    }
                                }))
                                .setAlignment(NodeAlignment.CENTER)
                                .setAnchor(NodeAlignment.CENTER)
                                .setDefaultStyle(new ColorStyleBox(RGBAColor.blue()))
                                .setHeldStyle(new ColorStyleBox(RGBAColor.red())));

                MinecraftClient.getInstance().openScreen(new OakTreeScreen(root, false));
            }

            return true;
        }
    }
}
