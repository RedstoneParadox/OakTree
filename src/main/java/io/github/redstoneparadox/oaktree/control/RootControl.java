package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.painter.Painter;
import io.github.redstoneparadox.oaktree.painter.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class RootControl extends AbstractControl {
	private final Control root;
	private Theme theme = Theme.vanilla();
	private boolean dirty = true;
	private final List<Control> zIndexedControls = new ArrayList<>();

	public RootControl(Control root) {
		this.root = root;
	}

	public Theme getTheme() {
		return theme.copy();
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public @NotNull Painter getStyle(String styleName) {
		return theme.get(styleName);
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float deltaTime) {
		if (dirty) {
			MinecraftClient client = MinecraftClient.getInstance();
			Window window = client.getWindow();

			zIndexedControls.clear();
			if (root.visible) root.updateTree(zIndexedControls, 0, 0, window.getWidth(), window.getHeight());
			dirty = false;
		}

		boolean captured = false;
		for (int i = zIndexedControls.size() - 1; i >= 0; i--) {
			AbstractControl control = zIndexedControls.get(i);

			if (!captured) {
				captured = control.interact(mouseX, mouseY, deltaTime, false);
			} else {
				control.interact(mouseX, mouseY, deltaTime, true);
			}
		}

		for (Control control: zIndexedControls) {
			control.prepare();
		}

		for (Control control: zIndexedControls) {
			control.draw(matrices);
		}
	}

	@Override
	protected void markDirty() {
		this.dirty = true;
	}
}
