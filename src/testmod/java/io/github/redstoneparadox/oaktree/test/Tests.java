package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.ControlGui;
import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.control.LabelControl;
import io.github.redstoneparadox.oaktree.networking.InventoryScreenHandlerAccess;
import io.github.redstoneparadox.oaktree.networking.OakTreeServerNetworking;
import io.github.redstoneparadox.oaktree.style.Theme;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tests {
	static Identifier testThree = new Identifier("oaktree:test_three");
	static ScreenHandlerType<TestScreenHandler> handlerType = ScreenHandlerRegistry.registerExtended(testThree, (syncId, playerInventory, buf) -> new TestScreenHandler(syncId, playerInventory.player));
	static Block TEST_THREE_BLOCK;
	static BlockEntityType<ScreenHandlerTestBlockEntity> TEST_THREE_BLOCK_ENTITY_TYPE;

	public static void init() {
		register(new TestBlock(true, Tests::testOne), "one");
		register(new TestBlock(true, Tests::testTwo), "two");
		TEST_THREE_BLOCK = register(new ScreenHandlerTestBlock(true, Tests::testThree), "three");
		register(new TestBlock(true, Tests::testFour), "four");
		register(new TestBlock(true, Tests::testFive), "five");

		TEST_THREE_BLOCK_ENTITY_TYPE = Registry.register(Registry.BLOCK_ENTITY_TYPE, "oaktree:test_three_block_entity", FabricBlockEntityTypeBuilder.create(ScreenHandlerTestBlockEntity::new, TEST_THREE_BLOCK).build(null));
	}

	public static void initClient() {
		ScreenRegistry.<TestScreenHandler, HandledTestScreen>register(handlerType, (screenHandler, inventory, title) -> new HandledTestScreen(screenHandler, new LiteralText(""), true, testThree()));
	}

	private static Block register(Block block, String suffix) {
		return Registry.register(Registry.BLOCK, new Identifier("oaktree", "test_" + suffix), block);
	}

	private static Control testOne() {
		return new Control();

		/*
		DropdownControl leftDropdown = new DropdownControl()
				.dropdown(
						new ListPanelControl()
								.id("base")
								.size(80, 80)
								.children(4, Tests::itemLabel)
								.displayCount(4)
				)
				.size(40, 20)
				.id("button")
				.dropdownDirection(Direction2D.LEFT)
				.anchor(Anchor.CENTER);

		leftDropdown.onTick((controlGui) -> {
			leftDropdown.dropdownDirection(Direction2D.RIGHT);
		});

		DropdownControl rightDropdown = new DropdownControl()
				.dropdown(
						new ListPanelControl()
								.id("base")
								.size(80, 80)
								.children(4, Tests::itemLabel)
								.displayCount(4)
				)
				.size(40, 20)
				.id("button")
				.dropdownDirection(Direction2D.RIGHT)
				.anchor(Anchor.CENTER);


		return new PanelControl<>()
				.child(new DropdownControl()
						.dropdown(
								new ListPanelControl()
										.id("base")
										.size(60, 60)
										.child(leftDropdown)
										.child(rightDropdown)
										.displayCount(2)
						)
						.size(60, 20)
						.id("button")
						.anchor(Anchor.CENTER)
				)
				.size(90, 50)
				.anchor(Anchor.CENTER)
				.id("base");

		 */
	}

	private static Control testTwo() {
		return new Control();

		/*
		ListPanelControl listPanel = new ListPanelControl()
				.size(100, 100)
				.children(20, Tests::itemLabel)
				.displayCount(5)
				.anchor(Anchor.CENTER);

		SliderControl scrollBar = new SliderControl()
				.size(20, 100)
				.onSlide((gui, control) -> listPanel.startIndex((int) Math.floor((
						(listPanel.children.size() - listPanel.getDisplayCount())
						* (control.getScrollPercent())/100))))
				.barLength(10)
				.anchor(Anchor.CENTER);

		return new SplitControl()
				.id("base")
				.size(140, 120)
				.splitSize(110)
				.first(scrollBar)
				.second(listPanel)
				.anchor(Anchor.CENTER);

		 */
	}

	private static Control testThree() {
		return new Control();

		/*
		GridPanelControl playerInvGrid = new GridPanelControl()
				.size(162, 72)
				.anchor(Anchor.CENTER)
				.rows(4)
				.columns(9)
				.children(36, integer -> {
					int index = integer;
					if (integer < 27) {
						index += 9;
					}
					else {
						index -= 27;
					}

					return new SlotControl(index, 0);
				});

		SlotControl slot1 = new SlotControl(0, 1)
				.filter(true, Items.ANDESITE);

		SlotControl slot2 = new SlotControl(1, 1)
				.canTake((gui, slotControl, stack) -> false);

		return new PanelControl<>()
				.id("base")
				.size(180, 120)
				.anchor(Anchor.CENTER)
				.child(playerInvGrid);

		 */
	}

	private static Control itemLabel(int number) {
		return new LabelControl();

		/*
		return new LabelControl()
				.size(60, 20)
				.text("Item No. " + (number + 1))
				.anchor(Anchor.CENTER)
				.shadow(true);

		 */
	}

	private static Control testFour() {
		return new Control();

		/*
		return new ButtonControl()
				.id("button")
				.size(200, 20)
				.anchor(Anchor.CENTER)
				.tooltip(
						new LabelControl()
								.text("Hi!")
								.size(40, 20)
				);

		 */
	}

	private static Control testFive() {
		return new Control();

		/*
		List<Text> texts = createText(50);

		LabelControl label = new LabelControl()
				.size(100, 100)
				.maxDisplayedLines(10)
				.id("tooltip")
				.text(texts)
				.anchor(Anchor.CENTER);

		SliderControl scrollBar = new SliderControl()
				.size(20, 100)
				.onSlide((gui, control) -> label.firstLine((int) Math.floor((45) * control.getScrollPercent()/100)))
				.barLength(10)
				.anchor(Anchor.CENTER);

		return new SplitControl()
				.id("base")
				.size(140, 120)
				.splitSize(110)
				.first(scrollBar)
				.second(label)
				.anchor(Anchor.CENTER);

		 */
	}

	private static List<Text> createText(int lines) {
		List<Text> texts = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < lines; i += 1) {
			LiteralText text = new LiteralText("Text.");
			int j = random.nextInt(3);
			Text formatted;

			switch (j) {
				case 0:
					formatted = text.formatted(Formatting.BOLD);
					break;
				case 1:
					formatted = text.formatted(Formatting.ITALIC);
					break;
				case 2:
					formatted = text.formatted(Formatting.UNDERLINE);
					break;
				default:
					throw new IllegalStateException("Unexpected value: " + j);
			}

			texts.add(formatted);
		}

		return texts;
	}
}
