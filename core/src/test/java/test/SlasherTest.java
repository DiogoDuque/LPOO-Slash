package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameworld.GameWorld;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Diogo on 30-05-2016.
 */
public class SlasherTest {

    private GameWorld gameWorld;
    private World world;
    private GameArea gameArea;
    private Slasher slasher;

    public SlasherTest()
    {
        World world=new World(new Vector2(0,0),false);
    }

    @Test
    public void testFinger()
    {
        Vector2 pt1=new Vector2(15,35),pt2=new Vector2(25,175),pt3=new Vector2(215,185),pt4=new Vector2(235,15);
        gameArea = new GameArea(pt1,pt2,pt3,pt4,gameWorld.getWorld()); //gameArea ligeiramente obtusa (no angulo do vertice do slasher)
        slasher=new Slasher(pt2,gameWorld);
        gameWorld=new GameWorld(world,gameArea,slasher);

        slasher.setFinger(null);
        //assert getFinger==null
        slasher.setFinger(new Vector2(14,35));
        //assert getFinger==null
        slasher.setFinger(new Vector2(16,35));
        //assert getFinger!=null

        assertFalse(true); //TODO FINISH THIS

    }

}