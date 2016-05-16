package com.lpoo.slashhelpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Diogo on 10-05-2016.
 */
public class Function {

    private float m;
    private float b;

    public Function(Vector2 pt1, Vector2 pt2)
    {
        m = (pt1.y - pt2.y) / (pt1.x - pt2.x);
        b = pt1.y - m * pt1.x;
    }


    protected float getM() {
            return m;
        }

    protected float getB() {
            return b;
    }

    public float getY(float x)
    {
        return m*x+b;
    }

    public float getX(float y)
    {
        return (y-b)/m;
    }

    /**
     * Intersects both functions using Gauss' Elimination. 1*y1=m1*x1+b1, 1*y2=m2*x2+b2
     *     |-     -|      |-  -|      |- -|
     * A = | 1  m1 |  b = | b1 |  x = | y |
     *     | 1  m2 |      | b2 |      | x |
     *     |-     -|      |-  -|      |- -|
     * @param f Function to intersect with
     * @return Point where both functions intersect
     */
    public Vector2 intersect(Function f)
    {
        Vector2 point = new Vector2();
        point.x=(f.getB()-b)/(m-f.getM());
        point.y=m*point.x+b;
        return point;
    }

}
