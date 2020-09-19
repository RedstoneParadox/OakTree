package io.github.redstoneparadox.oaktree;

import io.github.redstoneparadox.oaktree.networking.OakTreeNetworking;
import io.github.redstoneparadox.oaktree.test.Tests;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

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
