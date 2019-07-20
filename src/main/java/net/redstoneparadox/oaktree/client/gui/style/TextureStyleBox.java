package net.redstoneparadox.oaktree.client.gui.style;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public class TextureStyleBox extends StyleBox {

    Identifier textureID;

    int drawLeft = 0;
    int drawTop = 0;

    public TextureStyleBox(String path) {
        textureID = new Identifier(path);
    }

    public TextureStyleBox setDrawOrigin(int left, int top) {
        drawLeft = left;
        drawTop = top;
        return this;
    }

    @Override
    public void draw(float x, float y, float width, float height, OakTreeGUI gui, boolean mirroredHorizontal, boolean mirroredVertical) {
        GlStateManager.color4f(1.0f,1.0f, 1.0f, 1.0f);
        MinecraftClient.getInstance().getTextureManager().bindTexture(textureID);
        gui.drawTexture((int)x, (int)y, drawLeft, drawTop, (int)width, (int)height);
    }
}
