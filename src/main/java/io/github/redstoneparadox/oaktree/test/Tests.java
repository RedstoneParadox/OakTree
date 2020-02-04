package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.client.gui.ScreenBuilder;
import io.github.redstoneparadox.oaktree.client.gui.control.*;
import io.github.redstoneparadox.oaktree.client.gui.style.ColorStyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class Tests {
    private final TestBlock testOne = new TestBlock(testSettings(), this::testOne);
    private final TestBlock testTwo = new TestBlock(testSettings(), this::testTwo);

    public void init() {
        register(testOne, "one");
        register(testTwo, "two");
    }

    private Block.Settings testSettings() {
        return FabricBlockSettings.of(Material.METAL).build();
    }

    private void register(Block block, String testNum) {
        Registry.register(Registry.BLOCK, new Identifier("oaktree", "test_" + testNum), block);
    }

    static class TestBlock extends Block {
        private final Supplier<Control> supplier;

        TestBlock(Settings settings, Supplier<Control> supplier) {
            super(settings);
            this.supplier = supplier;
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if (world.isClient) {
                MinecraftClient.getInstance().openScreen(new ScreenBuilder(supplier.get()).build());
            }
            return ActionResult.SUCCESS;
        }
    }

    private Control testOne() {
        return new TextEditControl()
                .size(200, 200)
                .defaultStyle(new ColorStyleBox(RGBAColor.black()))
                .maxLines(20)
                .displayedLines(10);
    }

    private Control testTwo() {
        PagePanelControl pagePanelControl = new PagePanelControl();

        return new SplitPanelControl()
                .size(200, 300)
                .splitSize(200)
                .verticalSplit(true)
                .child(
                        pagePanelControl
                                .size(200, 200)
                                .defaultStyle(new ColorStyleBox(RGBAColor.black()))
                                .child(
                                        new LabelControl()
                                                .size(200, 50)
                                                .text("Page 1.")
                                )
                                .child(
                                        new LabelControl()
                                                .size(200, 50)
                                                .text("Page 2.")
                                )
                                .child(
                                        new LabelControl()
                                                .size(200, 50)
                                                .text("Page 3.")
                                )
                )
                .child(
                        new SplitPanelControl()
                                .size(200, 100)
                                .splitSize(100)
                                .child(
                                new ButtonControl()
                                .size(50, 50)
                                .anchor(ControlAnchor.CENTER)
                                .defaultStyle(new ColorStyleBox(RGBAColor.red()))
                                .onClick((gui, control) -> pagePanelControl.previousPage())
                                )
                                .child(
                                        new ButtonControl()
                                                .size(50, 50)
                                                .anchor(ControlAnchor.CENTER)
                                                .defaultStyle(new ColorStyleBox(RGBAColor.green()))
                                                .onClick((gui, control) -> pagePanelControl.nextPage())
                                )
                );
    }
}
