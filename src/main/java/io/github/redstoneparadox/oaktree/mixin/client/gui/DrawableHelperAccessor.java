package io.github.redstoneparadox.oaktree.mixin.client.gui;

import net.minecraft.client.gui.DrawableHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DrawableHelper.class)
public interface DrawableHelperAccessor {

    @Invoker("blit")
    void invokeBlit(int x, int y, int left, int top, int width, int height);
}
