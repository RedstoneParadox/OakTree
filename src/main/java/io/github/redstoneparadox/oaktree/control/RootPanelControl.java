package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.painter.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.List;

public class RootPanelControl extends PanelControl {
	private boolean dirty = true;
	private final List<Control> zIndexedControls = new ArrayList<>();

	public RootPanelControl() {
		theme = Theme.vanilla();
	}

	public Theme getTheme() {
		return theme.copy();
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	@Override
	protected void markDirty() {
		this.dirty = true;
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float deltaTime) {
		if (dirty) {
			MinecraftClient client = MinecraftClient.getInstance();
			Window window = client.getWindow();

			zIndexedControls.clear();
			updateTree(zIndexedControls, 0, 0, window.getWidth(), window.getHeight());
			dirty = false;
		}

		boolean captured = false;
		for (int i = zIndexedControls.size() - 1; i >= 0; i--) {
			Control control = zIndexedControls.get(i);

			if (!captured) {
				captured = control.interact(mouseX, mouseY, deltaTime, false);
			} else {
				control.interact(mouseX, mouseY, deltaTime, true);
			}
		}

		prepare();
		for (Control control: zIndexedControls) {
			control.prepare();
		}

		draw(matrices);
		for (Control control: zIndexedControls) {
			control.draw(matrices);
		}
	}
}
