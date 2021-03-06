package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.math.Vector2;
import io.github.redstoneparadox.oaktree.style.ControlStyle;
import io.github.redstoneparadox.oaktree.style.Theme;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ControlTree extends ControlElement {
	private final Control root;
	private Theme theme = Theme.vanilla();
	private boolean dirty = true;
	private List<Control> zIndexedControls = new ArrayList<>();

	public ControlTree(Control root) {
		this.root = root;
		this.root.setParent(this);
	}

	public Theme getTheme() {
		return theme.copy();
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public @Nullable ControlStyle getStyle(String styleName) {
		return theme.get(styleName);
	}

	public void render() {
		if (dirty) {
			zIndexedControls.clear();
			root.zIndex(zIndexedControls);
			dirty = false;
		}

		for (Control control: zIndexedControls) {
			// prepare
		}

		for (Control control: zIndexedControls) {
			// draw
		}
	}

	@Override
	public Vector2 getPosition() {
		return new Vector2(0, 0);
	}

	@Override
	protected Vector2 getSize() {
		MinecraftClient client = MinecraftClient.getInstance();
		Window window = client.getWindow();

		return new Vector2(window.getScaledWidth(), window.getScaledHeight());
	}
}
