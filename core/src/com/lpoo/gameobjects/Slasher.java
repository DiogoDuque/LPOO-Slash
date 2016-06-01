package com.lpoo.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.Function;

/**
 * Created by Diogo on 09-05-2016.
 */
public class Slasher {
    private GameWorld gameWorld;
    private Vector2 finger; //posicao onde o slasher ficará se houver um corte (levantar do dedo para cortar a caixa)
    private Vector2 position; //posicao atual do Slasher
    private Body body, bodyPath;
    private final static float radius = Ball.getRadius();
    private final static float velocity = 10;

    public Slasher(Vector2 pos, GameWorld gameWorld) {
        body=null;
        bodyPath=null;
        finger=null;
        position=pos;
        this.gameWorld=gameWorld;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getBodyPosition() {return body.getPosition();}

    public Vector2 getFinger() {
        return finger;
    }

    public void setFinger(Vector2 finger) {
        if(body!=null)
            return;
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

        /*   center
     .       /|  .
      .    /  | .       Now we will see if the finger's relative position is the same as the center, when compared to fa and fb.
       . /    |.        Example: in this example, center is above fa and fb. So, finger should also be above those two.
       A\    / B        The boundaries, for this example, "are continued" with dots, so that we can see them clearly.
         \  /           Concluding, if the finger is under any of those lines, the Slasher line wouldn't be drawn.
          \/
          SLASHER
         */

        Function fa = new Function(position,sideA);
        Function fb = new Function(position,sideB);

        boolean validFinger=false;
        if(center.y<fa.getY(center.x)) {
            if(center.y<fb.getY(center.x)) { //center under fa and fb
                if(finger.y<fa.getY(finger.x) && finger.y<fb.getY(finger.x))
                    validFinger=true;
            } else { //center under fa and above fb
                if(finger.y<fa.getY(finger.x) && finger.y>fb.getY(finger.x))
                    validFinger=true;
            }
        } else {
            if(center.y<fb.getY(center.x)) { //center above fa and under fb
                if(finger.y>=fa.getY(finger.x) && finger.y<fb.getY(finger.x))
                    validFinger=true;
            } else { //center above fa and fb
                if(finger.y>=fa.getY(finger.x) && finger.y>fb.getY(finger.x))
                    validFinger=true;
            }
        }
        if(!validFinger) //if it's not in a valid place to draw the line
            return;

        Function funcFinger = new Function(position, finger);
        System.out.println("Slasher::getFinger() ang = " + Math.atan(funcFinger.getM()));
        //correct the line drawn, so that it stops when intersects a gameArea's edge.
        Vector2 intersect1, intersect2;
        intersect1 = new Function(center,sideA).intersect(funcFinger);
        intersect2 = new Function(center,sideB).intersect(funcFinger);
        float dist1=0, dist2=0;
        if(intersect1!=null && intersect2!=null)
        {
            dist1=(float)Math.sqrt(Math.pow(intersect1.x-position.x,2)+Math.pow(intersect1.y-position.y,2));
            dist2=(float)Math.sqrt(Math.pow(intersect2.x-position.x,2)+Math.pow(intersect2.y-position.y,2));
        }
        if(dist1>dist2 || intersect1==null) {
            this.finger = intersect2;
            gameWorld.getGameArea().setToDelete(sideB);
        } else {
            this.finger=intersect1;
            gameWorld.getGameArea().setToDelete(sideA);
        }
    }

    //usar quando iniciar o movimento do slasher
    public void startedMoving()
    {
        Vector2 direction = new Vector2(finger.x-position.x,finger.y-position.y);
        float norm = (float)Math.sqrt(Math.pow(direction.x,2)+Math.pow(direction.y,2));
        direction.x/=norm; //unitary
        direction.y/=norm; //unitary

        //criar bola
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x+2*direction.x,position.y+2*direction.y); //starts a bit ahead so doesnt instantly collide w/ gameArea
        bodyDef.linearVelocity.set(velocity*direction.x,velocity*direction.y); //def linearVelocity
        body = gameWorld.getWorld().createBody(bodyDef);
        PolygonShape dynamicBox = new PolygonShape();
        dynamicBox.setAsBox(radius,radius);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = dynamicBox;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution=1.0f;
        body.createFixture(fixtureDef);

        //criar path
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(new Vector2());
        bodyPath = gameWorld.getWorld().createBody(bodyDef);
    }

    /**
     * Called as an animation of the Slasher
     * @return true if the ball is moving and has not collided, false if collided.
     */
    public boolean isMoving()
    {
        if(/**/false) //TODO Check if collision with contactListener
        {
            finishedMoving();
            return false;
        }
        //pre-calculations
        Vector2 midPoint = new Vector2((body.getPosition().x+position.x)/2,(body.getPosition().y+position.y)/2);
        double distancePTP = Math.sqrt(Math.pow((body.getPosition().x-position.x),2)+Math.pow((body.getPosition().y-position.y),2));
        double angle = Math.atan((body.getPosition().y-position.y)/(body.getPosition().x-position.x));

        //dispose path
        gameWorld.getWorld().destroyBody(bodyPath);

        //recreate path
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(midPoint);
        bodyPath = gameWorld.getWorld().createBody(bodyDef);
        PolygonShape pathBox = new PolygonShape();
        pathBox.setAsBox((float)distancePTP/2, 1);
        bodyPath.setTransform(midPoint,(float)angle);
        bodyPath.createFixture(pathBox, 0);

        return true;
    }

    /**
     * Stops Slasher's movement
     */
    public void finishedMoving()
    {
        //dispose both
        gameWorld.getWorld().destroyBody(body);
        gameWorld.getWorld().destroyBody(bodyPath);

        //point to null
        body=null;
        bodyPath=null;
    }

    public void dispose()
    {
        body.getWorld().destroyBody(body);
    }
}
