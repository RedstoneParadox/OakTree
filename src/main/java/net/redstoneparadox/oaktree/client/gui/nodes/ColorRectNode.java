package net.redstoneparadox.oaktree.client.gui.nodes;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.RGBAColor;
import net.redstoneparadox.oaktree.client.gui.util.ScreenVec;

/**
 * A node that can be used to draw a colored rectangle on the screen.
 * As this may get removed in the future, it is suggested to use a
 * regular node and a stylebox instead.
 */
@Deprecated
public final class ColorRectNode extends Node<ColorRectNode> {

    RGBAColor color = RGBAColor.white();

    public ColorRectNode setColor(RGBAColor newColor) {
        color = newColor;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        ScreenVec anchorOffset = anchorAlignment.getOffset(containerWidth, containerHeight);
        ScreenVec drawOffset = drawAlignment.getOffset(width, height);

        float trueX = x + anchorOffset.x + offsetX - drawOffset.x;
        float trueY = y + anchorOffset.y + offsetY - drawOffset.y;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBufferBuilder();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(color.redChannel, color.greenChannel, color.blueChannel, color.alphaChannel);

        builder.begin(7, VertexFormats.POSITION);
        builder.vertex(trueX, trueY, 0.0).next();
        builder.vertex(trueX, (trueY + height), 0.0).next();
        builder.vertex((trueX + width), (trueY + height), 0.0).next();
        builder.vertex((trueX + width), trueY, 0.0).next();
        tessellator.draw();

        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }
}
