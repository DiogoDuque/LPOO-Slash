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
    private Vector2 finger; //posicao onde o slasher ficará se houver um corte (levantar do dedo para cortar a caixa)
    private Vector2 position; //posicao atual do Slasher
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
        this.finger=null;
        if(finger==null)
        {
            System.out.println("Slasher::setFinger() null finger");
            return;
        }

        Vector2[] points = gameWorld.getGameArea().getPoints();
        Vector2 sideB=null, center=null, sideA=null;
        for(int i=0; i<4; i++)
        {
            if(points[i]==position)
            {
                sideA=points[(i+1)%4];
                center=points[(i+2)%4];
                sideB=points[(i+3)%4];
                break;
            }
        }

        //find inclination of the line between position and finger
        Function fa = new Function(position,sideA);
        Function fb = new Function(position,sideB);

        boolean validFinger=false;
        if(center.y<fa.getY(center.x)) {
            if(center.y<fb.getY(center.x)) { //caso 1
                if(finger.y<fa.getY(finger.x) && finger.y<fb.getY(finger.x))
                    validFinger=true;
                else System.out.println("caso 1: false");
            } else { //caso 2
                if(finger.y<fa.getY(finger.x) && finger.y>=fb.getY(finger.x))
                    validFinger=true;
                else System.out.println("caso 2: false");
            }
        } else {
            if(center.y<fb.getY(center.x)) { //caso 3
                if(finger.y>=fa.getY(finger.x) && finger.y<fb.getY(finger.x))
                    validFinger=true;
                else System.out.println("caso 3: false");
            } else { //caso 4
                if(finger.y>=fa.getY(finger.x) && finger.y>=fb.getY(finger.x))
                    validFinger=true;
                else System.out.println("caso 4: false");
            }
        }
        if(!validFinger) //se nao estiver no espaco devido
            return;
        Function funcFinger = new Function(position, finger);
        //corrigir a linha
        Vector2 intersect1, intersect2;
        intersect1 = funcFinger.intersect(new Function(center,sideA));
        intersect2 = funcFinger.intersect(new Function(center,sideB));
        float dist1, dist2;
        dist1=(float)Math.sqrt(Math.pow(intersect1.x-position.x,2)+Math.pow(intersect1.y-position.y,2));
        dist2=(float)Math.sqrt(Math.pow(intersect2.x-position.x,2)+Math.pow(intersect2.y-position.y,2));
        if(dist1>dist2) {
            this.finger = intersect2;
            gameWorld.getGameArea().setToDelete(sideB);
        } else {
            this.finger=intersect1;
            gameWorld.getGameArea().setToDelete(sideA);
        }
    }

    //usar quando iniciar o movimento do slasher TODO
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

    /**
     * TODO usar quando parar o movimento do slasher
     */
    private void finishedMoving(World world)
    {
        //dispose body and bodyDef TODO

        //point to null
        body=null;
        bodyDef=null;
    }
}
