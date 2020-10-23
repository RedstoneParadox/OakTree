package io.github.redstoneparadox.oaktree;

import io.github.redstoneparadox.oaktree.networking.OakTreeNetworking;
import net.fabricmc.api.ModInitializer;

public class OakTree implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Loading Oak Tree GUI Toolkit...");
		OakTreeNetworking.initPackets();
	}
}
