package io.github.redstoneparadox.oaktree;

import io.github.redstoneparadox.oaktree.test.Tests;
import net.fabricmc.api.ModInitializer;

public class OakTreeTest implements ModInitializer {
	@Override
	public void onInitialize() {
		Tests.init();
	}
}
