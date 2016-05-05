package com.lpoo.gameworld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameobjects.Ball;

/**
 * Created by Diogo on 26-04-2016.
 */
public class GameWorld {
    private Ball ball;
    private World world;
    private BodyDef[] sides;


    public GameWorld(int midPointY) {
        world=new World(new Vector2(0,0),false); //mundo
        ball=new Ball(4,8,8, world); //bola de teste
        sides=new BodyDef[4]; //4 lados da caixa
        BodyDef temp = new BodyDef();
        temp.position.set(0,-10);
        Body groundBody = world.createBody(temp);
        PolygonShape groundBox = new PolygonShape();
        groundBox.setAsBox(50,10);
        groundBody.createFixture(groundBox,0);
        sides[0]=temp;

    }

    public void update(float delta) {
        ball.update(delta);


    }

    public Ball getBall() {
        return ball;
    }

    public World getWorld() {
        return world;
    }
}
