package GameEngine;

import java.util.ArrayList;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    private int width, height;
    private int fps;
    private Timer tickTimer;
    private ArrayList<Obj> objs;
    private Room room;
    private boolean running;

    /** 
     * Creates a new game 
     * @param roomWidth The width of the game screen in pixels
     * @param roomHeight The height of the game screen in pixels
     * @param gameSpeedFPS The frames per second the game should run at
     * */
    public Game(int roomWidth, int roomHeight, int gameSpeedFPS) {
        width = roomWidth;
        height = roomHeight;
        fps = gameSpeedFPS;
        room = new Room(width, height);
        objs = new ArrayList<Obj>();
        Functions.game = this;
        running = true;
    }

    /**
     * Class for running the game at fps speed
     */
    public class Tick implements ActionListener{
        /** 
         * Does one tick
         */
        public void actionPerformed(ActionEvent e) {
            step();
            draw();
        }
    }

    /**
     * Starts the game
     */
    public void run() {
        tickTimer = new Timer((int) (1000.0/fps), new Tick());
        tickTimer.start();
        while (running) {}
    }

    /**
     * Runs the step method of all active objects
     */
    public void step() {
        for (int i=0; i<objs.size(); i++) {
            Obj obj = objs.get(i);
            obj.step();
        }
        room.getInput().removeFromPressed();
    }

    /**
     * Runs the draw method of all active objects
     */
    public void draw() {
        for (int i=0; i<objs.size(); i++) {
            Obj obj = objs.get(i);
            if (obj.getVisibility()) {
                obj.draw();
            }
        }
        room.render();
    }

    /**
     * Adds a new object in to the game
     * @param obj The new object to be added
     */
    public void addObj(Obj obj) {
        if (objs.indexOf(obj) == -1) {
            objs.add(obj);
        }
    }

    /**
     * Removes an object from the game
     * @param obj The object to remove
     */
    public void deleteObj(Obj obj) {
        if (objs.indexOf(obj) != -1) {
            objs.remove(obj);
        }
    }

    /**
     * Gets the room of the current game
     * @return The room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Gets all the active objects in the game
     * @return All the active objects
     */
    public ArrayList<Obj> getObjs() {
        return objs;
    }
}
