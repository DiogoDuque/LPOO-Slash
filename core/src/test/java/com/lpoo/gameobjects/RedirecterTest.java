package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameworld.GameWorld;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by Diogo on 06-06-2016.
 */
public class RedirecterTest {

    @Test
    public void testChangeBallDirection()
    {
        Vector2 pt1=new Vector2(20,20),pt2=new Vector2(30,175),pt3=new Vector2(235,175),pt4=new Vector2(225,20);
        GameWorld gameWorld=new GameWorld(pt1,pt2,pt3,pt4,pt2);
        //gameWorld.setRedirecter(new Redirecter(gameWorld.getGameArea().getPoints()));

        Slasher slasher = gameWorld.getSlasher();

        //ball nearby Redirecter
        Ball ball = gameWorld.getBalls().get(0);
        Vector2 oldDirection = ball.getBody().getLinearVelocity().cpy();
        ball.getBody().setTransform(100, 100, 0);

        gameWorld.update(0);

        assertNotEquals(oldDirection,ball.getBody().getLinearVelocity());
    }
}