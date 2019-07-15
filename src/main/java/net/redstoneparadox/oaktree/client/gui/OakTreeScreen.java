package net.redstoneparadox.oaktree.client.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.redstoneparadox.oaktree.client.gui.nodes.Node;

import java.util.List;
import java.util.stream.Stream;

public class OakTreeScreen extends Screen implements OakTreeGUI {

    Node root;

    public OakTreeScreen(Node treeRoot) {
        super(new Text() {
            @Override
            public Text setStyle(Style var1) {
                return null;
            }

            @Override
            public Style getStyle() {
                return null;
            }

            @Override
            public Text append(Text var1) {
                return null;
            }

            @Override
            public String asString() {
                return "";
            }

            @Override
            public List<Text> getSiblings() {
                return null;
            }

            @Override
            public Stream<Text> stream() {
                return null;
            }

            @Override
            public Text copy() {
                return null;
            }

            @Override
            public String getString() {
                return "gui";
            }
        });
        root = treeRoot;
    }

    @Override
    public void init(MinecraftClient minecraftClient_1, int int_1, int int_2) {
        super.init(minecraftClient_1, int_1, int_2);
        root.setup(minecraftClient_1, int_1, int_2);
    }

    @Override
    public void render(int int_1, int int_2, float float_1) {
        root.preDraw(this, 0, 0, width, height);
        root.draw(int_1, int_2, float_1, this,0, 0, width, height);
    }

}
