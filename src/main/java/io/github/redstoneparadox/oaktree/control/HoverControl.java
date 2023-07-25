package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.util.Action;
import org.jetbrains.annotations.NotNull;

/**
 * A Control that can be used to run functions
 * while the cursor is hovered over it and/or
 * when it enters or exits the control area.
 */
public class HoverControl extends Control {
	public static final PainterKey HOVERED = new PainterKey();

	protected @NotNull Action onMouseEnter = () -> {};
	protected @NotNull Action mouseExit = () -> {};
	protected @NotNull Action whileHovered = () -> {};

	private boolean mouseCurrentlyWithin = false;

	public HoverControl() {
		this.name = "hover";
	}

	/**
	 * Sets an {@link Action} to be run when
	 * the cursor enters the control's area.
	 *
	 * @param onMouseEnter The action to run.
	 */
	public void onMouseEnter(@NotNull Action onMouseEnter) {
		this.onMouseEnter = onMouseEnter;
	}

	/**
	 * Sets an {@link Action} to be run when
	 * the cursor exits the control's area
	 *
	 * @param onMouseExit The action to run.
	 */
	public void onMouseExit(@NotNull Action onMouseExit) {
		this.onMouseEnter = onMouseExit;
	}

	/**
	 * Sets an {@link Action} to be run while
	 * the cursor is in the control's area.
	 *
	 * @param whileHovered The action to run.
	 */
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
