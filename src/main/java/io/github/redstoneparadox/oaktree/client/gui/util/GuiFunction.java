package io.github.redstoneparadox.oaktree.client.gui.util;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.control.Control;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiConsumer;

@FunctionalInterface
@Deprecated
@ApiStatus.ScheduledForRemoval
public interface GuiFunction<C extends Control<C>> extends BiConsumer<ControlGui, C> {

	@Override
	default void accept(ControlGui gui, C c) {
		this.invoke(gui, c);
	}

	void invoke(ControlGui gui, C control);
}
