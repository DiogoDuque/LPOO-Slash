package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.Function;

/**
 * Created by Diogo on 09-05-2016.
 */
public class Slasher {
    private GameWorld gameWorld;
    private Vector2 finger;
    private Vector2 position;
    private BodyDef bodyDef;
    private Body body;
    private final static float radius = Ball.getRadius();
    private final static float velocity = 10;

    public Slasher(Vector2 pos, GameWorld gameWorld) {
        body=null;
        bodyDef=null;
        finger=null;
        position=pos;
        this.gameWorld=gameWorld;
    }

    public Vector2 getPosition() {return position;}

    public Vector2 getFinger() {
        return finger;
    }

    public void setFinger(Vector2 finger) {
        if(finger==null)
        {
            System.out.println("Slasher::setFinger() null finger");
            this.finger=null;
            return;
        }

        Vector2[] points = gameWorld.getGameArea().getPoints();
        Vector2 sideB=null, center=null, sideA=null;
        for(int i=0; i<4; i++)
        {
            if(points[i]==position)
            {
                sideB=points[(i+1)%4];
                center=points[(i+2)%4];
                sideA=points[(i+3)%4];
                break;
            }
        }

        //find inclination of the line between position and finger
        Function fingerFunc = new Function(position,finger);

        float theta = fingerFunc.getTheta();
        float beta = new Function(position,sideB).getTheta();
        float alfa = new Function(position,sideA).getTheta();
        float middleAngle = new Function(position,center).getTheta(); //angle of Shasler's opposite vertice

        //compare inclinations to see if can be drawn inside the box
        if(beta<theta && theta<alfa) { //se beta<theta<alfa
            System.out.println("Slasher::setFinger() beta<theta<alfa");

            if(theta<middleAngle)
                this.finger = fingerFunc.intersect(new Function(sideB,center));
            else this.finger = fingerFunc.intersect(new Function(sideA,center));

        } else if (alfa<theta && theta<beta) { //se alfa<theta<beta
            System.out.println("Slasher::setFinger() alfa<theta<beta");
            if(theta<middleAngle)
                this.finger = fingerFunc.intersect(new Function(sideA,center));
            else this.finger = fingerFunc.intersect(new Function(sideB,center));

        } else {
            System.out.println("Slasher::setFinger() out of box");
            this.finger=null;
        }

        System.out.println("Slasher::setFinger() finger="+this.finger);
    }

    //usar quando iniciar o movimento do slasher TODO completar
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

    //moving

    //TODO usar quando parar o movimento do slasher
    private void finishedMoving(World world)
    {
        //dispose body and bodyDef TODO

        //point to null
        body=null;
        bodyDef=null;
    }
}
