package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.control.Control;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenRect;
import io.github.redstoneparadox.oaktree.hooks.KeyboardHooks;
import io.github.redstoneparadox.oaktree.hooks.MouseHooks;
import io.github.redstoneparadox.oaktree.hooks.ScreenHooks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;

import java.util.*;

public final class ControlGui {
    private Map<ScreenRect, Control<?>> areaMap = new HashMap<>();
    private List<ScreenRect> areas = new ArrayList<>();

    private final ScreenHooks screen;
    private final Control<?> root;

    private boolean leftMouseClicked = false;
    private boolean leftMouseHeld = false;

    private boolean rightMouseClicked = false;
    private boolean rightMouseHeld = false;

    private Character lastChar = null;

    private Theme theme = null;

    public ControlGui(Screen screen, Control<?> root) {
        this.screen = (ScreenHooks)screen;
        this.root = root;

        ((KeyboardHooks)this.screen.getClient().keyboard).onCharTyped(character -> this.lastChar = character);
    }

    public void close() {


        areaMap.clear();
        areas.clear();
    }

    public void draw(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Window window = screen.getClient().getWindow();
        MouseHooks mouse = (MouseHooks) screen.getClient().mouse;

        if (mouse.leftButton()) System.out.println("Left mouse button down.");
        if (mouse.rightButton()) System.out.println("Right mouse button down.");

        if (mouse.leftButton()) {
            if (leftMouseClicked) {
                leftMouseClicked = false;
            }
            else {
                leftMouseClicked = true;
                leftMouseHeld = true;
            }
        }
        else {
            leftMouseClicked = false;
            leftMouseHeld = false;
        }

        if (mouse.rightButton()) {
            if (rightMouseClicked) {
                rightMouseClicked = false;
            }
            else {
                rightMouseClicked = true;
                rightMouseHeld = true;
            }
        }
        else {
            rightMouseClicked = false;
            rightMouseHeld = false;
        }

        root.preDraw(mouseX, mouseY, delta, this, 0, 0, window.getScaledWidth(), window.getScaledHeight());
        root.draw(matrices, mouseX, mouseY, delta, this);

        screen.setSize(root.area.width, root.area.height);

        lastChar = null;
    }

    public Optional<ScreenHandler> getScreenContainer() {
        return screen.getHandler();
    }

    public boolean mouseButtonHeld(String mouseButton) {
        switch (mouseButton) {
            case "left":
                return leftMouseHeld;
            case "right":
                return rightMouseHeld;
            default:
                return false;
        }
    }

    public boolean mouseButtonJustClicked(String mouseButton) {
        switch (mouseButton) {
            case "left":
                return leftMouseClicked;
            case "right":
                return rightMouseClicked;
            default:
                return false;
        }
    }

    public Optional<Character> getLastChar() {
        return lastChar == null ? Optional.empty() : Optional.of(lastChar);
    }

    public TextRenderer getTextRenderer() {
        return screen.getTextRenderer();
    }

    public int getX() {
        return screen.getX();
    }

    public int getY() {
        return screen.getY();
    }

    public Theme getTheme() {
        return theme;
    }

    public void applyTheme(Theme theme) {
        this.theme = theme;
        root.setup(screen.getClient(), this);
    }
}
