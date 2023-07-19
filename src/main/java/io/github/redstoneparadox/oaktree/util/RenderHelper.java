package io.github.redstoneparadox.oaktree.util;

public class RenderHelper {
	private static float zOffset = 0.0f;

	public static void setzOffset(double zOffset) {
		RenderHelper.zOffset = (float) zOffset;
	}

	/*
	public static void drawRectangle(MatrixStack matrices, int x, int y, int width, int height, @NotNull Color color) {
		int r = (int) (color.red * 255.0f);
		int g = (int) (color.green * 255.0f);
		int b = (int) (color.blue * 255.0f);
		int a = (int) (color.alpha * 255.0f);

		int c = (a << 24) + (r << 16) + (g << 8) + b;

		if (width >= 1 && height >= 1) DrawableHelper.fill(matrices, x, y, x + width, y + height, c);
	}


	public static void drawItemStackCentered(int x, int y, int width, int height, ItemStack stack) {
		int offsetX = width/2 - 8;
		int offsetY = height/2 - 8;

		ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
		TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

		itemRenderer.zOffset = zOffset;

		itemRenderer.renderGuiItemIcon(stack, x + offsetX, y + offsetY);
		itemRenderer.renderGuiItemOverlay(textRenderer, stack, x + offsetX, y + offsetY);

		itemRenderer.zOffset = -zOffset;
	}

	public static void drawTexture(MatrixStack matrices, float x, float y, float left, float top, float width, float height, float fileWidth, float fileHeight, float scale, Identifier texture, Color tint) {
		float r = (tint.red);
		float g = (tint.green);
		float b = (tint.blue);
		float a = (tint.alpha);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBufferBuilder();
		Matrix4f model = matrices.peek().getModel().getPosition();

		RenderSystem.enableBlend();
		RenderSystem.setShaderTexture(0, texture);
		RenderSystem.setShaderColor(r, g, b, a);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);

		float u1 = left/fileWidth;
		float u2 = (left + width)/fileWidth;
		float v1 = top/fileHeight;
		float v2 = (top + height)/fileHeight;

		/*
		RenderSystem.blendFuncSeparate(
				GlStateManager.SrcFactor.SRC_COLOR.value,
				GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA.value,
				GlStateManager.SrcFactor.ONE.value,
				GlStateManager.DstFactor.ZERO.value
		);

		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);

		bufferBuilder.vertex(model, x * scale, (y + height) * scale, zOffset).uv(u1, v2).next();
		bufferBuilder.vertex(model, (x + width) * scale, (y + height) * scale, zOffset).uv(u2, v2).next();
		bufferBuilder.vertex(model, (x + width) * scale, y * scale, zOffset).uv(u2, v1).next();
		bufferBuilder.vertex(model, x * scale, y * scale, zOffset).uv(u1, v1).next();

		BufferRenderer.drawWithShader(bufferBuilder.end());
		RenderSystem.disableBlend();
	}

	public static void drawText(MatrixStack matrices, OrderedText text, int x, int y, boolean shadow, Color fontColor) {
		TextRenderer renderer = MinecraftClient.getInstance().textRenderer;

		int redInt = (int) fontColor.red * 255;
		int greenInt = (int) fontColor.green * 255;
		int blueInt = (int) fontColor.blue * 255;

		int colorInt = redInt << 16 | greenInt << 8 | blueInt;

		matrices.translate(0.0, 0.0, zOffset);

		if (shadow) {
			renderer.drawWithShadow(matrices, text, x + 4, y + 4, colorInt);
		} else {
			renderer.draw(matrices, text, x + 4, y + 4, colorInt);
		}

		matrices.translate(0.0, 0.0, -zOffset);
	}
	*/
}
