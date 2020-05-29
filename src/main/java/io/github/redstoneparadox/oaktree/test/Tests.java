package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.ScreenBuilder;
import io.github.redstoneparadox.oaktree.client.gui.control.*;
import io.github.redstoneparadox.oaktree.client.gui.style.ColorStyleBox;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlAnchor;
import io.github.redstoneparadox.oaktree.client.gui.util.ControlDirection;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class Tests {

    public void init() {

        register(new TestBlock(false, this::testOne), "one");

        /*
        ScreenProviderRegistry.INSTANCE.registerFactory(testFourID, (syncId, identifier, player, buf) -> {
            BlockPos pos = buf.readBlockPos();
            return new ScreenBuilder
                    (
                            testFour()
                    )
                    .container(new TestScreenHandler(syncId, player))
                    .playerInventory(player.inventory)
                    .text(new LiteralText("Test 4"))
                    .buildContainerScreen();
        });

        ContainerProviderRegistry.INSTANCE.registerFactory(testFourID, (syncId, identifier, player, buf) -> {
            ScreenHandler screenHandler = new TestScreenHandler(syncId, player);
            OakTreeNetworking.addContainerForSyncing(screenHandler);
            return screenHandler;
        });
         */
    }

    private static Block.Settings testSettings() {
        return FabricBlockSettings.of(Material.METAL).build();
    }

    private void register(Block block, String suffix) {
        Registry.register(Registry.BLOCK, new Identifier("oaktree", "test_" + suffix), block);
    }

    class TestBlock extends Block {
        private final Supplier<Control<?>> supplier;
        private final boolean vanilla;

        TestBlock(boolean vanilla, Supplier<Control<?>> supplier) {
            super(testSettings());
            this.vanilla = vanilla;
            this.supplier = supplier;
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if (world.isClient) {
                MinecraftClient.getInstance().openScreen(new TestScreen(new LiteralText("test screen"), vanilla, supplier.get()));
            }
            return ActionResult.SUCCESS;
        }
    }

    class ContainerTestBlock extends TestBlock {
        private final Identifier containerID;

        ContainerTestBlock(boolean vanilla, Supplier<Control<?>> supplier, Identifier containerID) {
            super(vanilla, supplier);
            this.containerID = containerID;
        }

        @Override
        public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
            if (!world.isClient()) {
                ContainerProviderRegistry.INSTANCE.openContainer(containerID, player, (buf -> buf.writeBlockPos(pos)));
            }
            return ActionResult.SUCCESS;
        }
    }

    static class TestScreen extends Screen {
        private final ControlGui gui;

        protected TestScreen(Text title, boolean vanilla, Control<?> control) {
            super(title);
            this.gui = new ControlGui(this, control);
            if (vanilla) this.gui.applyTheme(Theme.vanilla());
        }

        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            super.render(matrices, mouseX, mouseY, delta);
            gui.draw(matrices, mouseX, mouseY, delta);
        }

        @Override
        public boolean isPauseScreen() {
            return false;
        }
    }

    static class TestScreenHandler extends ScreenHandler {
        private final PlayerInventory playerInventory;

        protected TestScreenHandler(int syncId, PlayerEntity player) {
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

    private Control<?> testOne() {
        DropdownControl leftDropdown = new DropdownControl(
                new ListPanelControl()
                        .defaultStyle(new ColorStyleBox(RGBAColor.red()))
                        .size(60, 80)
                        .children(4, this::itemLabel)
                        .displayCount(4)
        )
                .size(60, 20)
                .defaultStyle(new ColorStyleBox(RGBAColor.blue()))
                .dropdownDirection(ControlDirection.LEFT)
                .anchor(ControlAnchor.CENTER);

        DropdownControl rightDropdown = new DropdownControl(
                new ListPanelControl()
                        .defaultStyle(new ColorStyleBox(RGBAColor.red()))
                        .size(60, 80)
                        .children(4, this::itemLabel)
                        .displayCount(4)
        )
                .size(60, 20)
                .defaultStyle(new ColorStyleBox(RGBAColor.green()))
                .dropdownDirection(ControlDirection.RIGHT)
                .anchor(ControlAnchor.CENTER);


        return new DropdownControl(
                new ListPanelControl()
                        .defaultStyle(new ColorStyleBox(RGBAColor.red()))
                        .size(60, 40)
                        .child(leftDropdown)
                        .child(rightDropdown)
                        .displayCount(2)
        )
                .size(60, 20)
                .defaultStyle(new ColorStyleBox(RGBAColor.black()))
                .anchor(ControlAnchor.CENTER);
    }

    private Control<?> itemLabel(int number) {
        return new LabelControl()
                .size(60, 20)
                .text("Item No. " + number)
                .shadow(true);
    }
}
