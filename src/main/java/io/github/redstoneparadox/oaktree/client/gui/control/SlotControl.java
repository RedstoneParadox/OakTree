package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.style.ColorStyleBox;
import io.github.redstoneparadox.oaktree.client.gui.style.StyleBox;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import io.github.redstoneparadox.oaktree.util.InventoryScreenHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class SlotControl extends InteractiveControl<SlotControl> {
    public StyleBox highlightStyle = new ColorStyleBox(new RGBAColor(0.75f, 0.75f, 0.75f, 0.5f));
    public int slotBorder = 1;

    private final int index;
    private final int inventory;

    public SlotControl(int index, int inventory) {
        this.index = index;
        this.inventory = inventory;
        this.id = "item_slot";
        this.size(18, 18);
    }

    @Override
    public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
        gui.getScreenHandler().ifPresent(handler -> {
            if (handler instanceof InventoryScreenHandler) {
                super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);

                if (isMouseWithin) {
                    if (((InventoryScreenHandler) handler).isCursorEmpty()) {
                        if (gui.mouseButtonJustClicked("left")) ((InventoryScreenHandler) handler).pickupStack(index, inventory, true);
                        else if (gui.mouseButtonJustClicked("right")) ((InventoryScreenHandler) handler).pickupStack(index, inventory, false);
                    }
                    else {
                        if (gui.mouseButtonJustClicked("left")) ((InventoryScreenHandler) handler).placeStack(index, inventory, true);
                        else if (gui.mouseButtonJustClicked("right")) ((InventoryScreenHandler) handler).placeStack(index, inventory, false);
                    }
                }
            }
        });
    }

    @Override
    public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
        gui.getScreenHandler().ifPresent(screenHandler -> {
            if (screenHandler instanceof InventoryScreenHandler) {
                super.draw(matrices, mouseX, mouseY, deltaTime, gui);
                ItemStack stack = ((InventoryScreenHandler) screenHandler).getStack(index, inventory);
                ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();

                int itemX = (area.width - 16)/2 + trueX;
                int itemY = (area.height - 16)/2 + trueY;

                renderer.renderGuiItemIcon(stack, itemX, itemY);
                TextRenderer textRenderer = gui.getTextRenderer();
                renderer.renderGuiItemOverlay(textRenderer, stack, itemX, itemY);

                if (isMouseWithin) {
                    highlightStyle.draw(trueX + slotBorder, trueY + slotBorder, area.width - (2 * slotBorder), area.height - (2 * slotBorder), gui);
                }
            }
        });
    }
}
