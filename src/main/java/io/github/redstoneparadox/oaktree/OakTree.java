package io.github.redstoneparadox.oaktree;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class OakTree implements ModInitializer {
	public static String MODID = "oaktree";

	@Override
	public void onInitialize(ModContainer mod) {
		System.out.println("Loading Oak Tree GUI Library...");
	}
}
