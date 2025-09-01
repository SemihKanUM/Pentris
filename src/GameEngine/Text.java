package GameEngine;

import java.awt.Color;
import java.awt.Font;

public class Text implements DrawShape {

    private int x,y;
    private Color color;
    private String text;
    private Font font;

    /**
     * Creates a new drawable text object
     * @param x The x position of the top left corner of the first letter of the text
     * @param y The y position of the top left corner of the first letter of the text
     * @param text The text to be drawn
     * @param font The font to draw the text with
     * @param color The color of the text
     */
    public Text(int x, int y, String text, Font font, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        this.color = color;
    }

    /**
     * Gets the x position of the top left corner of the first letter of the text
     * @return The x position
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of the top left corner of the first letter of the text
     * @return The y position
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the color of the text
     * @return The color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the text
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the font that the text will be drawn in
     * @return The font
     */
    public Font getFont() {
        return font;
    }
    
}
