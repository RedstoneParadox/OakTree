package io.github.redstoneparadox.oaktree.client.gui.style;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import io.github.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import io.github.redstoneparadox.oaktree.client.geometry.Vector2D;

public class ColorStyle extends Style {

    private RGBAColor color;
    private RGBAColor borderColor;
    private int borderWidth;

    public ColorStyle(RGBAColor color, RGBAColor borderColor, int borderWidth) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    public ColorStyle(RGBAColor color) {
        this(color, null, 1);
    }

    @Override
    public void draw(int x, int y, int width, int height, ControlGui gui, boolean mirroredHorizontal, boolean mirroredVertical) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        if (borderColor != null) drawBorder(x, y, width, height, mirroredHorizontal, mirroredVertical);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.field_22545, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.field_22528, GlStateManager.SrcFactor.ONE.field_22545, GlStateManager.DstFactor.ZERO.field_22528);
        GlStateManager.blendColor(color.redChannel, color.greenChannel, color.blueChannel, color.alphaChannel);

        Vector2D vert1 = new Vector2D(x, y);
        Vector2D vert2 = new Vector2D(x, y + height);
        Vector2D vert3 = new Vector2D(x + width, y + height);
        Vector2D vert4 = new Vector2D(x + width, y);

        if (mirroredHorizontal) {
            vert1.x = vert1.x - width;
            vert2.x = vert2.x - width;
            vert3.x = vert3.x - width;
            vert4.x = vert4.x - width;
        }

        if (mirroredVertical) {
            vert1.y = vert1.y - height;
            vert2.y = vert2.y - height;
            vert3.y = vert3.y - height;
            vert4.y = vert4.y - height;
        }

        builder.begin(7, VertexFormats.POSITION_COLOR);
        builder.vertex(vert1.x, vert1.y, 0.0).color(color.redChannel, color.greenChannel, color.blueChannel, color.alphaChannel).next();
        builder.vertex(vert2.x, vert2.y, 0.0).color(color.redChannel, color.greenChannel, color.blueChannel, color.alphaChannel).next();
        builder.vertex(vert3.x, vert3.y, 0.0).color(color.redChannel, color.greenChannel, color.blueChannel, color.alphaChannel).next();
        builder.vertex(vert4.x, vert4.y, 0.0).color(color.redChannel, color.greenChannel, color.blueChannel, color.alphaChannel).next();

        tessellator.draw();

        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    private void drawBorder(int x, int y, int width, int height, boolean mirroredHorizontal, boolean mirroredVertical) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.field_22545, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.field_22528, GlStateManager.SrcFactor.ONE.field_22545, GlStateManager.DstFactor.ZERO.field_22528);
        GlStateManager.color4f(borderColor.redChannel, borderColor.greenChannel, borderColor.blueChannel, borderColor.alphaChannel);

        Vector2D vert1 = new Vector2D(x - borderWidth, y - borderWidth);
        Vector2D vert2 = new Vector2D(x - borderWidth, y + height + borderWidth);
        Vector2D vert3 = new Vector2D(x + width + borderWidth, y + height + borderWidth);
        Vector2D vert4 = new Vector2D(x + width + borderWidth, y - borderWidth);

        if (mirroredHorizontal) {
            vert1.x = vert1.x - width;
            vert2.x = vert2.x - width;
            vert3.x = vert3.x - width;
            vert4.x = vert4.x - width;
        }

        if (mirroredVertical) {
            vert1.y = vert1.y - height;
            vert2.y = vert2.y - height;
            vert3.y = vert3.y - height;
            vert4.y = vert4.y - height;
        }

        builder.begin(7, VertexFormats.POSITION);
        builder.vertex(vert1.x, vert1.y, 0.0).next();
        builder.vertex(vert2.x, vert2.y, 0.0).next();
        builder.vertex(vert3.x, vert3.y, 0.0).next();
        builder.vertex(vert4.x, vert4.y, 0.0).next();

        tessellator.draw();

        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

}
