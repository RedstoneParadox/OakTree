package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.painter.Theme;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import io.github.redstoneparadox.oaktree.util.ZIndexedControls;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class RootPanelControl extends PanelControl {
	protected Theme theme;
	private boolean dirty = true;
	private final ZIndexedControls zIndexedControls = new ZIndexedControls();

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
			Control control = zIndexedControls.get(i).control();

			if (!captured) {
				captured = control.interact(mouseX, mouseY, deltaTime, false);
			} else {
				control.interact(mouseX, mouseY, deltaTime, true);
			}
		}

		prepare();
		for (ZIndexedControls.Entry entry: zIndexedControls) {
			entry.control().prepare();
		}

		draw(matrices, theme);
		for (ZIndexedControls.Entry entry: zIndexedControls) {
			RenderHelper.setzOffset(entry.zOffset());
			entry.control().draw(matrices, theme);
			RenderHelper.setzOffset(-entry.zOffset());
		}
	}
}
