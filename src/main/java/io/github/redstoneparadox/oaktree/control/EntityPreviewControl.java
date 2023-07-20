package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.painter.Theme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;

/**
 * A {@link Control} that displays a {@link LivingEntity}.
 */
// TODO: Clean up.
public class EntityPreviewControl extends Control {
	LivingEntity entity;
	int entitySize;
	boolean followCursor = true;
	int mouseX = 0;
	int mouseY = 0;
	
	/**
	 * @param entity the entity to be displayed.
	 * @param entitySize the size of the entity on-screen (ex: survival inventory uses 30).
	 */
	public EntityPreviewControl(LivingEntity entity, int entitySize) {
		this.entity = entity;
		this.entitySize = entitySize;
	}
	
	/**
	 * @param entity the entity to be displayed.
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
	 * @param flag Whether the displayed entity should look at the cursor on-screen.
	 */
	public void setFollowCursor(boolean flag) {
		this.followCursor = flag;
	}

	@Override
	protected boolean interact(int mouseX, int mouseY, float deltaTime, boolean captured) {
		if (followCursor) {
			this.mouseX = mouseX;
			this.mouseY = mouseY;
		} else {
			this.mouseX = 0;
			this.mouseY = 0;
		}
		return super.interact(mouseX, mouseY, deltaTime, captured);
	}

	@Override
	protected void draw(GuiGraphics graphics, Theme theme) {
		super.draw(graphics, theme);
		InventoryScreen.drawEntity(graphics, this.trueArea.getX(), this.trueArea.getY(), this.entitySize,
				followCursor ? mouseX : 0f, followCursor ? mouseY : 0f, this.entity);
	}
}
