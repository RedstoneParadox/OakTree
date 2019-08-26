package net.redstoneparadox.oaktree;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.redstoneparadox.oaktree.networking.OakTreeNetworking;
import net.redstoneparadox.oaktree.test.Tests;

public class OakTree implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Loading Oak Tree GUI Toolkit...");
		OakTreeNetworking.initPackets();
		if (FabricLoader.getInstance().isDevelopmentEnvironment() && FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			new Tests().init();
		}
	}
}
