package net.redstoneparadox.oaktree.client.gui.style;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;

public class ColorStyleBox extends StyleBox {

    RGBAColor color;

    public ColorStyleBox(RGBAColor styleColor) {
        color = styleColor;
    }

    @Override
    public void draw(float x, float y, float width, float height, OakTreeGUI gui) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBufferBuilder();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(color.redChannel, color.greenChannel, color.blueChannel, color.alphaChannel);

        builder.begin(7, VertexFormats.POSITION);
        builder.vertex(x, y, 0.0).next();
        builder.vertex(x, (y + height), 0.0).next();
        builder.vertex((x + width), (y + height), 0.0).next();
        builder.vertex((x + width), y, 0.0).next();
        tessellator.draw();

        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

}
