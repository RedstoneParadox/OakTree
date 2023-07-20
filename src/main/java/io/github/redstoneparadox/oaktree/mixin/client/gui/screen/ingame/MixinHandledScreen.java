package io.github.redstoneparadox.oaktree.mixin.client.gui.screen.ingame;

import io.github.redstoneparadox.oaktree.util.BackingSlot;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen<T extends ScreenHandler> {
	@Shadow @Final protected T handler;
	@Shadow protected int x;
	@Shadow protected int y;

	@Inject(method = "drawSlot", at = @At("HEAD"), cancellable = true)
	private void drawSlot(GuiGraphics graphics, Slot slot, CallbackInfo ci) {
		if (slot instanceof BackingSlot) {
			ci.cancel();
		}
	}
}
