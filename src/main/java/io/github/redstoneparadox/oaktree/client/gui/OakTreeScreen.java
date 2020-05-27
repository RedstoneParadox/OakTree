package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.Key;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;

import java.util.Optional;

public class OakTreeScreen extends Screen implements OakTreeGUI {
    private Control root;
    private boolean isPauseScreen;
    private Character lastChar = null;
    private Screen parentScreen;
    private Theme theme;

    private boolean leftMouseButton;
    private boolean leftMouseJustPressed;

    private Key pressed = Key.NONE;

    public OakTreeScreen(Control root, boolean isPauseScreen, Screen parentScreen, Theme theme) {
        super(new LiteralText("gui"));
        this.root = root;
        this.isPauseScreen = isPauseScreen;
        this.parentScreen = parentScreen;
        this.theme = theme;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        pressed = Key.fromKeycode(keyCode);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return isPauseScreen;
    }

    @Override
    public void init(MinecraftClient client, int i, int j) {
        super.init(client, i, j);
        root.setup(client, this);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        Window clientWindow  = MinecraftClient.getInstance().getWindow();

        root.preDraw(mouseX, mouseY, delta, this, 0, 0, clientWindow.getScaledWidth(), clientWindow.getScaledHeight());
        root.draw(matrices, mouseX, mouseY, delta, this);

        leftMouseJustPressed = false;
        lastChar = null;
        pressed = Key.NONE;

        width = (int)root.width;
        height = (int)root.height;
    }

    @Override
    public Optional<ScreenHandler> getScreenContainer() {
        return Optional.empty();
    }

    @Override
    public boolean mouseButtonHeld(String mouseButton) {
        switch (mouseButton) {
            case "left":
                return leftMouseButton;
            case "right":
                return false;
            default:
                return false;
        }
    }

    @Override
    public boolean mouseButtonJustClicked(String mouseButton) {
        switch (mouseButton) {
            case "left":
                return leftMouseJustPressed;
            case "right":
                return false;
            default:
                return false;
        }
    }

    @Override
    public Optional<Character> getLastChar() {
        if (lastChar != null) {
            return Optional.of(lastChar);
        }

        return Optional.empty();
    }

    @Override
    public TextRenderer getTextRenderer() {
        return this.textRenderer;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    @Override
    public void applyTheme(Theme theme) {
        this.theme = theme;
        root.setup(MinecraftClient.getInstance(), this);
    }

    @Override
    public boolean mouseClicked(double double_1, double double_2, int int_1) {
        super.mouseClicked(double_1, double_2, int_1);

        if (int_1 == 0) {
            leftMouseButton = true;
            leftMouseJustPressed = true;
        }

        return false;
    }

    @Override
    public boolean mouseReleased(double double_1, double double_2, int int_1) {
        super.mouseReleased(double_1, double_2, int_1);

        if (int_1 == 0) {
            leftMouseButton = false;
            leftMouseJustPressed = false;
        }

        return true;
    }

    @Override
    public boolean charTyped(char char_1, int int_1) {
        lastChar = char_1;
        return true;
    }

    @Override
    public void onClose() {
        client.openScreen(parentScreen);
    }
}
