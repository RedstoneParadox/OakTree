package io.github.redstoneparadox.oaktree.mixin.client;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.hooks.KeyboardHooks;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

@Mixin(Keyboard.class)
public abstract class MixinKeyboard implements KeyboardHooks {
    private Collection<Consumer<Character>> charTypedListeners = new ArrayList<>();

    @Inject(method = "onChar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;wrapScreenError(Ljava/lang/Runnable;Ljava/lang/String;Ljava/lang/String;)V"))
    private void onChar(long window, int i, int j, CallbackInfo ci) {
        for (Consumer<Character> listener: charTypedListeners) {
            listener.accept((char)i);
        }
    }

    @Override
    public void onCharTyped(Consumer<Character> onCharTyped) {
        charTypedListeners.add(onCharTyped);
    }
}
