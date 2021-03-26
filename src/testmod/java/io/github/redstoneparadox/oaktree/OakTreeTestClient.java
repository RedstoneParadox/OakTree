package io.github.redstoneparadox.oaktree;

import io.github.redstoneparadox.oaktree.test.Tests;
import net.fabricmc.api.ClientModInitializer;

public class OakTreeTestClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		Tests.initClient();
	}
}
