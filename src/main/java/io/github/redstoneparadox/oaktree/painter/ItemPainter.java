package io.github.redstoneparadox.oaktree.painter;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/**
 * A {@code Painter} used to draw an {@link ItemStack} on screen.
 */
public class ItemPainter extends Painter {
	private ItemStack stack;

	/**
	 * Creates a {@code ItemPainter} instance from an item
	 * identifier and a stack size. Note that if the id is
	 * invalid, the painter will "draw" air.
	 *
	 * @param identifier The item identifier
	 * @param count The number of items in the stack
	 */
	public ItemPainter(Identifier identifier, int count) {
		this.stack = new ItemStack(Registries.ITEM.get(identifier), count);
	}

	/**
	 * Creates an {@code ItemPainter} instance from an
	 * item stack.
	 *
	 * @param stack The stack to daw
	 */
	public ItemPainter(ItemStack stack) {
		this.stack = stack;
	}

	/**
	 * Creates a {@code ItemPainter} instance from an item
	 * identifier. Note that if the id is
	 * invalid, the painter will "draw" air.
	 *
	 * @param identifier The item identifier
	 */
	public ItemPainter(Identifier identifier) {
		this(identifier, 1);
	}

	/**
	 * Creates a {@code ItemPainter} instance from an item
	 * identifier. Note that if the id is
	 * invalid, the painter will "draw" air.
	 *
	 * @param identifier The item identifier
	 */
	public ItemPainter(String identifier) {
		this(new Identifier(identifier), 1);
	}

	/**
	 * Creates a {@code ItemPainter} instance from an item
	 * identifier and a stack size. Note that if the id is
	 * invalid, the painter will "draw" air.
	 *
	 * @param identifier The item identifier
	 * @param count The number of items in the stack
	 */
	public ItemPainter(String identifier, int count) {
		this(new Identifier(identifier), count);
	}

	public int getCount() {
		return stack.getCount();
	}

	/**
	 * Sets the size of the item stack.
	 *
	 * @param count The item count
	 */
	public void setCount(int count) {
		stack.setCount(count);
	}

	public Item getItem() {
		return stack.getItem();
	}

	/**
	 * Sets the item to draw.
	 *
	 * @param item The item to draw.
	 */
	public void setItem(Item item) {
		stack = new ItemStack(item, stack.getCount());
	}

	@Override
	public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
		if (!stack.isEmpty()) {
			TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
			int offsetX = width/2;
			int offsetY = height/2;

			graphics.drawItem(stack, x + offsetX - 8, y + offsetY - 8);
			graphics.drawItemInSlot(textRenderer, stack, x + offsetX - 8, y + offsetY - 8);
		}
	}

	@Override
	public Painter copy() {
		return new ItemPainter(stack);
	}
}
