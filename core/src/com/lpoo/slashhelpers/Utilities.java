package com.lpoo.slashhelpers;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Diogo on 01-06-2016.
 */
public final class Utilities {

    /**
     *
     * @param pt1
     * @param pt2
     * @return Euclidian distance between the two points.
     */
    public static double distance(Vector2 pt1, Vector2 pt2)
    {
        return Math.sqrt(Math.pow(pt1.x-pt2.x,2)+Math.pow(pt1.y-pt2.y,2));
    }
}
