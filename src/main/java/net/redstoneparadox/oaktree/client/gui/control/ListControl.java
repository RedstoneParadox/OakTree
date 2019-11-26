package net.redstoneparadox.oaktree.client.gui.control;

import net.redstoneparadox.oaktree.client.gui.OakTreeGUI;
import net.redstoneparadox.oaktree.client.gui.util.ControlDirection;

import java.util.ArrayList;

/**
 *
 */
public class ListControl extends Control<ListControl> {

    float boxWidth = 0.1f;
    float boxHeight = 0.1f;

    boolean wrap = false;

    int displayCount = 1;
    int offset = 0;

    ControlDirection listDirection = ControlDirection.DOWN;

    private ArrayList<Control> children = new ArrayList<>();

    /**
     * Sets the size of the box for each list box.
     *
     * @param boxWidth The width of the box.
     * @param boxHeight The height of the box.
     * @return The node itself.
     */
    public ListControl setBoxSize(float boxWidth, float boxHeight) {
        this.boxHeight = boxHeight;
        this.boxWidth = boxWidth;
        return this;
    }

    /**
     * Sets whether or not this list should wrap around and display
     * children from the start of the list when it reaches the end
     * (or children from the end of the list when it reaches the
     * beginning).
     *
     * @param wrap Whether or not this ListNode should wrap.
     * @return The node itself.
     */
    public ListControl setWrap(boolean wrap) {
        this.wrap = wrap;
        return this;
    }

    /**
     * Sets how many children can be displayed at a time.
     *
     * @param displayCount The amount to be displayed.
     * @return The node itself.
     */
    public ListControl setDisplayCount(int displayCount) {
        this.displayCount = displayCount;
        return this;
    }

    /**
     * Sets the index of the first child to be displayed.
     * For example, having a display count of 3, 6 children
     * and an offset of 1 will display the 2nd, 3rd, and 4th
     * children.
     *
     * @param offset The offset.
     * @return The node itself.
     */
    public ListControl setOffset(int offset) {
        if (!wrap && offset >= children.size() - (displayCount - 1)) {
            this.offset = children.size();
        }
        else if (offset < 0) {
            this.offset = 0;
        }
        return this;
    }

    /**
     * Scrolls the list node by the amount; negative values
     * scroll the list backwards.
     *
     * @param amount The amount to scroll
     * @return The node itself.
     */
    public ListControl scroll(int amount) {
        int newOffset = this.offset + amount;
        if (wrap) {
            while (newOffset < 0) {
                newOffset += this.children.size();
            }
            while (newOffset >= this.children.size()) {
                newOffset -= this.children.size();
            }
        }
        return this.setOffset(newOffset);
    }

    /**
     * Sets the direction for this list to draw in.
     *
     * @param listDirection The direction.
     * @return The node itself.
     */
    public ListControl setListDirection(ControlDirection listDirection) {
        this.listDirection = listDirection;
        return this;
    }

    @Override
    public void draw(int mouseX, int mouseY, float deltaTime, OakTreeGUI gui) {
        if (!visible) return;
        super.draw(mouseX, mouseY, deltaTime, gui);


    }
}
