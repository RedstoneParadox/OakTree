package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.listeners.MouseButtonListener;
import io.github.redstoneparadox.oaktree.painter.Theme;
import io.github.redstoneparadox.oaktree.util.BackingSlot;
import io.github.redstoneparadox.oaktree.util.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * <p>A {@link Control} that can be used for interacting
 * with items in an inventory. Requires a
 * {@link BackingSlot} to use.</p>
 */
public class SlotControl extends Control implements MouseButtonListener {
	protected @NotNull Color highlightColor = Color.rgba(0.9f, 0.9f, 0.9f, 0.5f);
	protected int slotBorder = 1;

	private final PlayerEntity player;
	private final BackingSlot slot;
	private boolean leftJustClicked = false;
	private boolean rightJustClicked = false;
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
		tooltip.setIsTooltip(true);
		tooltip.setMaxDisplayedLines(999);

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

	@Override
	protected void updateTree(RootPanelControl.ZIndexedControls zIndexedControls, int containerX, int containerY, int containerWidth, int containerHeight) {
		super.updateTree(zIndexedControls, containerX, containerY, containerWidth, containerHeight);
		slot.x = trueArea.getX() + (trueArea.getWidth() - 18)/2;
		slot.y = trueArea.getY() + (trueArea.getHeight() - 18)/2;
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		captured = super.interact(mouseX, mouseY, deltaTime, captured);

		if (captured) {
			highlighted = true;
			ScreenHandler handler = player.currentScreenHandler;
			ItemStack slotStack = slot.getStack();
			MinecraftClient client = MinecraftClient.getInstance();

			assert client.interactionManager != null;

			if (leftJustClicked) {
				client.interactionManager.clickSlot(handler.syncId, slot.id, GLFW.GLFW_MOUSE_BUTTON_LEFT, SlotActionType.PICKUP, client.player);

				slot.markDirty();
			} else if (rightJustClicked) {
				client.interactionManager.clickSlot(handler.syncId, slot.id, GLFW.GLFW_MOUSE_BUTTON_RIGHT, SlotActionType.PICKUP, client.player);

				slot.markDirty();
			}

			if (tooltip != null && tooltip instanceof LabelControl) {
				if (!slotStack.isEmpty()) {
					List<Text> texts = slotStack.getTooltip(player, TooltipContext.Default.HIDE_ADVANCED_DETAILS);
					((LabelControl) tooltip).setText(texts);
					tooltip.setVisible(true);
				}
				else {
					((LabelControl) tooltip).clearText();
					tooltip.setVisible(false);
				}
			}
		} else {
			if (tooltip != null) tooltip.setVisible(false);
			highlighted = false;
		}

		leftJustClicked = false;
		rightJustClicked = false;

		return captured;
	}

	@Override
	protected void draw(GuiGraphics graphics, Theme theme) {
		super.draw(graphics, matrices, theme);

		int x = trueArea.getX();
		int y = trueArea.getY();

		ItemStack stack = slot.getStack();
		graphics.drawItem(stack, x + trueArea.getWidth()/2, y + trueArea.getHeight()/2);

		if (highlighted) {
			int highlightX = x + slotBorder;
			int highlightY = y + slotBorder;
			int highlightWidth = trueArea.getWidth() - (2 * slotBorder);
			int highlightHeight = trueArea.getHeight() - (2 * slotBorder);
			int z = 0;

			if (!stack.isEmpty()) z = 1;

			graphics.fill(highlightX, highlightY, highlightX + highlightWidth, highlightY + highlightHeight, z, highlightColor.toInt());
		}
	}

	@Override
	protected void cleanup() {
		super.cleanup();
		ClientListeners.MOUSE_BUTTON_LISTENERS.remove(this);
	}

	@Override
	public void onMouseButton(int button, boolean justPressed, boolean released) {
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && justPressed) {
			leftJustClicked = true;
		}
		else if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && justPressed) {
			rightJustClicked = true;
		}
	}
}
