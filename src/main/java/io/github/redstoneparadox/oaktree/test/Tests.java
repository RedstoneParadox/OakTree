package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.client.gui.ScreenBuilder;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;
import io.github.redstoneparadox.oaktree.client.gui.control.TextEditControl;
import io.github.redstoneparadox.oaktree.client.gui.style.ColorStyleBox;
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

    public void init() {
        register(testOne, "one");
    }

    private Block.Settings testSettings() {
        return FabricBlockSettings.of(Material.METAL).build();
    }

    private void register(Block block, String testNum) {
        Registry.register(Registry.BLOCK, new Identifier("oaktree", "test_" + testNum), block);
    }

    class TestBlock extends Block {
        private final Supplier<Control> supplier;

        public TestBlock(Settings settings, Supplier<Control> supplier) {
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
                .maxLines(10);
    }
}
