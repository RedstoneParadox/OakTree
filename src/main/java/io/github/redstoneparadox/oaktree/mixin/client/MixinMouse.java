package io.github.redstoneparadox.oaktree.mixin.client;

import io.github.redstoneparadox.oaktree.hooks.MouseHooks;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public abstract class MixinMouse implements MouseHooks {
    private boolean rightButton = false;
    private boolean leftButton = false;

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (action == 1) {
            if (button == 1) rightButton = true;
            if (button == 0) leftButton = true;
        }
        else if (action == 0) {
            if (button == 1) rightButton = false;
            if (button == 0) leftButton = false;
        }
    }

    @Override
    public boolean leftButton() {
        return leftButton;
    }

    @Override
    public boolean rightButton() {
        return rightButton;
    }
}
