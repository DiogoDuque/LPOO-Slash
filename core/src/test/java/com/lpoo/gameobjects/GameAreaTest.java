package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameworld.GameWorld;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Created by Diogo on 30-05-2016.
 */
public class GameAreaTest {

    @Test
    public void testBasics()
    {
        Vector2 pt1 = new Vector2(30,20), pt2 = new Vector2(20,170), pt3 = new Vector2(230,160), pt4 = new Vector2(220,20);
        GameWorld gameWorld = new GameWorld(pt1,pt2,pt3,pt4,pt1);
        GameArea gameArea = gameWorld.getGameArea();

        Vector2[] pts = gameArea.getPoints();
        assertEquals(pt1,pts[0]);
        assertEquals(pt2,pts[1]);
        assertEquals(pt3,pts[2]);
        assertEquals(pt4,pts[3]);

        assertSame(gameArea.getToDelete(),null);
        Vector2 toDelete = pt2;
        gameArea.setToDelete(pt2);
        assertSame(gameArea.getToDelete(),toDelete);

        gameArea.dispose();
    }

    @Test
    public void testImpossibleLines()
    {
        Vector2 pt1 = new Vector2(20,20), pt2 = new Vector2(20,170), pt3 = new Vector2(230,170), pt4 = new Vector2(230,20);
        GameWorld gameWorld = new GameWorld(pt1,pt2,pt3,pt4,pt1);
        Vector2[] pts = gameWorld.getGameArea().getPoints();
        assertNotSame(pts[0].x,pts[1].x);
        assertNotSame(pts[2].x,pts[3].x);
    }

}