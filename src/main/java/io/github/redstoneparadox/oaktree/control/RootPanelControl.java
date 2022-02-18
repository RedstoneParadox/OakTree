package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.painter.Theme;
import io.github.redstoneparadox.oaktree.util.RenderHelper;
import io.github.redstoneparadox.oaktree.util.ZIndexedControls;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

/**
 * A special {@link PanelControl} that manages an
 * entire tree of {@link Control} instances.
 * Note that mouse interaction with this control
 * is disabled.
 */
public class RootPanelControl extends PanelControl {
	protected Theme theme;
	private boolean dirty = true;
	private final ZIndexedControls zIndexedControls = new ZIndexedControls();

	public RootPanelControl() {
		theme = Theme.vanilla();
	}

	/**
	 * Sets the theme for the entire GUI
	 *
	 * @param theme The theme to use
	 */
	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	/**
	 * Gets the current theme.
	 *
	 * @return The current theme.
	 */
	public Theme getTheme() {
		return theme.copy();
	}

	/**
	 * Renders this control and the entire {@link Control}
	 * tree.
	 *
	 * @param matrixStack The matrix stack
	 * @param mouseX The mouse x position
	 * @param mouseY The mouse y position
	 * @param deltaTime The time since the last frame
	 */
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float deltaTime) {
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

		draw(matrixStack, theme);
		for (ZIndexedControls.Entry entry: zIndexedControls) {
			RenderHelper.setzOffset(entry.zOffset());
			entry.control().draw(matrixStack, theme);
			RenderHelper.setzOffset(-entry.zOffset());
		}
	}

	@Override
	protected void markDirty() {
		this.dirty = true;
	}
}
