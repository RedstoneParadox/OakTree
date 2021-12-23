package io.github.redstoneparadox.oaktree.style;

import io.github.redstoneparadox.oaktree.util.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemControlStyle extends ControlStyle {
	private ItemStack stack;

	public ItemControlStyle(Identifier identifier, int count) {
		this.stack = new ItemStack(Registry.ITEM.get(identifier), count);
	}

	public ItemControlStyle(ItemStack stack) {
		this.stack = stack;
	}

	public ItemControlStyle(Identifier identifier) {
		this(identifier, 1);
	}

	public ItemControlStyle(String identifier) {
		this(new Identifier(identifier), 1);
	}

	public ItemControlStyle(String identifier, int count) {
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
	public void draw(int x, int y, int width, int height) {
		if (!stack.isEmpty()) {
			RenderHelper.drawItemStackCentered(x, y, width, height, stack);
		}
	}

	@Override
	public ControlStyle copy() {
		return new ItemControlStyle(stack);
	}
}
