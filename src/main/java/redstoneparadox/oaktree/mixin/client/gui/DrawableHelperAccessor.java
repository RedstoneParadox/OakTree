package redstoneparadox.oaktree.mixin.client.gui;

import net.minecraft.client.gui.DrawableHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DrawableHelper.class)
public interface DrawableHelperAccessor {

    @Invoker("blit")
    void invokeBlit(int int_1, int int_2, int int_3, int int_4, int int_5, int int_6);
}
