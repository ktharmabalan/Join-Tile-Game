package ca.codemake.join.ui;

/**
 * Created by Kajan on 4/17/2015.
 */
public class Box {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public boolean contains() {
        return x > this.x - width / 2 && x < this.x + width / 2 &&
                y > this.y - height / 2 && y < this.y + height / 2;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }
}
