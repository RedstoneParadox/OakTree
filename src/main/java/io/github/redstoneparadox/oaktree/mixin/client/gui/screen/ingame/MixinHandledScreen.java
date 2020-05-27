package io.github.redstoneparadox.oaktree.mixin.client.gui.screen.ingame;

import io.github.redstoneparadox.oaktree.hooks.ScreenHooks;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen<T extends ScreenHandler> implements ScreenHooks {
    @Shadow @Final protected T handler;

    @Shadow protected int x;
    @Shadow protected int y;

    @Override
    public Optional<ScreenHandler> getHandler() {
        return Optional.of(handler);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
