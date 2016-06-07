package com.lpoo.slashhelpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Diogo on 10-05-2016.
 * Creates a function of type: y=m*x+b. Can also detect intersections.
 * Design Pattern: Facade
 */
public class Function {

    /**
     * slope
     */
    private float m;
    /**
     * height
     */
    private float b;
    /**
     * minimum and maximum x and y
     */
    private Vector2 min = new Vector2(), max = new Vector2();

    /**
     * Constructor
     * @param pt1 one point
     * @param pt2 another point
     */
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

    /**
     *
     * @return m
     */
    public float getM() {
        return m;
    }

    /**
     *
     * @return b
     */
    protected float getB() {
        return b;
    }

    /**
     *
     * @param x
     * @return y(x)
     */
    public float getY(float x) {
        return m * x + b;
    }

    /**
     *
     * @param y
     * @return x(y)
     */
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