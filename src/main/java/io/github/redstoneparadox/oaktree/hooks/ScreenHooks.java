package io.github.redstoneparadox.oaktree.hooks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.ScreenHandler;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

@Deprecated
@ApiStatus.ScheduledForRemoval
public interface ScreenHooks {
	Optional<ScreenHandler> getHandler();

	MinecraftClient getClient();

	int getX();

	int getY();

	void setSize(int width, int height);
}
