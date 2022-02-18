package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.control.RootPanelControl;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

class TestScreen extends Screen {
	private final RootPanelControl root;

	protected TestScreen(Text title, boolean vanilla, RootPanelControl root) {
		super(title);
		this.root = root;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		super.render(matrices, mouseX, mouseY, delta);
		root.render(matrices, mouseX, mouseY, delta);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
