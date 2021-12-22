package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.style.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

class HandledTestScreen extends HandledScreen<TestScreenHandler> {
	private final ControlGui gui;

	public HandledTestScreen(TestScreenHandler handler, Text title, boolean vanilla, Control control) {
		super(handler, handler.getPlayer().getInventory(), title);
		this.gui = new ControlGui(this, control);
		if (vanilla) this.gui.applyTheme(Theme.vanilla());
	}

	@Override
	public void init() {
		gui.init();
	}

	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {

	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		gui.draw(matrices, mouseX, mouseY, delta);
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
