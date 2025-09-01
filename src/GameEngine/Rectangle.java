package GameEngine;

import java.awt.Color;

public class Rectangle implements DrawShape {
    private int x, y, width, height;
    private Color color;

    /**
     * Creates an instance of a drawable rectangle
     * @param leftX The x position of the top left corner of the rectangle
     * @param topY The y position of the top left corner of the rectagle
     * @param rectWidth The width of the rectaingle
     * @param rectHeight The height of the rectangle
     * @param rgbColor The color of the rectangle
     */
    public Rectangle(int leftX, int topY, int rectWidth, int rectHeight, Color rgbColor) {
        x = leftX;
        y = topY;
        width = rectWidth;
        height = rectHeight;
        color = rgbColor;
    }

    /**
     * Gets the x position of the top left corner of the rectangle
     * @return The x position
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of the top left corner of the rectangle
     * @return The y position
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the width of the rectangle
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the rectangle
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the color of the rectangle
     * @return The color
     */
    public Color getColor() {
        return color;
    }
}
