package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.util.Action;
import org.jetbrains.annotations.NotNull;

public class HoverControl extends InteractiveControl {
	public static final PainterKey HOVERED = new PainterKey();

	protected @NotNull Action onMouseEnter = () -> {};
	protected @NotNull Action mouseExit = () -> {};
	protected @NotNull Action whileHovered = () -> {};

	private boolean mouseCurrentlyWithin = false;

	public HoverControl() {
		this.id = "hover";
	}

	public void onMouseEnter(@NotNull Action onMouseEnter) {
		this.onMouseEnter = onMouseEnter;
	}

	public void onMouseExit(@NotNull Action onMouseExit) {
		this.onMouseEnter = onMouseExit;
	}

	public void whileHovered(@NotNull Action whileHovered) {
		this.onMouseEnter = whileHovered;
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		captured = super.interact(mouseX, mouseY, deltaTime, captured);

		if (captured) {
			painterKey = HOVERED;
			if (!mouseCurrentlyWithin) {
				onMouseEnter.run();
				mouseCurrentlyWithin = true;
			}
			whileHovered.run();
		} else {
			painterKey = DEFAULT;
			if (mouseCurrentlyWithin) {
				mouseExit.run();
				mouseCurrentlyWithin = false;
			}
		}

		return captured;
	}
}
