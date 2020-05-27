package io.github.redstoneparadox.oaktree.mixin.client.gui.screen;

import io.github.redstoneparadox.oaktree.hooks.ScreenHooks;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


import java.util.Optional;

@Mixin(Screen.class)
public abstract class MixinScreen implements ScreenHooks {
    @Shadow protected net.minecraft.client.font.TextRenderer textRenderer;

    @Override
    public Optional<ScreenHandler> getHandler() {
        return Optional.empty();
    }

    @Override
    public TextRenderer getTextRenderer() {
        return textRenderer;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }
}
