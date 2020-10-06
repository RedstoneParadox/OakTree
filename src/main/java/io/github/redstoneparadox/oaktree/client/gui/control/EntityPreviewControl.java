package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class EntityPreviewControl extends Control<EntityPreviewControl> {
	LivingEntity entity;
	int entitySize;
	
	public EntityPreviewControl(LivingEntity entity, int entitySize) {
		this.entity = entity;
		this.entitySize = entitySize;
	}
	
	public EntityPreviewControl entity(LivingEntity entity) {
		this.entity = entity;
		return this;
	}
	
	public EntityPreviewControl entitySize(int size) {
		this.entitySize = size;
		return this;
	}
	
	@Override
	public void draw(MatrixStack matrices, int mouseX, int mouseY, float deltaTime, ControlGui gui) {
		super.draw(matrices, mouseX, mouseY, deltaTime, gui);
		InventoryScreen.drawEntity(this.trueX, this.trueY, this.entitySize, mouseX, mouseY, this.entity);
	}
}
