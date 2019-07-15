package net.redstoneparadox.oaktree.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

public interface OakTreeGUI {

    default ScreenVec getWindowSize() {
        if (!(this instanceof Screen)) {
            return new ScreenVec(0.1f, 0.1f);
        }

        Screen self = ((Screen)(Object)this);
        return new ScreenVec(self.width, self.height);
    }
}
