package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

/**
 * Created by Diogo on 26-04-2016.
 */
public class Ball {
    private BodyDef bodyDef;
    private Body body;
    private final static int radius = 4;
    private final static float velocity = 60;

    /**
     * Constructor.
     * @param xPos x component of the ball's position.
     * @param yPos y component of the ball's position.
     * @param world World in which this ball was created.
     */
    public Ball(float xPos, float yPos, World world) {
        //criar body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; //é dinamico (sofre acao de forcas)
        bodyDef.position.set(new Vector2(xPos,yPos));
        setRandomMovement();
        body = world.createBody(bodyDef);

        //criar forma de caixa...
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(radius,radius);
        //...e criar uma fixture atraves dessa forma
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution=1.0f;
        body.createFixture(fixtureDef);

    }

    /**
     * Will set the ball with a linearVelocity of 'velocity' in a random direction
     */
    private void setRandomMovement()
    {
        Random rand = new Random();
        double rad = 2*Math.PI*rand.nextFloat();
        float x=(float)Math.cos(rad), y=(float)Math.sin(rad);
        bodyDef.linearVelocity.set(velocity*x,velocity*y);
    }

    /**
     *
     * @return x component of the ball's position.
     */
    public float getX() { return bodyDef.position.x; }

    /**
     *
     * @return y component of the ball's position.
     */
    public float getY() { return bodyDef.position.y; }

    /**
     * The conversion is made using GameRenderer.resizeY().
     * @return ball's radius.
     */
    public static float getRadius() { return radius; }

    /**
     *
     * @return ball's Body.
     */
    public Body getBody() {return body;}

}
