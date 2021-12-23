package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.style.ControlStyle;
import io.github.redstoneparadox.oaktree.style.Theme;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ControlTree {
	private final Control root;
	private Theme theme = Theme.vanilla();
	private boolean dirty = true;
	private List<Control> zIndexedControls = new ArrayList<>();

	public ControlTree(Control root) {
		this.root = root;
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
}
