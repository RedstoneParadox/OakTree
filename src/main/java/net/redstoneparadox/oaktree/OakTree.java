package net.redstoneparadox.oaktree;

import net.fabricmc.api.ModInitializer;
import net.redstoneparadox.oaktree.test.Tests;

public class OakTree implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("Loading Oak Tree GUI Toolkit...");

		new Tests().init();
	}
}
