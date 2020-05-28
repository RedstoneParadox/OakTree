package io.github.redstoneparadox.oaktree.client.gui.control;

import io.github.redstoneparadox.oaktree.client.gui.ControlGui;
import io.github.redstoneparadox.oaktree.client.gui.util.ScreenVec;
import io.github.redstoneparadox.oaktree.util.ListUtils;
import io.github.redstoneparadox.oaktree.util.TriFunction;

import java.util.List;

/**
 * Subclass of {@link PanelControl} that lays out its children in
 * a grid pattern.
 */
public class GridPanelControl extends PanelControl<GridPanelControl> {
    public int rows = 1;
    public int columns = 1;

    public GridPanelControl() {
        this.id = "grid_panel";
    }

    /**
     * Sets the number of rows to divide the
     * panel into.
     *
     * @param rows The number of rows.
     * @return The control itself.
     */
    public GridPanelControl rows(int rows) {
        this.rows = rows;
        return this;
    }

    /**
     * Sets the number of columns to divide
     * the panel into.
     *
     * @param columns The number of columns.
     * @return The control itself.
     */
    public GridPanelControl columns(int columns) {
        this.columns = columns;
        return this;
    }

    /**
     * Adds a child {@link Control} to a cell specified by the
     * passed row and column. Both the row and column are
     * 0-indexed; a {@link GridCellOutOfBoundsException} will
     * be thrown if either of them are too large.
     *
     * @param child The child {@link Control}
     * @param row The row.
     * @param column The column
     * @return The control itself.
     */
    public GridPanelControl child(Control child, int row, int column) throws IndexOutOfBoundsException {
        if (row >= rows || column >= columns) throw new GridCellOutOfBoundsException(row, column);

        int index = row * columns + column;
        if (index >= children.size()) ListUtils.growList(children, index + 1);
        children.set(index, child);
        return this;
    }

    /**
     * Iterates through all cells and adds the results of the function
     * to each cell; the function is passed the row, column, and cell
     * index in that order.
     *
     * @param function The function to call.
     * @return The control itself.
     */
    public GridPanelControl cells(TriFunction<Integer, Integer, Integer, Control<?>> function) {
        children.clear();
        int index = 0;
        for (int j = 0; j < rows; j += 1) {
            for (int i = 0; i < columns; i += 1) {
                children.add(function.apply(j, i, index));
                index += 1;
            }
        }
        return this;
    }

    @Override
    void arrangeChildren(ControlGui gui, List<Control<?>> controlList, int mouseX, int mouseY) {
        int cellWidth = area.width/columns;
        int cellHeight = area.height/rows;
        ScreenVec innerDimensions = innerDimensions(cellWidth, cellHeight);
        ScreenVec innerPosition = innerPosition(trueX, trueY);

        int index = 0;

        for (int j = 0; j < rows && index < children.size(); j += 1) {
            for (int i = 0; i < columns && index < children.size(); i += 1) {
                int cellX = innerPosition.x + (i * cellWidth);
                int cellY = innerPosition.y + (j * cellHeight);

                Control child = children.get(index);
                if (child != null) child.preDraw(gui, cellX, cellY, innerDimensions.x, innerDimensions.y, controlList, mouseX, mouseY);

                index += 1;
            }
        }
    }

    class GridCellOutOfBoundsException extends IndexOutOfBoundsException {
        GridCellOutOfBoundsException(int row, int column) {
            super("Grid cell [" + row + ", " + column + "] was out of bounds; actual grid size: [ " + rows + ", " + columns + "].");
        }
    }
}
