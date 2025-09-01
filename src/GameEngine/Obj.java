package GameEngine;

public class Obj {
    public static int objectCount = 0;

    public double x, y;

    private int collisionBoxWidth, collisionBoxHeight;

    private boolean visible = true;

    /**
     * Constructor.
     * Adds one to the number of active objects.
     * Sets initial collision box dimension to 0x0.
     * Sets initial coordinates to 0, 0
     */
    public Obj() {
        objectCount++;
        setCollisionBoxDimensions(0, 0);
        x = 0;
        y = 0;
        Functions.game.addObj(this);
    }

    /**
     * Resets the visibility of the object.
     * If visibility is true then the object is drawn and otherwise it isn't
     * @param isVisible Whether the object is visible or not
     */
    public final void setVisible(boolean isVisible) {
        visible = isVisible;
    }

    /**
     * Gets the visiblity of the object
     * @return True if the object is visible and false otherwise
     */
    public final boolean getVisibility() {
        return visible;
    }

    /**
     * Sets the dimensions of the collision box of the object
     * @param width The width of the collision box
     * @param height The height of the collision box
     */
    public final void setCollisionBoxDimensions(int width, int height) {
        collisionBoxWidth = width;
        collisionBoxHeight = height;
    }

    /**
     * The main tick function of the object.
     * Has to be overwritten to be used
     */
    public void step() {}

    /**
     * The main draw function of the object.
     * Has to be overwritten to be used
     */
    public void draw() {}

    /** 
     * Fetches the collision box width of the object
     */
    public final int getCollisionBoxWidth() {
        return collisionBoxWidth;
    }

    /** 
     * Fetches the collision box height of the object
     */
    public final int getCollisionBoxHeight() {
        return collisionBoxHeight;
    }

    public final void destroySelf() {
        Functions.game.deleteObj(this);
    }

    /**
     * Checks if this object is colliding with another object at a certain position
     * @param xx The x position where to check
     * @param yy The y position where to check
     * @param objType The string representing the class of the object type it might be colliding with
     * @return true if it's colliding and false otherwise
     */
    public final boolean isCollidingAt(double xx, double yy, String objType) {
        double oldX = x;
        double oldY = y;
        x = xx;
        y = yy;
        for (int i=0; i<Functions.game.getObjs().size(); i++) {
            Obj obj = Functions.game.getObjs().get(i);
            if (!obj.equals(this) && obj.getClass().getName().equals(objType)) {
                if (Functions.checkCollision(this, obj)) {
                    x = oldX;
                    y = oldY;
                    return true;
                }
            }
        }
        x = oldX;
        y = oldY;
        return false;
    }
}
