package io.github.redstoneparadox.oaktree.hooks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.screen.ScreenHandler;

import java.util.Optional;

public interface ScreenHooks {

    Optional<ScreenHandler> getHandler();

    TextRenderer getTextRenderer();

    MinecraftClient getClient();

    int getX();

    int getY();

    void setSize(int width, int height);
}
