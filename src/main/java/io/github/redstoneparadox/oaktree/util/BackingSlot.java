package io.github.redstoneparadox.oaktree.util;

import io.github.redstoneparadox.oaktree.control.SlotControl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

/**
 * A custom {@link Slot} implementation that is not
 * rendered by {@link ScreenHandler}.
 * Use with {@link SlotControl}.
 */
public class BackingSlot extends Slot {
	private boolean enabled;
	private Predicate<ItemStack> canInsert = stack -> true;
	private Predicate<PlayerEntity> canTake = player -> true;

	public BackingSlot(Inventory inventory, int index) {
		super(inventory, index, 0, 0);
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean canInsert(ItemStack itemStack) {
		return canInsert.test(itemStack);
	}

	public void setCanInsert(@NotNull Predicate<ItemStack> canInsert) {
		this.canInsert = canInsert;
	}

	/**
	 * Helper method for setting a list of {@link Item}
	 * to match against when inserting into slot.
	 *
	 * @param allow Whether matching an item in the list
	 *              should allow or deny inserting a stack.
	 * @param items The items to allow/deny.
	 */
	public void insertFilter(boolean allow, Item... items) {
		this.canInsert = ((stack) -> {
			for (Item item: items) {
				if (stack.getItem() == item) return allow;
			}
			return !allow;
		});
	}

	/**
	 * Helper method for setting a list of {@link Tag <Item>}
	 * to match against when inserting into slot.
	 *
	 * @param allow Whether matching an item tag in the list
	 *              should allow or deny inserting a stack.
	 * @param tags The item tags to match against.
	 */
	@SafeVarargs
	public final void insertFilter(boolean allow, Tag<Item>... tags) {
		this.canInsert = ((stack) -> {
			for (Tag<Item> tag: tags) {
				if (tag.contains(stack.getItem())) return allow;
			}
			return !allow;
		});
	}

	@Override
	public boolean canTakeItems(PlayerEntity playerEntity) {
		return canTake.test(playerEntity);
	}

	public void setCanTake(Predicate<PlayerEntity> canTake) {
		this.canTake = canTake;
	}
}
