package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slash.GameOverScreen;

import org.junit.Test;

import java.util.Date;

import static com.lpoo.slashhelpers.Utilities.changeScreen;
import static org.junit.Assert.*;

/**
 * Created by Diogo on 30-05-2016.
 */
public class SlasherTest {

    @Test
    public void testBasics()
    {
        Vector2 pt1=new Vector2(20,20),pt2=new Vector2(30,175),pt3=new Vector2(235,175),pt4=new Vector2(225,20);
        GameWorld gameWorld=new GameWorld(pt1,pt2,pt3,pt4,pt2);

        Slasher slasher = gameWorld.getSlasher();

        assertEquals(slasher.getPosition(),pt2);


    }

    @Test
    public void testFinger()
    {
        //gameArea ligeiramente obtusa (no angulo do vertice do slasher)
        Vector2 pt1=new Vector2(15,35),pt2=new Vector2(25,175),pt3=new Vector2(215,185),pt4=new Vector2(235,15);
        GameWorld gameWorld=new GameWorld(pt1,pt2,pt3,pt4,pt2);

        Slasher slasher = gameWorld.getSlasher();

        //invalid finger
        slasher.setFinger(new Vector2(14,35));
        assertSame(slasher.getFinger(),null);

        //valid finger
        slasher.setFinger(new Vector2(16,35));
        assertNotSame(slasher.getFinger(),null);

    }

    @Test public void testSlashGameArea()
    {
        /*
           ________
          \        \
           \        \
            \________\
         */
        Vector2 pt1=new Vector2(20,20),pt2=new Vector2(30,175),pt3=new Vector2(235,175),pt4=new Vector2(225,20);
        GameWorld gameWorld=new GameWorld(pt1,pt2,pt3,pt4,pt2);

        Slasher slasher = gameWorld.getSlasher();

        gameWorld.getBalls().get(0).dispose();
        gameWorld.getBalls().remove(0);

        slasher.setFinger(new Vector2(100, 100));

        String message="OK";
        gameWorld.startSlashMovement();
        while(message=="OK")
        {
            //lines of code extracted from GameWorld::update
            gameWorld.getWorld().step(1f / 60f, 6, 2);
            message=slasher.isMoving();

        }
        assertEquals(message,"Slasher End Reached");
    }

    @Test public void testGameOverOnBallCollision()
    {
        /*
           ________
          \        \
           \        \
            \________\
         */
        Vector2 pt1=new Vector2(20,20),pt2=new Vector2(30,175),pt3=new Vector2(235,175),pt4=new Vector2(225,20);
        GameWorld gameWorld=new GameWorld(pt1,pt2,pt3,pt4,pt2);

        Slasher slasher = gameWorld.getSlasher();

        //ball on collision course
        Ball ball = gameWorld.getBalls().get(0);
        ball.getBody().setLinearVelocity(0,0);
        ball.getBody().setTransform(100,100,0);

        //slash on collision
        slasher.setFinger(new Vector2(100, 100));

        String message="OK";
        gameWorld.startSlashMovement();
        while(message=="OK")
        {
            //lines of code extracted from GameWorld::update
            gameWorld.getWorld().step(1f / 60f, 6, 2);
            message=slasher.isMoving();

        }
        assertEquals("Game Over",message);

    }
}