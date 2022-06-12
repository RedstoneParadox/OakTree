package io.github.redstoneparadox.oaktree.test;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class OakTreeTestClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		TestScreens.init();
	}
}
