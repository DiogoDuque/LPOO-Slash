package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Diogo on 30-05-2016.
 */
public class GameAreaTest {

    private World world;
    private GameArea gameArea;

    public GameAreaTest()
    {
        world = new World(new Vector2(0,0),false);
    }

    @Test
    public void testImpossibleLines()
    {
      //  Vector2 pt1=new Vector2(25,25),pt2=new Vector2(25,175),pt3=new Vector2(225,175),pt4=new Vector2(225,25);
        //gameArea = new GameArea(pt1,pt2,pt3,pt4,world,);
        //Vector2[] pts = gameArea.getPoints();
        //expects minor alterations so the lines that unite points can be be of format: y=m*x+b
        //assertNotEquals(pts[0].x,pts[1].x);
        //assertNotEquals(pts[2].x,pts[3].x);
    }

}