package redstoneparadox.oaktree.client.gui.style;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.world.CollisionView;
import redstoneparadox.oaktree.client.gui.OakTreeGUI;
import redstoneparadox.oaktree.client.gui.util.RGBAColor;
import redstoneparadox.oaktree.client.gui.util.ScreenVec;

public class ColorStyleBox extends StyleBox {

    private RGBAColor color;
    private RGBAColor borderColor;
    private float borderWidth;

    public ColorStyleBox(RGBAColor color, RGBAColor borderColor, float borderWidth) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    public ColorStyleBox(RGBAColor color) {
        this(color, null, 1.0f);
    }

    @Override
    public void draw(float x, float y, float width, float height, OakTreeGUI gui, boolean mirroredHorizontal, boolean mirroredVertical) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        if (borderColor != null) drawBorder(x, y, width, height, mirroredHorizontal, mirroredVertical);

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.value, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SrcFactor.ONE.value, GlStateManager.DstFactor.ZERO.value);
        GlStateManager.color4f(color.redChannel, color.greenChannel, color.blueChannel, color.alphaChannel);

        ScreenVec vert1 = new ScreenVec(x, y);
        ScreenVec vert2 = new ScreenVec(x, y + height);
        ScreenVec vert3 = new ScreenVec(x + width, y + height);
        ScreenVec vert4 = new ScreenVec(x + width, y);

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

    private void drawBorder(float x, float y, float width, float height, boolean mirroredHorizontal, boolean mirroredVertical) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.value, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.value, GlStateManager.SrcFactor.ONE.value, GlStateManager.DstFactor.ZERO.value);
        GlStateManager.color4f(borderColor.redChannel, borderColor.greenChannel, borderColor.blueChannel, borderColor.alphaChannel);

        ScreenVec vert1 = new ScreenVec(x - borderWidth, y - borderWidth);
        ScreenVec vert2 = new ScreenVec(x - borderWidth, y + height + borderWidth);
        ScreenVec vert3 = new ScreenVec(x + width + borderWidth, y + height + borderWidth);
        ScreenVec vert4 = new ScreenVec(x + width + borderWidth, y - borderWidth);

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
