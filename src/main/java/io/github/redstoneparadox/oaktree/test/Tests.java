package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.client.gui.ScreenBuilder;
import io.github.redstoneparadox.oaktree.client.gui.control.*;
import io.github.redstoneparadox.oaktree.client.gui.style.ColorStyleBox;
import io.github.redstoneparadox.oaktree.client.gui.style.NinePatchStyleBox;
import io.github.redstoneparadox.oaktree.client.gui.style.TextureStyleBox;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import io.github.redstoneparadox.oaktree.networking.OakTreeNetworking;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class Tests {
    private final TestBlock testOne = new TestBlock(testSettings(), false, this::testOne);
    private final TestBlock testTwo = new TestBlock(testSettings(), false, this::testTwo);
    private final TestBlock testThree = new TestBlock(testSettings(), false, this::testThree);
    private final ContainerTestBlock testFour = new ContainerTestBlock(testSettings(), false, this::testFour);
    private final TestBlock testFive = new TestBlock(testSettings(), false, this::testFive);
    private final TestBlock testSix = new TestBlock(testSettings(), true, this::testSix);

    private Identifier testFourID = new Identifier("oaktree:test_four");

    public void init() {
        register(testOne, "one");
        register(testTwo, "two");
        register(testThree, "three");
        register(testFour, "four");
        register(testFive, "five");
        register(testSix, "six");

        ScreenProviderRegistry.INSTANCE.registerFactory(testFourID, (syncId, identifier, player, buf) -> {
            BlockPos pos = buf.readBlockPos();
            return new ScreenBuilder
                    (
                            testFour()
                    )
                    .container(new TestContainer(syncId, player))
                    .playerInventory(player.inventory)
                    .text(new LiteralText("Test 4"))
                    .buildContainerScreen();
        });

        ContainerProviderRegistry.INSTANCE.registerFactory(testFourID, (syncId, identifier, player, buf) -> {
            Container container = new TestContainer(syncId, player);
            OakTreeNetworking.addContainerForSyncing(container);
            return container;
        });
    }

    private Block.Settings testSettings() {
        return FabricBlockSettings.of(Material.METAL).build();
    }

    private void register(Block block, String suffix) {
        Registry.register(Registry.BLOCK, new Identifier("oaktree", "test_" + suffix), block);
    }

    class TestBlock extends Block {
        private final Supplier<Control> supplier;
        private final boolean vanilla;

        TestBlock(Settings settings, boolean vanilla, Supplier<Control> supplier) {
            super(settings);
            this.vanilla = vanilla;
            this.supplier = supplier;
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if (world.isClient) {
                ScreenBuilder builder = new ScreenBuilder(supplier.get());
                if (vanilla) builder.theme(Theme.vanilla());
                MinecraftClient.getInstance().openScreen(builder.build());
            }
            return ActionResult.SUCCESS;
        }
    }

    class ContainerTestBlock extends TestBlock {
        ContainerTestBlock(Settings settings, boolean vanilla, Supplier<Control> supplier) {
            super(settings, vanilla, supplier);
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if (!world.isClient()) {
                ContainerProviderRegistry.INSTANCE.openContainer(testFourID, player, (buf -> buf.writeBlockPos(pos)));
            }
            return ActionResult.SUCCESS;
        }
    }

    static class TestContainer extends Container {
        private final PlayerInventory playerInventory;

        protected TestContainer(int syncId, PlayerEntity player) {
            super(null, syncId);
            this.playerInventory = player.inventory;

            for (int i = 0; i < 3; i += 1) {
                for (int j = 0; j < 9; j += 1) {
                    this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 0, 0));
                }
            }

            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j, 0, 0));
            }
        }

        @Override
        public boolean canUse(PlayerEntity player) {
            return true;
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

    private Control testThree() {
        return new Control()
                .size(100, 200)
                .anchor(ControlAnchor.CENTER)
                .defaultStyle(
                        new NinePatchStyleBox("oaktree:textures/gui/ui.png")
                                .widths(5, 1, 5)
                                .heights(5, 1, 5)
                );
    }

    private Control testFour() {
        return new BoxControl()
                .size(200, 200)
                .anchor(ControlAnchor.CENTER)
                .defaultStyle(
                        new NinePatchStyleBox("oaktree:textures/gui/ui.png")
                                .widths(5, 1, 5)
                                .heights(5, 1, 5)
                )
                .child(
                        new GridPanelControl()
                                .rows(4)
                                .columns(9)
                                .anchor(ControlAnchor.CENTER)
                                .size(162, 72)
                                .cells(this::slot)
                );
    }

    private Control slot(int row, int column, int index) {
        return new ItemSlotControl(index)
                .size(18, 18)
                .anchor(ControlAnchor.CENTER)
                .defaultStyle(
                    new TextureStyleBox("oaktree:textures/gui/ui.png").drawOrigin(18, 0)
                );
    }

    private Control testFive() {
        ListPanelControl list = new ListPanelControl();

        return new SplitPanelControl()
                .size(300, 200)
                .splitSize(200)
                .anchor(ControlAnchor.CENTER)
                .child(
                        list
                                .size(200, 200)
                                .children(20, this::itemLabel)
                                .displayCount(4)
                                .defaultStyle(new ColorStyleBox(RGBAColor.black()))
                )
                .child(
                        new SplitPanelControl()
                                .verticalSplit(true)
                                .size(100, 200)
                                .splitSize(100)
                                .child(
                                        new ButtonControl()
                                                .size(50, 50)
                                                .anchor(ControlAnchor.CENTER)
                                                .defaultStyle(new ColorStyleBox(RGBAColor.green()))
                                                .onClick((gui, control) -> list.scroll(-1))
                                )
                                .child(
                                        new ButtonControl()
                                                .size(50, 50)
                                                .anchor(ControlAnchor.CENTER)
                                                .defaultStyle(new ColorStyleBox(RGBAColor.red()))
                                                .onClick((gui, control) -> list.scroll(1))
                                )
                );
    }

    private Control itemLabel(int i) {
        return new LabelControl()
                .size(200, 50)
                .anchor(ControlAnchor.CENTER)
                .shadow(true)
                .text("List Item " + (i + 1) + ".");
    }

    private Control testSix() {
        return new BoxControl()
                .expand(true)
                .defaultStyle(
                        new TextureStyleBox("textures/gui/options_background.png")
                                .textureSize(16, 16)
                                .magicNumber(2)
                                .tiled(true)
                );
    }
}
