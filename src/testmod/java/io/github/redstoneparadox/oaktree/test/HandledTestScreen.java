package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.control.RootPanelControl;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

class HandledTestScreen extends HandledScreen<TestScreenHandler> {
	private final RootPanelControl root;

	public HandledTestScreen(TestScreenHandler handler, Text title, boolean vanilla, RootPanelControl root) {
		super(handler, handler.getPlayer().getInventory(), title);
		this.root = root;
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		root.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public boolean isPauseScreen() {
		return super.isPauseScreen();
	}

	@Override
	public void onClose() {
		super.onClose();
		handler.close(handler.getPlayer());
	}
}
