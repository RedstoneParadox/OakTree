package io.github.redstoneparadox.oaktree.mixin.client;

import io.github.redstoneparadox.oaktree.listeners.ClientListeners;
import io.github.redstoneparadox.oaktree.hooks.MouseHooks;
import net.minecraft.client.Mouse;
import org.lwjgl.glfw.GLFW;
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
		if (action == GLFW.GLFW_PRESS) {
			if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && !rightButton) {
				rightButton = true;
			}
			if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && !leftButton) {
				leftButton = true;
			}

			ClientListeners.onMouseButton(button, true, false);
		}
		else if (action == GLFW.GLFW_REPEAT) {
			ClientListeners.onMouseButton(button, false, false);
		}
		else if (action == GLFW.GLFW_RELEASE) {
			if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT && rightButton) {
				rightButton = false;
			}
			if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT && leftButton) {
				leftButton = false;
			}

			ClientListeners.onMouseButton(button, false, true);
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
