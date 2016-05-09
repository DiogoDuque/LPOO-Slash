package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Diogo on 09-05-2016.
 */
public class Slasher {
    private Vector2 position;
    private BodyDef bodyDef;
    private Body body;
    private final static float radius = 2.5f;
    private final static float velocity = 10;


    public Slasher(Vector2 pos) {
        body=null;
        bodyDef=null;
        position=pos;
    }

    public Vector2 getPosition() {return position;}

    public float getRadius() {return velocity;}

    //usar quando iniciar o movimento do slasher
    private void startedMoving(World world)
    {
        //criar body
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; //é dinamico (sofre acao de forcas)
        bodyDef.position.set(position);
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

    //TODO //usar quando parar o movimento do slasher
    private void finishedMoving(World world)
    {
        //dispose body and bodyDef TODO

        //point to null
        body=null;
        bodyDef=null;
    }
}
