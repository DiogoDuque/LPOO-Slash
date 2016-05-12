package com.lpoo.slashhelpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Diogo on 10-05-2016.
 */
public class Function {

    private float m;
    private float b;
    private float theta;

    public Function(Vector2 pt1, Vector2 pt2)
    {
        m = (pt1.y-pt2.y)/(pt1.x-pt2.x);
        b = pt1.y - m*pt1.x;
        theta = (float)Math.atan(m);
    }

    public float getM() {
        return m;
    }

    public float getB() {
        return b;
    }

    public float getTheta() {return theta<0 ? theta+=2*Math.PI : theta; }

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

        /*float matrix[][] = new float[2][3];
        matrix[0][0] = 1;
        matrix[1][0] = 1;
        matrix[0][1] = m;
        matrix [1][1] = f.getM();
        matrix [0][2] = b;
        matrix [1][2] = f.getB();

        for(int i=0; i<3; i++)
            matrix[1][i] -= matrix[1][0]*matrix[0][i];

        point.x=matrix[1][2]/matrix[1][1]; //0+m2*x=b2 <=> x=b2/m2
        point.y=matrix[0][2]-matrix[0][1]*point.x;
*/
        point.x=(f.getB()-b)/(m-f.getM());
        point.y=m*point.x+b;

        return point;
    }

}
