package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.control.Control;
import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenRect;
import io.github.redstoneparadox.oaktree.hooks.ScreenHooks;
import net.minecraft.client.Mouse;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;

import java.util.*;

@SuppressWarnings("deprecation")
public final class ControlGui implements OakTreeGUI {
    private static final Collection<ControlGui> GUIS = new ArrayList<>();

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

        GUIS.add(this);
    }

    public void close() {
        GUIS.remove(this);

        areaMap.clear();
        areas.clear();
    }

    public void draw(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Window window = screen.getClient().getWindow();
        Mouse mouse = screen.getClient().mouse;

        if (mouse.wasLeftButtonClicked()) System.out.println("Left mouse button down.");
        if (mouse.wasRightButtonClicked()) System.out.println("Right mouse button down.");

        if (mouse.wasLeftButtonClicked()) {
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

        if (mouse.wasRightButtonClicked()) {
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

        screen.setSize((int)root.width, (int)root.height);
    }

    @Override
    public Optional<ScreenHandler> getScreenContainer() {
        return screen.getHandler();
    }

    @Override
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

    @Override
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

    @Override
    public Optional<Character> getLastChar() {
        return lastChar == null ? Optional.empty() : Optional.of(lastChar);
    }

    @Override
    public TextRenderer getTextRenderer() {
        return screen.getTextRenderer();
    }

    @Override
    public int getX() {
        return screen.getX();
    }

    @Override
    public int getY() {
        return screen.getY();
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    @Override
    public void applyTheme(Theme theme) {
        this.theme = theme;
        root.setup(screen.getClient(), this);
    }

    public static void onCharTyped(char c) {
        for (ControlGui gui: GUIS) {
            gui.lastChar = c;
        }
    }
}
