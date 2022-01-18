package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.style.ControlStyle;
import io.github.redstoneparadox.oaktree.style.Theme;
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

	public @NotNull ControlStyle getStyle(String styleName) {
		return theme.get(styleName);
	}

	public void render(MatrixStack matrices, int mouseX, int mouseY, float deltaTime) {
		if (dirty) {
			MinecraftClient client = MinecraftClient.getInstance();
			Window window = client.getWindow();

			zIndexedControls.clear();
			root.updateTree(zIndexedControls, 0, 0, window.getWidth(), window.getHeight());
			dirty = false;
		}

		for (Control control: zIndexedControls) {
			if (control.interact(mouseX, mouseY, deltaTime)) {
				break;
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
		this.dirty = true
	}
}
