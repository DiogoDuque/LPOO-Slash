package com.lpoo.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Diogo on 26-04-2016.
 */
public class Ball {
    private Vector2 position;
    private Vector2 velocity;
    private BodyDef bodyDef;

    private int radius;

    public Ball(float xPos, float yPos, int radius, World world) {
        this.radius = radius;
        position = new Vector2(xPos,yPos);
        velocity = new Vector2(30,0);

        //criar body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; //é dinamico (AKA sofre acao de forcas)
        bodyDef.position.set(xPos, yPos);
        Body body = world.createBody(bodyDef);

        //criar forma de caixa...
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(1,1); //-------------------------------
        //dynamicBox.setRadius(radius); ------------------------------------trocar AQUI
        //...e criar uma fixture atraves dessa forma
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        body.createFixture(fixtureDef);

    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
    }

    public void onClick() {
        Gdx.app.log("Ball","OnClick");
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getRadius() {
        return radius;
    }
}
