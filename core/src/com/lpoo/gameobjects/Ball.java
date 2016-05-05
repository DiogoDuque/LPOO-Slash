package com.lpoo.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameworld.GameRenderer;

/**
 * Created by Diogo on 26-04-2016.
 */
public class Ball {
    private BodyDef bodyDef;
    private Body body;

    private int radius;

    public Ball(float xPos, float yPos, int radius, World world) {
        this.radius = radius;

        //criar body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; //é dinamico (sofre acao de forcas)
        bodyDef.position.set(GameRenderer.resize(new Vector2(xPos,yPos)));
        bodyDef.linearVelocity.set(-20,0);
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

    /*public void update(float delta) {
        position.add(velocity.cpy().scl(delta));
    }*/

    public void onClick() {
        Gdx.app.log("Ball","OnClick");
    }

    public float getX() { return bodyDef.position.x; }

    public float getY() { return bodyDef.position.y; }

    public int getRadius() { return radius; }

    public Body getBody() {return body;}
}
