package io.github.redstoneparadox.oaktree.client.gui.style;

import com.mojang.blaze3d.platform.GlStateManager;
import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import io.github.redstoneparadox.oaktree.client.gui.Color;
import io.github.redstoneparadox.oaktree.client.geometry.ScreenPos;

public class ColorStyle extends Style {

    private Color color;
    private Color borderColor;
    private int borderWidth;

    public ColorStyle(Color color, Color borderColor, int borderWidth) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    public ColorStyle(Color color) {
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
        GlStateManager.blendColor(color.red, color.green, color.blue, color.alpha);

        ScreenPos vert1 = new ScreenPos(x, y);
        ScreenPos vert2 = new ScreenPos(x, y + height);
        ScreenPos vert3 = new ScreenPos(x + width, y + height);
        ScreenPos vert4 = new ScreenPos(x + width, y);

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
        builder.vertex(vert1.x, vert1.y, 0.0).color(color.red, color.green, color.blue, color.alpha).next();
        builder.vertex(vert2.x, vert2.y, 0.0).color(color.red, color.green, color.blue, color.alpha).next();
        builder.vertex(vert3.x, vert3.y, 0.0).color(color.red, color.green, color.blue, color.alpha).next();
        builder.vertex(vert4.x, vert4.y, 0.0).color(color.red, color.green, color.blue, color.alpha).next();

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
        GlStateManager.color4f(borderColor.red, borderColor.green, borderColor.blue, borderColor.alpha);

        ScreenPos vert1 = new ScreenPos(x - borderWidth, y - borderWidth);
        ScreenPos vert2 = new ScreenPos(x - borderWidth, y + height + borderWidth);
        ScreenPos vert3 = new ScreenPos(x + width + borderWidth, y + height + borderWidth);
        ScreenPos vert4 = new ScreenPos(x + width + borderWidth, y - borderWidth);

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
