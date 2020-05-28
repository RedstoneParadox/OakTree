package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;

@Deprecated
public class OakTreeScreen extends Screen implements OakTreeGUI {
    private final ControlGui gui;

    private boolean isPauseScreen;
    private Screen parentScreen;

    public OakTreeScreen(Control<?> root, boolean isPauseScreen, Screen parentScreen, Theme theme) {
        super(new LiteralText("gui"));
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
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        gui.draw(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        gui.close();
        client.openScreen(parentScreen);
    }
}
