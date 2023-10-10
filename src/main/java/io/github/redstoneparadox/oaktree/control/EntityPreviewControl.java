package io.github.redstoneparadox.oaktree.control;

import io.github.redstoneparadox.oaktree.painter.Theme;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/**
 * <p>A {@link Control} that displays a {@link LivingEntity} just
 * like in the creative, survival, and horse inventory screens.</p>
 *
 * <p>By default, the entity will look towards the cursor, but this
 * behavior can be disabled.</p>
 */
public class EntityPreviewControl extends Control {
	protected @NotNull LivingEntity entity;
	protected int entitySize = 30;
	protected float yOffset = 0.0625f;
	protected boolean followCursor = true;
	protected int mouseX = 0;
	protected int mouseY = 0;
	
	/**
	 * @param entity the {@link LivingEntity} to be displayed.
	 * @param entitySize the size of the entity on-screen (ex: survival inventory uses 30).
	 */
	public EntityPreviewControl(@NotNull LivingEntity entity, int entitySize) {
		this.entity = entity;
		this.entitySize = entitySize;
	}

	public EntityPreviewControl(@NotNull LivingEntity entity) {
		this.entity = entity;
	}
	
	/**
	 * Sets the {@link LivingEntity} to be displayed on screen.
	 *
	 * @param entity the entity to be displayed.
	 */
	public void setEntity(@NotNull LivingEntity entity) {
		this.entity = entity;
	}

	public @NotNull LivingEntity getEntity() {
		return entity;
	}

	/**
	 * Sets the size of the entity on-screen
	 * (ex: survival inventory uses 30 for the player).
	 *
	 * @param size the size of the entity on-screen
	 */
	public void setEntitySize(int size) {
		this.entitySize = size;
	}

	public int getEntitySize() {
		return entitySize;
	}

	/**
	 * Appears to set the yOffset of the entity on screen.
	 *
	 * @param yOffset The yOffset of the entity.
	 */
	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	/**
	 * Sets Whether the displayed entity should look at the
	 * cursor on-screen. ('true' by default)
	 *
	 * @param flag Whether the displayed entity should look at the cursor.
	 */
	public void setFollowCursor(boolean flag) {
		this.followCursor = flag;
	}

	public boolean isFollowCursor() {
		return followCursor;
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
		int x = trueArea.getX();
		int y = trueArea.getY();
		int width = trueArea.getWidth();
		int height = trueArea.getHeight();

		InventoryScreen.drawEntity(
				graphics,
				x,
				y,
				x + width,
				y + height,
				this.entitySize,
				yOffset, // Not actually lookX, despite what the mappings state
				followCursor ? (-mouseX + this.trueArea.getX()) : 0f,
				followCursor ? (-mouseY + this.trueArea.getY()) : 0f, 
				this.entity
		);
	}
}
