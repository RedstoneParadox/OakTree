package net.redstoneparadox.oaktree.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.client.util.Window;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.redstoneparadox.oaktree.client.gui.nodes.Node;

import java.util.Optional;

public class OakTreeContainerScreen<T extends Container> extends AbstractContainerScreen<T> implements OakTreeGUI {

    private Node root;

    private boolean isPauseScreen;

    private boolean leftMouseButton;
    private boolean leftMouseJustPressed;

    private Character lastChar = null;

    Screen parentScreen;

    public OakTreeContainerScreen(Node root, boolean isPauseScreen, Screen parentScreen, T container, PlayerInventory playerInventory, Text text) {
        super(container, playerInventory, text);
        this.root = root;
        this.isPauseScreen = isPauseScreen;
        this.parentScreen = parentScreen;
    }

    @Override
    public boolean isPauseScreen() {
        return isPauseScreen;
    }

    @Override
    protected void drawBackground(float var1, int var2, int var3) {

    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        super.render(int_1, int_2, float_1);

        Window clientWindow  = MinecraftClient.getInstance().window;

        root.preDraw(int_1, int_2, float_1, this, 0, 0, clientWindow.getScaledWidth(), clientWindow.getScaledHeight());
        root.draw(int_1, int_2, float_1, this);
        leftMouseJustPressed = false;
        lastChar = null;

        width = (int)root.trueWidth;
        height = (int)root.trueHeight;

        left = (int)root.trueX;
        top = (int)root.trueY;
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
    public Optional<Container> getScreenContainer() {
        return Optional.of(container);
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
    protected boolean isClickOutsideBounds(double double_1, double double_2, int int_1, int int_2, int int_3) {
        return false;
    }

    @Override
    public void onClose() {
        minecraft.openScreen(parentScreen);
    }
}
