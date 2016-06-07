package com.lpoo.slashhelpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Diogo on 10-05-2016.
 * Creates a function of type: y=m*x+b. Can also detect intersections.
 * Design Pattern: Facade
 */
public class Function {

    private float m;
    private float b;
    private Vector2 min = new Vector2(), max = new Vector2();

    public Function(Vector2 pt1, Vector2 pt2) {
        m = (pt1.y - pt2.y) / (pt1.x - pt2.x);
        b = pt1.y - m * pt1.x;

        if (pt1.x > pt2.x) {
            max.x = pt1.x;
            min.x = pt2.x;
        } else {
            max.x = pt2.x;
            min.x = pt1.x;
        }

        if (pt1.y > pt2.y) {
            max.y = pt1.y;
            min.y = pt2.y;
        } else {
            max.y = pt2.y;
            min.y = pt1.y;
        }
    }


    public float getM() {
        return m;
    }

    protected float getB() {
        return b;
    }

    public float getY(float x) {
        return m * x + b;
    }

    public float getX(float y) {
        return (y - b) / m;
    }

    /**
     * Intersects the two functions and returns the point of intersection.
     * If intersection is not inside the bound (min and max), returns null.
     *
     * @param f Function to intersect with
     * @return Point where both functions intersect or null (see above).
     */
    public Vector2 intersect(Function f) {
        Vector2 point = new Vector2();
        point.x = (f.getB() - b) / (m - f.getM());
        point.y = m * point.x + b;

        if (point.x < min.x || point.x > max.x || point.y < min.y || point.y > max.y) //if out of bounds
            return null;
        return point;
    }


}