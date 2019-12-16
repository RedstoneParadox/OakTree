package io.github.redstoneparadox.oaktree.mixin.container;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.container.Slot;

/**
 * Accessor mixin for changing slot x and y positions.
 * Thanks Emi!
 */
@Mixin(Slot.class)
public interface SlotAccessor {

    @Accessor("xPosition")
    public abstract int getXPosition();

    @Accessor("xPosition")
    public abstract void setXPosition(int xPosition);

    @Accessor("yPosition")
    public abstract int getYPosition();

    @Accessor("yPosition")
    public abstract void setYPosition(int yPosition);
}
