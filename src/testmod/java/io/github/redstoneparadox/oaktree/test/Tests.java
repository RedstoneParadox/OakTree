package io.github.redstoneparadox.oaktree.test;

import io.github.redstoneparadox.oaktree.control.Anchor;
import io.github.redstoneparadox.oaktree.control.ButtonControl;
import io.github.redstoneparadox.oaktree.control.Control;
import io.github.redstoneparadox.oaktree.control.DropdownControl;
import io.github.redstoneparadox.oaktree.control.GridPanelControl;
import io.github.redstoneparadox.oaktree.control.LabelControl;
import io.github.redstoneparadox.oaktree.control.ListPanelControl;
import io.github.redstoneparadox.oaktree.control.PanelControl;
import io.github.redstoneparadox.oaktree.control.SliderControl;
import io.github.redstoneparadox.oaktree.control.SlotControl;
import io.github.redstoneparadox.oaktree.control.SplitControl;
import io.github.redstoneparadox.oaktree.math.Direction2D;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Items;
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
		PanelControl panel = new PanelControl();
		DropdownControl leftDropdown = new DropdownControl();
		DropdownControl rightDropdown = new DropdownControl();
		DropdownControl centerDropdown = new DropdownControl();
		ListPanelControl leftListPanel = new ListPanelControl();
		ListPanelControl rightListPanel = new ListPanelControl();
		ListPanelControl centerListPanel = new ListPanelControl();

		leftListPanel.setId("base");
		leftListPanel.setSize(80, 80);
		leftListPanel.addChildren(4, false, Tests::itemLabel);

		leftDropdown.setId("button");
		leftDropdown.setDropdown(leftListPanel);
		leftDropdown.setSize(40, 20);
		leftDropdown.setDropdownDirection(Direction2D.LEFT);
		leftDropdown.setAnchor(Anchor.CENTER);

		rightListPanel.setId("base");
		rightListPanel.setSize(80, 80);
		rightListPanel.addChildren(4, false, Tests::itemLabel);

		rightDropdown.setId("button");
		rightDropdown.setDropdown(rightListPanel);
		rightDropdown.setSize(40, 20);
		rightDropdown.setDropdownDirection(Direction2D.LEFT);
		rightDropdown.setAnchor(Anchor.CENTER);

		centerListPanel.setId("base");
		centerListPanel.setSize(60, 60);
		centerListPanel.addChild(leftDropdown);
		centerListPanel.addChild(rightDropdown);
		centerListPanel.setDisplayCount(2);

		centerDropdown.setId("button");
		centerDropdown.setDropdown(centerListPanel);
		centerDropdown.setSize(60, 20);
		centerDropdown.setAnchor(Anchor.CENTER);

		panel.setId("base");
		panel.addChild(centerDropdown);
		panel.setSize(90, 50);
		panel.setAnchor(Anchor.CENTER);

		return panel;
	}

	private static Control testTwo() {
		SplitControl split = new SplitControl();
		SliderControl scrollBar = new SliderControl();
		ListPanelControl listPanel = new ListPanelControl();

		listPanel.setSize(100, 100);
		listPanel.addChildren(20, false, Tests::itemLabel);
		listPanel.setDisplayCount(5);
		listPanel.setAnchor(Anchor.CENTER);

		scrollBar.setSize(20, 100);
		scrollBar.setBarLength(10);
		scrollBar.setAnchor(Anchor.CENTER);
		scrollBar.onSlide(gui -> listPanel.setStartIndex(
				(int) Math.floor((listPanel.children.size() - listPanel.getDisplayCount()) * scrollBar.getScrollPercent() /100)
		));

		split.setId("base");
		split.setSize(140, 120);
		split.setSplitSize(110);
		split.setFirst(scrollBar);
		split.setSecond(listPanel);
		split.setAnchor(Anchor.CENTER);

		return split;
	}

	private static Control testThree() {
		PanelControl panel = new PanelControl();
		SlotControl slot1 = new SlotControl(0, 1);
		SlotControl slot2 = new SlotControl(1, 1);
		GridPanelControl inventoryPanel = new GridPanelControl();

		inventoryPanel.setSize(162, 72);
		inventoryPanel.setRows(4);
		inventoryPanel.setColumns(9);
		inventoryPanel.setAnchor(Anchor.CENTER);
		inventoryPanel.addChildren(36, false, integer -> {
			int index = integer;
			if (integer < 27) {
				index += 9;
			}
			else {
				index -= 27;
			}

			return new SlotControl(index, 0);
		});

		slot1.filter(true, Items.ANDESITE);
		slot2.canTake((controlGui, itemStack) -> false);

		panel.setId("base");
		panel.setSize(180, 120);
		panel.addChild(inventoryPanel);
		panel.setAnchor(Anchor.CENTER);

		return panel;
	}

	private static Control testFour() {
		ButtonControl button = new ButtonControl();
		LabelControl tooltip = new LabelControl();

		tooltip.setId("tooltip");
		tooltip.setText("Hi!");
		tooltip.setSize(40, 20);

		button.setId("button");
		button.setSize(200, 20);
		button.setTooltip(tooltip);
		button.setAnchor(Anchor.CENTER);

		return button;
	}

	private static Control testFive() {
		List<Text> texts = createText(50);
		LabelControl label = new LabelControl();
		SliderControl scrollbar = new SliderControl();
		SplitControl split = new SplitControl();

		label.setId("tooltip");
		label.setSize(100, 100);
		label.setMaxDisplayedLines(10);
		label.setText(texts);
		label.setAnchor(Anchor.CENTER);

		scrollbar.setSize(20, 100);
		scrollbar.setAnchor(Anchor.CENTER);
		scrollbar.setBarLength(10);
		scrollbar.onSlide(gui -> label.setFirstLine((int) Math.floor((45) * scrollbar.getScrollPercent()/100)));

		split.setId("base");
		split.setSize(130, 120);
		split.setAnchor(Anchor.CENTER);
		split.setSplitSize(110);
		split.setFirst(scrollbar);
		split.setSecond(label);

		return split;
	}

	private static Control itemLabel(int number) {
		LabelControl label = new LabelControl();

		label.setSize(60, 20);
		label.setText("Item No. " + (number + 1));
		label.setShadow(true);
		label.setAnchor(Anchor.CENTER);

		return label;
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
