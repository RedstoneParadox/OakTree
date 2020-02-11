package io.github.redstoneparadox.oaktree.client.gui.style;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import io.github.redstoneparadox.oaktree.client.gui.OakTreeGUI;

public class TextureStyleBox extends StyleBox {
    Identifier textureID;
    int drawLeft = 0;
    int drawTop = 0;
    int magicNumber = 1;
    private boolean tiled;
    private int textureWidth = 0;
    private int textureHeight = 0;

    public TextureStyleBox(String path) {
        textureID = new Identifier(path);
    }

    public TextureStyleBox drawOrigin(int left, int top) {
        drawLeft = left;
        drawTop = top;
        return this;
    }

    /**
     * <p>
     * Sets the magic number to multiply the left, top,
     * width, and height by when drawing. You may need
     * to adjust this depending on the side of the
     * texture file you are using.
     * </p>
     *
     * <p>
     * For example, if the file dimensions are 16x16,
     * the magic number should be 2.
     * </p>
     *
     * @param magicNumber The value to set.
     * @return The stylebox itself.
     */
    public TextureStyleBox magicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
        return this;
    }

    /**
     * Sets whether or not the StyleBox
     * should tile its texture; it's
     * recommended that you have a
     * separate texture file for this.
     *
     * @param tiled The value to set.
     * @return The StyleBox itself.
     */
    public TextureStyleBox tiled(boolean tiled) {
        this.tiled = tiled;
        return this;
    }

    public TextureStyleBox textureSize(int textureWidth, int textureHeight) {
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        return this;
    }

    @Override
    public void draw(float x, float y, float width, float height, OakTreeGUI gui, boolean mirroredHorizontal, boolean mirroredVertical) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(textureID);
        if (gui instanceof DrawableHelper) {
            if (!tiled) {
                int drawWidth = Math.min((int)width, textureWidth);
                int drawHeight = Math.min((int)height, textureHeight);

                drawTexture(x, y, drawLeft * magicNumber, drawTop * magicNumber, drawWidth * magicNumber, drawHeight * magicNumber);
            }
            else {
                drawTexture(x, y, drawLeft * magicNumber, drawTop * magicNumber, width * magicNumber, height * magicNumber);
            }
        }
    }

    private void drawTexture(float x, float y, float left, float top, float width, float height) {
        float widthDivisor = textureWidth * 2.0f;
        float heightDivisor = textureHeight * 2.0f;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(x, (y + height), 0.0).texture(left/widthDivisor, (height + top)/heightDivisor).color(255, 255, 255, 255).next();
        bufferBuilder.vertex((x + width), (y + height), 0.0).texture((width + left)/widthDivisor, (height + top)/heightDivisor).color(255, 255, 255, 255).next();
        bufferBuilder.vertex((x + width), y, 0.0).texture((width + left)/widthDivisor, top/heightDivisor).color(255, 255, 255, 255).next();
        bufferBuilder.vertex(x, y, 0.0).texture(left/widthDivisor, top/heightDivisor).color(255, 255, 255, 255).next();
        tessellator.draw();
    }
}
