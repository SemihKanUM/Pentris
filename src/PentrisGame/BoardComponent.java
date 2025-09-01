package PentrisGame;

import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;

public class BoardComponent extends JComponent {
    ArrayList<Drawable> drawables;

    public BoardComponent() {
        drawables = new ArrayList<Drawable>();
    }

    /**
     * Interface that defines all drawable objects
     */
    interface Drawable {
        public void draw(Graphics2D g2);
    }

    /**
     * Class to draw a rectangle.
     */
    class Rect implements Drawable {
        int x, y, width, height;
        Color color;
        /**
         * Constructor.
         * Creates a drawable rectangle object.
         * @param x x position of the top left corner of the rectangle
         * @param y y position of the top left corner of the rectangle
         * @param width Width of the rectangle
         * @param height Height of the rectangle
         * @param color Color of the rectangle
         */
        public Rect(int x, int y, int width, int height, Color color) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.color = color;
        }

        /**
         * Method that draws the rectangle to the screen.
         */
        public void draw(Graphics2D g2) {
            g2.setColor(color);
            g2.fillRect(x, y, width, height);
        }
    }

    /**
     * Draws a image to the screen.
     * @param x x position of the top left corner of the rectangle
     * @param y y position of the top left corner of the rectangle
     * @param img Color of the rectangle
     * @param img Image that will be drawen.
     */
    public void drawImage(int x, int y ,Image img){
        image image = new image(x, y, img);
        drawables.add(image);
    }

    class image implements Drawable{
        Image img;
        int x,y;

        /**
         * Constructor.
         * Creates a drawable image object.
         * @param x x position of the top left corner of the image.
         * @param y y position of the top left corner of the image.
         * @param img Image that will be drawen.
         */

        public image(int x, int y, Image img) {
            this.x = x;
            this.y = y;
            this.img = img;
        }

        /**
         * Method for drawing the image to the screen.
         */
        public void draw(Graphics2D g2) {
            g2.drawImage(img, x, y, null);
        }

    }


    /**
     * Class to draw text.
     */
    class Text implements Drawable {
        int x, y;
        String text;
        Color color;
        Font font;

        /**
         * Constructor.
         * Creates a new text instance.
         * @param x x position of the text
         * @param y y position of the text
         * @param text The text
         * @param color Color of the text
         * @param font Font to be used
         */
        public Text(int x, int y, String text, Color color, Font font) {
            this.x = x;
            this.y = y;
            this.text = text;
            this.color = color;
            this.font = font;
        }

        /**
         * Method for drawing the text to the screen.
         */
        public void draw(Graphics2D g2) {
            g2.setColor(color);
            g2.setFont(font);
            g2.drawString(text, x, y);
        }
    }

    /**
     * Draws a rectangle to the screen.
     * @param x x position of the top left corner of the rectangle
     * @param y y position of the top left corner of the rectangle
     * @param width Width of the rectangle
     * @param height Height of the rectangle
     * @param color Color of the rectangle
     */
    public void drawRectangleColor(int x, int y, int width, int height, Color color) {
        Rect r = new Rect(x, y, width, height, color);
        drawables.add(r);
    }

    

    /**
     * Draws text to the screen.
     * @param x x position of the text
     * @param y y position of the text
     * @param text The text to be drawn
     * @param font The font to use
     * @param color The color to use
     */
    public void drawTextFontColor(int x, int y, String text, Font font, Color color) {
        Text t = new Text(x, y, text, color, font);
        drawables.add(t);
    }

    /**
     * Clears the screen.
     * Removes all objects that were being drawn.
     */
    public void clear() {
        drawables = new ArrayList<Drawable>();
    }

    /**
     * Overriden method.
     * Draws all components of the UI.
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i=0; i<drawables.size(); i++) {
            drawables.get(i).draw(g2);
        }
    }


}
