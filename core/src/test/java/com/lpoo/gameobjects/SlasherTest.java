package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameworld.GameWorld;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Diogo on 30-05-2016.
 */
public class SlasherTest {

    @Test
    public void testFinger()
    {
        //gameArea ligeiramente obtusa (no angulo do vertice do slasher)
        Vector2 pt1=new Vector2(15,35),pt2=new Vector2(25,175),pt3=new Vector2(215,185),pt4=new Vector2(235,15);
        GameWorld gameWorld=new GameWorld(pt1,pt2,pt3,pt4,pt2);

        Slasher slasher = gameWorld.getSlasher();

        slasher.setFinger(null);
        //assert getFinger==null
        slasher.setFinger(new Vector2(14,35));
        assertSame(slasher.getFinger(),null);
        slasher.setFinger(new Vector2(16,35));
        assertNotSame(slasher.getFinger(),null);

    }

}