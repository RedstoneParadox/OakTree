package io.github.redstoneparadox.oaktree.mixin.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.redstoneparadox.oaktree.hooks.ScreenHooks;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
    public int getX() { return x; }

    @Override
    public int getY() {
        return y;
    }
}
