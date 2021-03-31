package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.ControlGui;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

/**
 * A {@link Control} that displays a {@link LivingEntity}.
 */
// TODO: Clean up.
public class EntityPreviewControl extends Control {
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
	 */
	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}
	
	/**
	 * @param size the size of the entity on-screen (ex: survival inventory uses 30)
	 */
	public void setEntitySize(int size) {
		this.entitySize = size;
	}
	
	/**
	 * @param flag Whether the displayed entity should look at the cursor on-screen
	 */
	public void setFollowCursor(boolean flag) {
		this.followCursor = flag;
	}
	
	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);
		InventoryScreen.drawEntity(this.trueX, this.trueY, this.entitySize,
				followCursor ? mouseX : 0, followCursor ? mouseY : 0, this.entity);
	}
}
