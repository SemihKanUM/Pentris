package PentrisGame;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.LabelView;
import java.awt.event.*;

public class UI {
    JFrame frame;
    Canvas canvas;
    Input input;
    BoardComponent boardComponent;
    

    /**
     * Constructor.
     * Creates a new UI object. 
     * @param width The width of the UI in pixels
     * @param height The height of the UI in pixels
     * @param name The name of the UI
     */
    public UI(int width, int height, String name) {
            frame = new JFrame();
            input = new Input();
            boardComponent = new BoardComponent();

            JPanel panelBoard = new JPanel();

            panelBoard.setLayout(new BorderLayout());
            panelBoard.add(boardComponent, BorderLayout.CENTER);
            frame.add(panelBoard, BorderLayout.CENTER);
            frame.pack();
            
            frame.setLocation(400, 300);
            frame.setSize(width, height);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setTitle(name);
            frame.setVisible(true);
            frame.addKeyListener(input);
              

    

        }
        

    public BoardComponent getBoardComponent() {
        return boardComponent;
    }

    

    /**
     * Checks if a key was pressed.
     * @param key The key to check. see {@code Input}
     * @return {@code true} if it was pressed and {@code false} otherwise
     */
    public boolean checkKeyPressed(int key) {
        return input.isPressed(key);
    }

    /**
     * Checks if a key is being held down.
     * @param key The key to check. see {@code Input}
     * @return {@code true} if it is being helf down and {@code false} otherwise
     */
    public boolean checkKeyDown(int key) {
        return input.isDown(key);
    }

    /**
     * Resets the inputs. Removes all the pressed keys in the pressed keys list.
     */
    public void resetInputs() {
        input.removeFromPressed();
    }
    
}
