package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.networking.InventoryScreenHandlerAccess;
import io.github.redstoneparadox.oaktree.painter.Theme;
import io.github.redstoneparadox.oaktree.util.BackingSlot;
import io.github.redstoneparadox.oaktree.util.Color;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import io.github.redstoneparadox.oaktree.util.ZIndexedControls;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.Predicate;

/**
 * <p>A {@link Control} that can be used for interacting
 * with items in an inventory. Rather than delegating to
 * a vanilla {@link net.minecraft.screen.slot.Slot}, it
 * is implemented from scratch and requires your
 * {@link net.minecraft.screen.ScreenHandler} to
 * implement {@link InventoryScreenHandlerAccess} in order to
 * work. Syncing inventory contents is also handled
 * through the same interface so avoid implementing that
 * yourself.</p>
 */
public class SlotControl extends Control implements MouseButtonListener {
	protected @NotNull Color highlightColor = Color.rgba(0.75f, 0.75f, 0.75f, 0.5f);
	protected int slotBorder = 1;
	protected @NotNull Predicate<ItemStack> canInsert = (stack) -> true;
	protected @NotNull Predicate<ItemStack> canTake = (stack) -> true;
	protected boolean locked = false;

	private final PlayerEntity player;
	private final BackingSlot slot;
	private boolean leftClicked = false;
	private boolean rightClicked = false;
	private boolean highlighted = false;

	public SlotControl(PlayerEntity player, BackingSlot slot) {
		this.player = player;
		this.slot = slot;
		this.id = "item_slot";

		LabelControl tooltip = new LabelControl();
		tooltip.setId("tooltip");
		tooltip.setText("");
		tooltip.setShadow(true);
		tooltip.setFitText(true);
		tooltip.visible = false;

		this.tooltip = tooltip;
		this.setSize(18, 18);

		ClientListeners.MOUSE_BUTTON_LISTENERS.add(this);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		slot.setEnabled(visible);
	}

	/**
	 * Sets what color should be used to highlight this {@link SlotControl}
	 * when hovering over it in the GUI. Default value is
	 * {@code Color.rgba(0.75f, 0.75f, 0.75f, 0.5f)}.
	 *
	 * @param highlightColor The color to be used.
	 */
	public void setHighlightColor(@NotNull Color highlightColor) {
		this.highlightColor = highlightColor;
	}

	public @NotNull Color getHighlightColor() {
		return highlightColor;
	}

	public void setSlotBorder(int slotBorder) {
		this.slotBorder = slotBorder;
	}

	public int getSlotBorder() {
		return slotBorder;
	}

	public void canInsert(@NotNull Predicate<ItemStack> canInsert) {
		this.canInsert = canInsert;
	}

	/**
	 * Helper method for setting a list of {@link Item }
	 * to match against when inserting into slot.
	 *
	 * @param allow Whether matching an item in the list
	 *              should allow or deny inserting a stack.
	 * @param items The items to allow/deny.
	 * @return The {@link SlotControl} for further modification.
	 */
	public SlotControl filter(boolean allow, Item... items) {
		this.canInsert = ((stack) -> {
			for (Item item: items) {
				if (stack.getItem() == item) return allow;
			}
			return !allow;
		});
		return this;
	}

	/**
	 * Helper method for setting a list of {@link Tag<Item>}
	 * to match against when inserting into slot.
	 *
	 * @param allow Whether matching an item tag in the list
	 *              should allow or deny inserting a stack.
	 * @param tags The item tags to match against.
	 * @return The {@link SlotControl} for further modification.
	 */
	public SlotControl filter(boolean allow, Tag<Item>... tags) {
		this.canInsert = ((stack) -> {
			for (Tag<Item> tag: tags) {
				if (tag.contains(stack.getItem())) return allow;
			}
			return !allow;
		});
		return this;
	}

	public void canTake(@NotNull Predicate<ItemStack> canTake) {
		this.canTake = canTake;
	}

	/**
	 * Used to disable or enable interactions with
	 * this {@link SlotControl}.
	 *
	 * @param locked Whether the slot should be locked.
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocked() {
		return locked;
	}

	@Override
	protected void updateTree(ZIndexedControls zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);
		slot.x = trueArea.getX() + (trueArea.getWidth() - 18)/2;
		slot.y = trueArea.getY() + (trueArea.getHeight() - 18)/2;
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		captured = super.interact(mouseX, mouseY, deltaTime, captured);

		if (captured) {
			highlighted = true;
			boolean stackChanged = false;
			ScreenHandler handler = player.currentScreenHandler;
			ItemStack slotStack = slot.getStack();
			ItemStack cursorStack = handler.getCursorStack();

			if (leftClicked) {
				if (cursorStack.isEmpty() && slot.canTakeItems(player)) {
					handler.setCursorStack(slot.takeStack(slotStack.getCount()));
					stackChanged = true;
				} else if (slot.canInsert(cursorStack)) {
					slot.insertStack(cursorStack);
				}
			} else if (rightClicked) {
				if (cursorStack.isEmpty() && slot.canTakePartial(player)) {
					handler.setCursorStack(slot.takeStack(divide(slotStack.getCount())));
					stackChanged = true;
				} else if (slot.canInsert(cursorStack)) {
					slot.insertStack(cursorStack, 1);
					stackChanged = true;
				}
			}

			//TODO: Find out why I actually need to do this and fix the problem at the source.
			leftClicked = false;
			rightClicked = false;

			if (stackChanged) {
				slot.markDirty();
			}
			if (tooltip instanceof LabelControl) {
				if (!slotStack.isEmpty()) {
					List<Text> texts = slotStack.getTooltip(player, TooltipContext.Default.NORMAL);
					((LabelControl) tooltip).setText(texts);
					tooltip.setVisible(true);
				}
				else {
					((LabelControl) tooltip).clearText();
					tooltip.setVisible(false);
				}
			}

			if (slotStack.isEmpty() && tooltip != null) tooltip.visible = false;

		} else {
			tooltip.setVisible(false);
			highlighted = false;
		}

		return captured;
	}

	@Override
	protected void draw(MatrixStack matrices, Theme theme) {
		super.draw(matrices, theme);

		int x = trueArea.getX();
		int y = trueArea.getY();

		ItemStack stack = slot.getStack();
		RenderHelper.drawItemStackCentered(x, y, trueArea.getWidth(), trueArea.getHeight(), stack);

		if (highlighted) {
			RenderHelper.setzOffset(200.0);
			RenderHelper.drawRectangle(matrices, x + slotBorder, y + slotBorder, trueArea.getWidth() - (2 * slotBorder), trueArea.getHeight() - (2 * slotBorder), highlightColor);
			RenderHelper.setzOffset(0.0);
		}
	}

	private int divide(int num) {
		double result = ((double) num)/((double) 2);
		return (int) (result - Math.floor(result) <= 0.5 ? Math.floor(result) : Math.ceil(result));
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			leftClicked = justPressed;
		}
		else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
			rightClicked = justPressed;
		}
	}
}
