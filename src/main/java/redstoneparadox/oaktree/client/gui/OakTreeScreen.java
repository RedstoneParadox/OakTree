package redstoneparadox.oaktree.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.container.Container;
import net.minecraft.text.LiteralText;
import redstoneparadox.oaktree.client.gui.control.Control;

import java.util.Optional;

public class OakTreeScreen extends Screen implements OakTreeGUI {

    Control root;

    boolean isPauseScreen;

    boolean leftMouseButton;
    boolean leftMouseJustPressed;

    Character lastChar = null;

    Screen parentScreen;


    boolean backspace = false;


    public OakTreeScreen(Control root, boolean isPauseScreen, Screen parentScreen) {
        super(new LiteralText("gui"));
        this.root = root;
        this.isPauseScreen = isPauseScreen;
        this.parentScreen = parentScreen;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 259) {
            backspace = true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return isPauseScreen;
    }

    @Override
    public void init(MinecraftClient minecraftClient_1, int int_1, int int_2) {
        super.init(minecraftClient_1, int_1, int_2);
        root.setup(minecraftClient_1, int_1, int_2, this);
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        Window clientWindow  = MinecraftClient.getInstance().getWindow();

        root.preDraw(int_1, int_2, float_1, this, 0, 0, clientWindow.getScaledWidth(), clientWindow.getScaledHeight());
        root.draw(int_1, int_2, float_1, this);

        leftMouseJustPressed = false;
        lastChar = null;
        backspace = false;

        width = (int)root.trueWidth;
        height = (int)root.trueHeight;
    }

    @Override
    public Optional<Container> getScreenContainer() {
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
    public boolean isKeyPressed(String key) {
        switch (key) {
            case "enter":
                return false;
            case "backspace":
                return backspace;
            default:
                return false;
        }
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
        minecraft.openScreen(parentScreen);
    }
}
