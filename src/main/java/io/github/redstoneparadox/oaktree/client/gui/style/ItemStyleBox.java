package io.github.redstoneparadox.oaktree.client.gui.style;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.registry.Registry;

public class ItemStyleBox extends StyleBox {

    private ItemStack stack;

    public ItemStyleBox(Identifier identifier, int count) {
        this.stack = new ItemStack(Registry.ITEM.get(identifier), count);
    }

    public ItemStyleBox(Identifier identifier) {
        this(identifier, 1);
    }

    public ItemStyleBox(String identifier) {
        this(new Identifier(identifier), 1);
    }

    public ItemStyleBox(String identifier, int count) {
        this(new Identifier(identifier), count);
    }

    public int getCount() {
        return stack.getCount();
    }

    public void setCount(int count) {
        stack.setCount(count);
    }

    public Item getItem() {
        return stack.getItem();
    }

    public void setItem(Item item) {
        stack = new ItemStack(item, stack.getCount());
    }

    @Override
    public void draw(int x, int y, int width, int height, ControlGui gui, boolean mirroredHorizontal, boolean mirroredVertical) {
        ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();
        DiffuseLighting.enableForLevel(new Matrix4f());
        renderer.renderGuiItem(stack, (int)x - 7, (int)y - 7);
        TextRenderer textRenderer = gui.getTextRenderer();
        renderer.renderGuiItemOverlay(textRenderer, stack, (int)x - 7, (int)y - 7);
    }
}
