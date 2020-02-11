package io.github.redstoneparadox.oaktree.client.gui;

import io.github.redstoneparadox.oaktree.client.gui.style.Theme;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.container.Container;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;

/**
 * Created by RedstoneParadox on 11/27/2019.
 *
 * Used for building new screens.
 */
public class ScreenBuilder {
    private boolean pause = false;
    private Screen parent = null;
    private Control<?> root;
    private Theme theme = null;
    private Container container;
    private PlayerInventory playerInventory;
    private Text text = new LiteralText("");

    public ScreenBuilder(Control<?> root) {
        this.root = root;
    }

    /**
     * Sets whether or not the built screen pauses
     * the game when opened in single-player.
     *
     * @param pause the value to set.
     * @return The builder itself.
     */
    public ScreenBuilder shouldPause(boolean pause) {
        this.pause = pause;
        return this;
    }

    /**
     * Sets the parent {@link Screen} to open when
     * the built screen is closed. For example, if
     * this screen is opened from the main menu,
     * the parent screen would be the main menu
     * screen.
     *
     * @param parent The parent screen.
     * @return The builder itself.
     */
    public ScreenBuilder parentScreen(Screen parent) {
        this.parent = parent;
        return this;
    }

    public ScreenBuilder theme(Theme theme) {
        this.theme = theme;
        return this;
    }

    /**
     * If building a contain screen, this sets the
     * backing {@link Container} for the built
     * screen.
     *
     * @param container The container.
     * @return The builder itself.
     */
    public ScreenBuilder container(Container container) {
        this.container = container;
        return this;
    }

    /**
     * If building a container screen, this adds the
     * inventory of the player that opened the
     * container screen.
     *
     * @param playerInventory The player's inventory.
     * @return The builder itself.
     */
    public ScreenBuilder playerInventory(PlayerInventory playerInventory) {
        this.playerInventory = playerInventory;
        return this;
    }

    /**
     *
     * @param text The value to set.
     * @return The builder itself.
     */
    public ScreenBuilder text(Text text) {
        this.text = text;
        return this;
    }

    /**
     * Builds a new {@link Screen}.
     *
     * @return A {@link Screen} instance.
     */
    public Screen build() {
        return new OakTreeScreen(root, pause, parent, theme);
    }

    /**
     * Builds a new {@link ContainerScreen}
     *
     * @return An {@link ContainerScreen} instance.
     */
    public <C extends Container> ContainerScreen<C> buildContainerScreen() {
        return new OakTreeContainerScreen<C>(root, pause, parent, (C) container, playerInventory, text);
    }
}
