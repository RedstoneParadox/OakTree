package io.github.redstoneparadox.oaktree.client.gui.style;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class TextureStyle extends Style {
    Identifier textureID;
    int drawLeft = 0;
    int drawTop = 0;
    private boolean tiled;
    private int textureWidth = 0;
    private int textureHeight = 0;
    private RGBAColor tint = RGBAColor.white();
    private float fileWidth = 0;
    private float fileHeight = 0;
    private float scale = 2;

    public TextureStyle(String path) {
        textureID = new Identifier(path);
    }

    public TextureStyle drawOrigin(int left, int top) {
        drawLeft = left;
        drawTop = top;
        return this;
    }

    /**
     * Sets whether or not the StyleBox
     * should tile its texture.
     *
     * @param tiled The value to set.
     * @return The StyleBox itself.
     */
    public TextureStyle tiled(boolean tiled) {
        this.tiled = tiled;
        return this;
    }

    public TextureStyle textureSize(int textureWidth, int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        return this;
    }

    public TextureStyle fileDimensions(float fileWidth, float fileHeight) {
        this.fileWidth = fileWidth;
        this.fileHeight = fileHeight;
        return this;
    }

    public TextureStyle tint(RGBAColor tint) {
        this.tint = tint;
        return this;
    }

    /**
     * If texture dimensions are wonky, you
     * may need to change the scale.
     *
     * @param scale The value to set.
     * @return This
     */
    public TextureStyle scale(float scale) {
        this.scale = scale;
        return this;
    }

    @Override
    public void draw(int x, int y, int width, int height, ControlGui gui, boolean mirroredHorizontal, boolean mirroredVertical) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(textureID);
        
        if (!tiled) {
            int drawWidth = Math.min((int)width, textureWidth);
            int drawHeight = Math.min((int)height, textureHeight);

            drawTexture(x, y, drawLeft, drawTop, drawWidth, drawHeight);
        }
        else {
            drawTiled(x, y, drawLeft, drawTop, textureWidth, textureHeight, (int)width, (int)height);
        }
    }

    void drawTiled(float x, float y, int left, int top, int drawWidth, int drawHeight, int width, int height) {
        int remainingWidth = width;
        int remainingHeight = height;

        while (remainingHeight > 0) {
            float currentX = x + (width - remainingWidth);
            float currentY = y + (height - remainingHeight);

            float minWidth = Math.min(remainingWidth, drawWidth);
            float minHeight = Math.min(remainingHeight, drawHeight);

            drawTexture(currentX, currentY, left, top, minWidth, minHeight);

            remainingWidth -= drawWidth;
            if (remainingWidth < 0) {
                remainingWidth = width;
                remainingHeight -= drawHeight;
            }
        }
    }

    void drawTexture(float x, float y, float left, float top, float width, float height) {
        int r = (int) (tint.redChannel * 255.0f);
        int g = (int) (tint.greenChannel * 255.0f);
        int b = (int) (tint.blueChannel * 255.0f);
        int a = (int) (tint.alphaChannel * 255.0f);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.color4f(255, 255, 255, 255);

        bufferBuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_TEXTURE);

        bufferBuilder.vertex(x * scale, (y + height) * scale, 0.0).color(r, g, b, a).texture(left/fileWidth, (top + height)/fileHeight).next();
        bufferBuilder.vertex((x + width) * scale, (y + height) * scale, 0.0).color(r, g, b, a).texture((left + width)/fileWidth, (top + height)/fileHeight).next();
        bufferBuilder.vertex((x + width) * scale, y * scale, 0.0).color(r, g, b, a).texture((left + width)/fileWidth, top/fileHeight).next();
        bufferBuilder.vertex(x * scale, y * scale, 0.0).color(r, g, b, a).texture(left/fileWidth, top/fileHeight).next();

        tessellator.draw();

        RenderSystem.disableBlend();
    }
}
