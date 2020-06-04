package io.github.redstoneparadox.oaktree.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.redstoneparadox.oaktree.client.geometry.ScreenPos;
import io.github.redstoneparadox.oaktree.client.gui.Color;
import net.minecraft.class_5348;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

public class RenderHelper {
    private static double zOffset = 0.0;

    public static void setzOffset(double zOffset) {
        RenderHelper.zOffset = zOffset;
    }

    public static void drawRectangle(int x, int y, int width, int height, @NotNull Color color) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.getBuffer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SrcFactor.SRC_COLOR.field_22545, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.field_22528, GlStateManager.SrcFactor.ONE.field_22545, GlStateManager.DstFactor.ZERO.field_22528);
        GlStateManager.blendColor(color.red, color.green, color.blue, color.alpha);
        GlStateManager.enableDepthTest();

        ScreenPos vert1 = new ScreenPos(x, y);
        ScreenPos vert2 = new ScreenPos(x, y + height);
        ScreenPos vert3 = new ScreenPos(x + width, y + height);
        ScreenPos vert4 = new ScreenPos(x + width, y);

        builder.begin(7, VertexFormats.POSITION_COLOR);
        builder.vertex(vert1.x, vert1.y, zOffset).color(color.red, color.green, color.blue, color.alpha).next();
        builder.vertex(vert2.x, vert2.y, zOffset).color(color.red, color.green, color.blue, color.alpha).next();
        builder.vertex(vert3.x, vert3.y, zOffset).color(color.red, color.green, color.blue, color.alpha).next();
        builder.vertex(vert4.x, vert4.y, zOffset).color(color.red, color.green, color.blue, color.alpha).next();

        tessellator.draw();

        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }

    public static void drawItemStackCentered(int x, int y, int width, int height, ItemStack stack) {
        int offsetX = width/2 - 8;
        int offsetY = height/2 - 8;

        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        itemRenderer.renderGuiItem(stack, x + offsetX, y + offsetY);
        itemRenderer.renderGuiItemOverlay(textRenderer, stack, x + offsetX, y + offsetY);
    }

    public static void drawTexture(float x, float y, float left, float top, float width, float height, float fileWidth, float fileHeight, float scale, Identifier texture, Color tint) {
        int r = (int) (tint.red * 255.0f);
        int g = (int) (tint.green * 255.0f);
        int b = (int) (tint.blue * 255.0f);
        int a = (int) (tint.alpha * 255.0f);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.blendColor(255, 255, 255, 255);
        RenderSystem.enableDepthTest();

        bufferBuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION_COLOR_TEXTURE);

        bufferBuilder.vertex(x * scale, (y + height) * scale, zOffset).color(r, g, b, a).texture(left/fileWidth, (top + height)/fileHeight).next();
        bufferBuilder.vertex((x + width) * scale, (y + height) * scale, zOffset).color(r, g, b, a).texture((left + width)/fileWidth, (top + height)/fileHeight).next();
        bufferBuilder.vertex((x + width) * scale, y * scale, zOffset).color(r, g, b, a).texture((left + width)/fileWidth, top/fileHeight).next();
        bufferBuilder.vertex(x * scale, y * scale, zOffset).color(r, g, b, a).texture(left/fileWidth, top/fileHeight).next();

        tessellator.draw();

        RenderSystem.disableBlend();
    }

    public static void drawText(MatrixStack matrices, class_5348 text, int x, int y, boolean shadow, Color fontColor) {
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;

        int redInt = (int) fontColor.red * 255;
        int greenInt = (int) fontColor.green * 255;
        int blueInt = (int) fontColor.blue * 255;

        int colorInt = redInt << 16 | greenInt << 8 | blueInt;

        matrices.translate(0.0, 0.0, zOffset);
        if (shadow) renderer.drawWithShadow(matrices, text, x + 4, y + 4, colorInt);
        else renderer.draw(matrices, text, x + 4, y + 4, colorInt);
        matrices.translate(0.0, 0.0, -zOffset);
    }
}
