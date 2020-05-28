package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;

@Deprecated
public class OakTreeHandledScreen<T extends ScreenHandler> extends HandledScreen<T> {
    private final ControlGui gui;

    private boolean isPauseScreen;
    private Screen parentScreen;

    public OakTreeHandledScreen(Control<?> root, boolean isPauseScreen, Screen parentScreen, Theme theme, T container, PlayerInventory playerInventory, Text text) {
        super(container, playerInventory, text);
        this.isPauseScreen = isPauseScreen;
        this.parentScreen = parentScreen;

        this.gui = new ControlGui(this, root);
        this.gui.applyTheme(theme);
    }

    @Override
    public boolean isPauseScreen() {
        return isPauseScreen;
    }

    @Override
    protected void drawBackground(MatrixStack matrixStack, float f, int i, int j) {

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        gui.draw(matrices, mouseX, mouseY, delta);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        return !((mouseX > x && mouseY > y) && (mouseX < x + width && mouseY < y + height));
    }

    @Override
    public void onClose() {
        client.openScreen(parentScreen);
    }
}
