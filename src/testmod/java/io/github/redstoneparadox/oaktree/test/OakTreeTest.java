package io.github.redstoneparadox.oaktree.test;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class OakTreeTest implements ModInitializer {
	@Override
	public void onInitialize(ModContainer mod) {
		TestBlocks.init();
		TestBlockEntities.init();
	}
}
