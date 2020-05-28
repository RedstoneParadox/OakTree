package io.github.redstoneparadox.oaktree.mixin.client;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class MixinKeyboard {
    @Inject(method = "onChar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V"))
    private void onChar(long window, int i, int j, CallbackInfo ci) {
        ControlGui.onCharTyped((char)i);
    }
}
