package io.github.redstoneparadox.oaktree.mixin.client.gui.screen.ingame;

import io.github.redstoneparadox.oaktree.hooks.ScreenHooks;
import io.github.redstoneparadox.oaktree.util.BackingSlot;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
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

	@Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
	private void drawSlot(MatrixStack matrixStack, Slot slot, CallbackInfo ci) {
		if (slot instanceof BackingSlot) {
			ci.cancel();
		}
	}
}
