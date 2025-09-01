package GameEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Functions {
    public static Game game;

    /**
     * Draws a cirlce on to the screen
     * @param x X position of the center of the circle
     * @param y Y position of the center of the circle
     * @param radius Size of the radius of the circle
     * @param color Color of the circle
     */
    public void drawCircle(double x, double y, double radius, Color color) {
        Circle circle = new Circle((int) x, (int) y, (int) radius, color);
        game.getRoom().addShape(circle);
    }

    /**
     * Draws an image on to the screen
     * @param imageLocation The string representing the location of the image file
     * @param x The x position of the top left corner of the image
     * @param y The y position of the top left corner of the image
     */
    public static void drawImage(String imageLocation, double x, double y) {
        Image img = null;
        try {
            img = ImageIO.read(new File(imageLocation));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        Sprite spr = new Sprite((int) x, (int) y, img);
        game.getRoom().addShape(spr);
    }

    /**
     * Draws a rectangle on to the field
     * @param leftX The x position of the top left corner of the rectangle
     * @param topY The y position of the top left corner of the rectangle
     * @param rectWidth The width of the rectangle
     * @param rectHeight The height of the rectangle
     * @param color The color of the rectangle
     */
    public static void drawRectangle(double leftX, double topY, int rectWidth, int rectHeight, Color color) {
        Rectangle rect = new Rectangle((int) leftX, (int) topY, rectWidth, rectHeight, color);
        game.getRoom().addShape(rect);
    }

    /**
     * Checks if a key was pressed
     * @param key The ID of the key to check
     * @see Input.java to find the key IDs
     * @return true if the key was pressed and false if it wasn't
     */
    public static boolean checkKeyPressed(int key) {
        return game.getRoom().getInput().isPressed(key);
    }

    /**
     * Checks if a key is being held down
     * @param key The ID of the key to check
     * @see Input.java to find the key IDs
     * @return true if the key is being held down and false otherwise
     */
    public static boolean checkKeyDown(int key) {
        return game.getRoom().getInput().isDown(key);
    }

    /**
     * Gets the width of the game room (in pixels)
     * @return Width in pixels
     */
    public static int getRoomWidth() {
        return game.getRoom().getWidth();
    }

    /**
     * Gets the height of the game room (in pixels)
     */
    public static int getRoomHeight() {
        return game.getRoom().getHeight();
    }

    /**
     * Creates a color given for given RGB values
     * @param red The amount of red color (0-255)
     * @param green The amount of green color (0-255)
     * @param blue The amount of blue color (0-255)
     * @return The color
     */
    public static Color makeColorRGB(int red, int green, int blue) {
        return new Color(red, green, blue);
    }

    /**
     * Checks if 2 objects are coliding
     * @param obj1 The first object to check for
     * @param obj2 The second object to check for
     * @return true if the objects are colliding and false if they aren't
     */
    public static boolean checkCollision(Obj obj1, Obj obj2) {
        int x1 = (int) Math.round(obj1.x);
        int y1 = (int) Math.round(obj1.y);
        int x2 = (int) Math.round(obj2.x);
        int y2 = (int) Math.round(obj2.y);
        int width1 = obj1.getCollisionBoxWidth();
        int height1 = obj1.getCollisionBoxHeight();
        int width2 = obj2.getCollisionBoxWidth();
        int height2 = obj2.getCollisionBoxHeight();
        // First check one way
        // Check x
        if (x2 > x1 && x2 < x1+width1 || x2+width2 > x1 && x2+width2 < x1+width1) {
            // Check y
            if (y2 > y1 && y2 < y1+height1 || y2+height2 > y1 && y2+height2 < y1+height1) {
                return true;
            }
        }
        // Now check the other way
        // Check x
        if (x1 > x2 && x1 < x2+width2 || x1+width1 > x2 && x1+width1 < x2+width2) {
            // Check y
            if (y1 > y2 && y1 < y2+height2 || y1+height1 > y2 && y1+height1 < y2+height2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the first created instance of an object with the given class name
     * @param className The name of the class that controls this object
     * @return The first instance of the class
     */
    public static Obj getInstanceOfClass(String className) {
        for (int i=0; i<game.getObjs().size(); i++) {
            Obj obj = game.getObjs().get(i);
            if (obj.getClass().getName().equals(className)) {
                return obj;
            }
        }
        return null;
    }

    /**
     * Finds the sign of a value
     * @param val The value to find the sign of
     * @return -1 if val < 0, 0 if val == 0 and 1 if val > 0
     */
    public static int sign(double val) {
        if (val > 0) {
            return 1;
        } else if (val == 0) {
            return 0;
        } else {
            return -1;
        }
    } 

    /**
     * Draws text to the screen
     * @param text The text to be drawn
     * @param x The x position of the top left corner of the first letter
     * @param y The y position of the top left corner od the first letter
     * @param fontType The font type expressed as a string
     * @param fontSize The size of the font
     * @param color The color of the text
     */
    public static void drawText(String text, int x, int y, String fontType, int fontSize, Color color) {
        Font font = new Font(fontType, Font.PLAIN, fontSize);
        Text textDraw = new Text(x, y, text, font, color);
        game.getRoom().addShape(textDraw);
    }
}
