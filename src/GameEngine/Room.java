package GameEngine;

import java.awt.*;
import javax.swing.*;

import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Room extends Canvas {
    private int width, height;
    private JFrame frame;
    private BufferStrategy bs;
    private Graphics g;
    private ArrayList<DrawShape> shapes;
    private Input input;

    /**
     * Creates a new game room
     * @param roomWidth The width of the room in pixels
     * @param roomHeight The height of the room in pixels
     */
    public Room(int roomWidth, int roomHeight) {
        shapes = new ArrayList<DrawShape>();
        
        input = new Input();

        width = roomWidth;
        height = roomHeight;

        // JFrame and Canvas setup
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        // Set the size of the Canvas
        setPreferredSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        // Add the canvas to the frame
        frame.add(this);
        frame.pack();
        frame.setVisible(true);

        setKeyListener();
    }

    /**
     * Sets up the key listener of the room
     */
    public void setKeyListener() {
        addKeyListener(input);
    }

    /**
     * Adds a drawable shape to the list of shapes to draw
     * @param shape The new shape to be drawn
     */
    public void addShape(DrawShape shape) {
        if (shapes.indexOf(shape) == -1) {
            shapes.add(shape);
        }
    }

    /**
     * Renders the game screen based on the current shapes that have to be drawn
     */
    public void render() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }
        g = bs.getDrawGraphics();
        // Draw
        g.clearRect(0,0,width,height);
        // Rectangles
        for (int i=0; i<shapes.size(); i++) {
            DrawShape shape = shapes.get(i);
            if (shape instanceof Rectangle) {
                Rectangle rect = (Rectangle) shape;
                // Draw the rectangle
                g.setColor(rect.getColor());
                g.fillRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                
            } else if (shape instanceof Text) {
                Text text = (Text) shape;
                //Draw the text
                g.setFont(text.getFont());
                g.setColor(text.getColor());
                g.drawString(text.getText(), text.getX(), text.getY());
            } else if (shape instanceof Sprite) {
                Sprite s = (Sprite) shape;
                g.drawImage(s.getImage(), s.getX(), s.getY(), null);
            } else if (shape instanceof Circle) {
                Circle c = (Circle) shape;
                g.setColor(c.getColor());
                g.fillArc(c.getX(), c.getY(), c.getRadius(), c.getRadius(), 0, 360);
            }
        }
        // End draw
        bs.show();
        g.dispose();
        shapes = new ArrayList<DrawShape>();
    }


    

    /**
     * Gets the input object of the room
     * @return The input object
     */
    public Input getInput() {
        return input;
    }
}