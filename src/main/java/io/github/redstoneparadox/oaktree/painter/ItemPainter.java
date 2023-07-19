package io.github.redstoneparadox.oaktree.painter;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemPainter extends Painter {
	private ItemStack stack;

	public ItemPainter(Identifier identifier, int count) {
		this.stack = new ItemStack(Registries.ITEM.get(identifier), count);
	}

	public ItemPainter(ItemStack stack) {
		this.stack = stack;
	}

	public ItemPainter(Identifier identifier) {
		this(identifier, 1);
	}

	public ItemPainter(String identifier) {
		this(new Identifier(identifier), 1);
	}

	public ItemPainter(String identifier, int count) {
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
	public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
		if (!stack.isEmpty()) {
			int offsetX = width/2;
			int offsetY = height/2;

			graphics.drawItem(stack, x + offsetX, y + offsetY);
		}
	}

	@Override
	public Painter copy() {
		return new ItemPainter(stack);
	}
}
