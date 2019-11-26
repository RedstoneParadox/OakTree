package net.redstoneparadox.oaktree.client.gui.style;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.mixin.client.gui.screen.ScreenAccessor;

public class ItemStyleBox extends StyleBox {

    private Identifier itemID;

    public ItemStyleBox(Identifier itemID) {
        this.itemID = itemID;
    }

    public ItemStyleBox(String itemID) {
        this(new Identifier(itemID));
    }

    @Override
    public void draw(float x, float y, float width, float height, OakTreeGUI gui, boolean mirroredHorizontal, boolean mirroredVertical) {
        ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
        Item item = Registry.ITEM.get(itemID);
        GuiLighting.enableForItems(new Matrix4f());
        ItemStack stack = new ItemStack(item);
        renderer.renderGuiItem(stack, (int)x - 7, (int)y - 7);
        if (gui instanceof Screen) {
            TextRenderer font = ((ScreenAccessor)gui).getFont();
            renderer.renderGuiItemOverlay(font, stack, (int)x - 7, (int)y - 7);
        }
    }
}
