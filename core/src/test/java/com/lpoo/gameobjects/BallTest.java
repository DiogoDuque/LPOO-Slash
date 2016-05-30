package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Created by Diogo on 29-05-2016.
 */
public class BallTest {

    private World world;
    private int x,y;
    private Ball ball;

    public BallTest()
    {
        world=new World(new Vector2(0, 0), false);
        x=60;
        y=20;
    }

    @Test
    public void testBallBasics()
    {
        ball = new Ball(x,y,world);
        assertSame(x,(int)ball.getX()); //xPosition
        assertSame(y,(int)ball.getY()); //yPosition
        assertSame(x,(int)ball.getBody().getPosition().x); //xPosition
        assertSame(y,(int)ball.getBody().getPosition().y); //yPosition
        //for full coverage
        assertTrue((Ball.getRadius()-ball.getRadius())==0);
    }
}