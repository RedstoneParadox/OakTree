package io.github.redstoneparadox.oaktree.mixin.container;

import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor mixin for changing slot x and y positions.
 * Thanks Emi!
 */
@Mixin(Slot.class)
public interface SlotAccessor {

    @Accessor("x")
    public abstract int getX();

    @Accessor("x")
    public abstract void setX(int xPosition);

    @Accessor("y")
    public abstract int getY();

    @Accessor("y")
    public abstract void setY(int yPosition);
}
