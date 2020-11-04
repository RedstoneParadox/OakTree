package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.math.Vector2;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * BoxControl is a type of PaddingControl that
 * can have a single child node.
 *
 * While {@link PanelControl} could also be used,
 * BoxControl is specialized for dealing with a
 * single child.
 */
public class BoxControl extends PaddingControl<BoxControl> {
	protected  @NotNull Control<?> child = new Control<>();

	public BoxControl() {
		this.id = "box";
	}

	/**
	 * Sets the child node of this node. The child node
	 * will be drawn relative to this node within the box
	 * created by the internal margins.
	 *
	 * @param child The node that is being added as a child.
	 */
	public void setChild(@NotNull Control<?> child) {
		this.child = child;
	}

	public @NotNull Control<?> getChild() {
		return child;
	}

	public BoxControl child(@NotNull Control<?> child) {
		this.child = child;
		return this;
	}

	@Override
	public void setup(MinecraftClient client, ControlGui gui) {
		super.setup(client, gui);
		child.setup(client, gui);
	}

	@Override
	public void zIndex(List<Control<?>> controls) {
		if (!visible) return;
		controls.add(this);
		child.zIndex(controls);
	}

	@Override
	public void preDraw(ControlGui gui, int offsetX, int offsetY, int containerWidth, int containerHeight, int mouseX, int mouseY) {
		super.preDraw(gui, offsetX, offsetY, containerWidth, containerHeight, mouseX, mouseY);
		Vector2 innerPosition = innerPosition(trueX, trueY);
		Vector2 innerDimensions = innerDimensions(area.width, area.height);

		if (child.isVisible()) child.preDraw(gui, innerPosition.x, innerPosition.y, innerDimensions.x, innerDimensions.y, mouseX, mouseY);
	}

	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);

		if (child.isVisible()) {
			child.draw(matrices, mouseX, mouseY, deltaTime, gui);
		}
	}
}
