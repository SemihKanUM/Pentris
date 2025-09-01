package GameEngine;

import java.awt.Color;

public class Circle implements DrawShape {
    private int x, y, r;
    private Color color;

    /**
     * Creates an instances of a drawable circle object
     * @param centerX The x position of the center of the circle
     * @param centerY The y position of the center of the circle
     * @param radius The size of the radius of the circle
     * @param rgbColor The color of the circle
     */
    public Circle(int centerX, int centerY, int radius, Color rgbColor) {
        x = centerX;
        y = centerY;
        r = radius;
        color = rgbColor;
    }

    /**
     * Gets the x position of the center of the circle
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of the center of the circle
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the color of a circle
     */
    public Color getColor() {
        return color;
    }
    
    /**
     * Gets the size of the radius of the circle
     * @return
     */
    public int getRadius() {
        return r;
    }
}
