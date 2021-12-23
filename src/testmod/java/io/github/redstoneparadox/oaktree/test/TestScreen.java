package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.style.Theme;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

class TestScreen extends Screen {
	private final ControlGui gui;

	protected TestScreen(Text title, boolean vanilla, Control control) {
		super(title);
		this.gui = new ControlGui(this, control);
		if (vanilla) this.gui.applyTheme(Theme.vanilla());
	}

	@Override
	public void init() {
		gui.init();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		gui.draw(matrices, mouseX, mouseY, delta);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
