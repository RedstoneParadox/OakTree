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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.function.Supplier;

@SuppressWarnings("ALL")
public class Tests {

    public void init() {

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
        private final Identifier containerID;

        ContainerTestBlock(Settings settings, boolean vanilla, Supplier<Control> supplier, Identifier containerID) {
            super(settings, vanilla, supplier);
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
}
