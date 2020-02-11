package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;

import java.util.Optional;

public class OakTreeContainerScreen<T extends Container> extends ContainerScreen<T> implements OakTreeGUI {

    private Control root;
    private boolean isPauseScreen;
    private Screen parentScreen;
    private Theme theme;

    private boolean leftMouseButton;
    private boolean leftMouseJustPressed;

    private Character lastChar = null;

    private boolean closeOnInv = true;

    private boolean backspace = false;
    private boolean enter = false;
    private boolean ctrlA = false;
    private boolean copy = false;
    private boolean cut = false;
    private boolean paste = false;

    public OakTreeContainerScreen(Control root, boolean isPauseScreen, Screen parentScreen, Theme theme, T container, PlayerInventory playerInventory, Text text) {
        super(container, playerInventory, text);
        this.root = root;
        this.isPauseScreen = isPauseScreen;
        this.parentScreen = parentScreen;
        this.theme = theme;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 259) backspace = true;
        if (keyCode == 257) enter = true;
        if (hasControlDown() && keyCode == 65) ctrlA = true;
        if (isCopy(keyCode)) copy = true;
        if (isCut(keyCode)) cut = true;
        if (isPaste(keyCode)) paste = true;

        if (this.minecraft != null && this.minecraft.options.keyInventory.matchesKey(keyCode, scanCode) && keyCode != 256) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return isPauseScreen;
    }

    @Override
    public void init(MinecraftClient minecraftClient_1, int int_1, int int_2) {
        super.init(minecraftClient_1, int_1, int_2);
        root.setup(minecraftClient_1, this);
    }

    @Override
    protected void drawBackground(float var1, int var2, int var3) {

    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);

        Window clientWindow  = MinecraftClient.getInstance().getWindow();

        root.preDraw(mouseX, mouseY, delta, this, 0, 0, clientWindow.getScaledWidth(), clientWindow.getScaledHeight());
        root.draw(mouseX, mouseY, delta, this);

        leftMouseJustPressed = false;
        lastChar = null;
        backspace = false;
        enter = false;
        ctrlA = false;
        copy = false;
        cut = false;
        paste = false;

        width = (int)root.width;
        height = (int)root.height;
    }

    @Override
    public Optional<Container> getScreenContainer() {
        return Optional.of(container);
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
    public boolean isKeyPressed(String key) {
        switch (key) {
            case "enter":
                return enter;
            case "backspace":
                return backspace;
            case "ctrl_a":
                return ctrlA;
            case "cut":
                return cut;
            case "copy":
                return copy;
            case "paste":
                return paste;
            default:
                return false;
        }
    }

    @Override
    public TextRenderer getTextRenderer() {
        return this.font;
    }

    @Override
    public boolean isBackspaceHeld() {
        return InputUtil.isKeyPressed(minecraft.getWindow().getHandle(), 259);
    }

    @Override
    public void shouldCloseOnInventoryKey(boolean value) {
        closeOnInv = value;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
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
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        return !((mouseX > x && mouseY > y) && (mouseX < x + width && mouseY < y + height));
    }

    @Override
    public void onClose() {
        minecraft.openScreen(parentScreen);
    }
}
