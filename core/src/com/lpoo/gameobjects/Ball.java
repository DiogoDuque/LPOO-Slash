package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameworld.GameRenderer;

import java.util.Random;

/**
 * Created by Diogo on 26-04-2016.
 */
public class Ball {
    private BodyDef bodyDef;
    private Body body;

    private int radius;

    /**
     * Constructor.
     * @param xPos x component of the ball's position.
     * @param yPos y component of the ball's position.
     * @param radius radius of the ball.
     * @param world World in which this ball was created.
     */
    public Ball(float xPos, float yPos, int radius, World world) {
        this.radius = radius;

        //criar body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; //é dinamico (sofre acao de forcas)
        bodyDef.position.set(GameRenderer.resize(new Vector2(xPos,yPos)));
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
     * Will set the ball with a linearVelocity of 20 in a random direction
     */
    private void setRandomMovement()
    {
        //handle the values of the x and y components of velocity
        float velocity = 30;
        Random rand = new Random();
        float vx = velocity*rand.nextFloat();
        float vy=(float)Math.sqrt(Math.pow(velocity,2) - Math.pow(vx,2));
        //handles the sector for which to send the ball
        int direction = rand.nextInt(4);
        switch(direction)
        {
            case 0: bodyDef.linearVelocity.set(vx,vy); break;
            case 1: bodyDef.linearVelocity.set(-vx,vy); break;
            case 2: bodyDef.linearVelocity.set(vx,-vy); break;
            case 3: bodyDef.linearVelocity.set(-vx,-vy); break;
        }
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
     *
     * @return ball's radius.
     */
    public int getRadius() { return radius; }

    /**
     *
     * @return ball's Body.
     */
    public Body getBody() {return body;}
}
