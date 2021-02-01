package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

/**
 * A {@link Control} that displays a {@link LivingEntity}.
 */
public class EntityPreviewControl extends Control<EntityPreviewControl> {
	LivingEntity entity;
	int entitySize;
	boolean followCursor = true;
	
	/**
	 * @param entity the entity to be displayed
	 * @param entitySize the size of the entity on-screen (ex: survival inventory uses 30)
	 */
	public EntityPreviewControl(LivingEntity entity, int entitySize) {
		this.entity = entity;
		this.entitySize = entitySize;
	}
	
	/**
	 * @param entity the entity to be displayed
	 * @return the control itself
	 */
	public EntityPreviewControl entity(LivingEntity entity) {
		this.entity = entity;
		return this;
	}
	
	/**
	 * @param size the size of the entity on-screen (ex: survival inventory uses 30)
	 * @return the control itself
	 */
	public EntityPreviewControl entitySize(int size) {
		this.entitySize = size;
		return this;
	}
	
	/**
	 * @param flag Whether the displayed entity should look at the cursor on-screen
	 * @return the control itself
	 */
	public EntityPreviewControl followCursor(boolean flag) {
		this.followCursor = flag;
		return this;
	}
	
	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);
		InventoryScreen.drawEntity(this.trueX, this.trueY, this.entitySize,
				followCursor ? mouseX : 0, followCursor ? mouseY : 0, this.entity);
	}
}
