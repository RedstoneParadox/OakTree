package io.github.redstoneparadox.oaktree.client.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class ClientEvents {
	public static Event<CharTypedCallback> ON_CHAR_TYPED = EventFactory.createArrayBacked(CharTypedCallback.class, (listeners -> c -> {
		for (CharTypedCallback listener: listeners) {
			listener.onCharTyped(c);
		}
	}));
}
