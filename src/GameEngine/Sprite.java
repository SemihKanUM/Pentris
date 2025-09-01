package GameEngine;

import java.awt.Image;

import java.awt.Color;

public class Sprite implements DrawShape {
    private int x, y;
    private Image image;

    /**
     * Creates a new image that can be drawn on to the screen
     * @param leftX The x coordinate of the top left corner of the image
     * @param topY The y coordinate of the top left corner of the image
     * @param img The image that will be drawn
     */
    public Sprite(int leftX, int topY, Image img) {
        x = leftX;
        y = topY;
        image = img;
    }

    /**
     * Gets the x position of the top left corner of the image
     * @return The x position
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of the top left corner of the image
     * @return The y position
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the image that will be drawn
     * @return The image
     */
    public Image getImage() {
        return image;
    }

    // Unused
    public Color getColor() {
        return null;
    }
}
