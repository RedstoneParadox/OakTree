package net.redstoneparadox.oaktree.client.gui.nodes;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

/**
 * A node that can be used to draw a texture on screen. It is suggested
 * that you use a regular node and a StyleBox instead as this class may
 * get removed in a future update.
 */
@Deprecated
public class TextureRectNode extends Node<TextureRectNode> {

    public Identifier textureID;

    public int drawTop = 0;
    public int drawLeft = 0;

    public TextureRectNode(String path) {
        textureID = new Identifier(path);
    }

    public TextureRectNode setTexture(String path) {
        textureID = new Identifier(path);
        return this;
    }

    public TextureRectNode setDrawOrigin(int originX, int originY) {
        drawLeft = originX;
        drawTop = originY;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        ScreenVec anchorOffset = anchorAlignment.getOffset(containerWidth, containerHeight);
        ScreenVec drawOffset = drawAlignment.getOffset(width, height);

        float trueX = x + anchorOffset.x + offsetX - drawOffset.x;
        float trueY = y + anchorOffset.y + offsetY - drawOffset.y;


        GlStateManager.color4f(1.0f,1.0f, 1.0f, 1.0f);
        MinecraftClient.getInstance().getTextureManager().bindTexture(textureID);
        gui.drawTexture((int)trueX, (int)trueY, drawLeft, drawTop, (int)width, (int)height);
    }
}
