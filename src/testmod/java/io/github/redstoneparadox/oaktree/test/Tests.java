package io.github.redstoneparadox.oaktree.test;

import net.minecraft.block.Block;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tests {
	public static void init() {
		// TEST_THREE_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "oaktree:test_three_block_entity", FabricBlockEntityTypeBuilder.create(ScreenHandlerTestBlockEntity::new, TEST_THREE_BLOCK).build(null));
	}

	public static void initClient() {
		// ScreenRegistry.<TestScreenHandler, HandledTestScreen>register(handlerType, (screenHandler, inventory, title) -> new HandledTestScreen(screenHandler, new LiteralText(""), true, testThree()));
	}

	private static Block register(Block block, String suffix) {
		return Registry.register(Registry.BLOCK, new Identifier("oaktree", "test_" + suffix), block);
	}

	private static List<Text> createText(int lines) {
		List<Text> texts = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < lines; i += 1) {
			LiteralText text = new LiteralText("Text.");
			int j = random.nextInt(3);
			Text formatted = switch (j) {
				case 0 -> text.formatted(Formatting.BOLD);
				case 1 -> text.formatted(Formatting.ITALIC);
				case 2 -> text.formatted(Formatting.UNDERLINE);
				default -> throw new IllegalStateException("Unexpected value: " + j);
			};

			texts.add(formatted);
		}

		return texts;
	}
}
