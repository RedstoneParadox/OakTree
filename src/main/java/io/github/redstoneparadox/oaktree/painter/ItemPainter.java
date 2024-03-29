package io.github.redstoneparadox.oaktree.painter;

import io.github.redstoneparadox.oaktree.util.RenderHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemPainter extends Painter {
	private ItemStack stack;

	public ItemPainter(Identifier identifier, int count) {
		this.stack = new ItemStack(Registry.ITEM.get(identifier), count);
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
	public void draw(MatrixStack matrices, int x, int y, int width, int height) {
		if (!stack.isEmpty()) {
			RenderHelper.drawItemStackCentered(x, y, width, height, stack);
		}
	}

	@Override
	public Painter copy() {
		return new ItemPainter(stack);
	}
}
