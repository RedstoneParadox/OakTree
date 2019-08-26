package net.redstoneparadox.oaktree.client.gui.nodes;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class GridNode extends Node<GridNode> {

    private int rows = 1;
    private int columns = 1;

    private float cellWidth = 0.1f;
    private float cellHeight = 0.1f;

    private List<Node> children = new ArrayList<>();

    @Override
    public GridNode setSize(float width, float height) {
        System.out.println("GridNode cannot be resized with Node#setSize(); size is calculated based on cells.");
        return this;
    }

    public GridNode setCellSize(float cellWidth, float cellHeight) {
        this.cellWidth = width;
        this.cellHeight = height;
        return this;
    }

    public GridNode setRows(int rows) {
        this.rows = rows;
        return this;
    }

    public GridNode setColumns(int columns) {
        this.columns = columns;
        return this;
    }

    public GridNode setCell(int index, Node child) {
        if (index >= children.size()) {
            children.add(child);
        }
        else {
            children.set(index, child);
        }
        return this;
    }

    public GridNode forEachCell(BiConsumer<GridNode, Integer> consumer) {
        int cellCount = rows * columns;

        for (int i = 0; i < cellCount; i++) {
            consumer.accept(this, i);
        }

        return this;
    }

    @Override
    public void preDraw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui, float offsetX, float offsetY, float containerWidth, float containerHeight) {
        this.width = cellWidth * columns;
        this.height = cellHeight * rows;

        super.preDraw(mouseX, mouseY, deltaTime, gui, offsetX, offsetY, containerWidth, containerHeight);

        int cellIndex = 0;
        for (int row = 0; row < rows; row++) {
            float cellY = trueY + (cellHeight * (float)row);

            for (int column = 0; column < columns; columns++) {
                float cellX = trueX + (cellWidth * (float)column);

                children.get(cellIndex).preDraw(mouseX, mouseY, deltaTime, gui, cellX, cellY, cellWidth, cellHeight);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        super.draw(mouseX, mouseY, deltaTime, gui);

        int cellCount = rows * columns;
        for (int i = 0; i < cellCount; i++) {
            children.get(i).draw(mouseX, mouseY, deltaTime, gui);
        }
    }
}
