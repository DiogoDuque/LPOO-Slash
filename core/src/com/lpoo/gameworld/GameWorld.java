package com.lpoo.gameworld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameobjects.Ball;
import com.lpoo.gameobjects.GameArea;

/**
 * Created by Diogo on 26-04-2016.
 */
public class GameWorld {
    private Ball ball;
    private GameArea gameArea;
    private World world;


    public GameWorld() {
        //todos os objetos criados devem ter coordenadas entre (0,0) e (176,100), por razoes de scaling para o ecra
        world = new World(new Vector2(0, 0), false); //mundo
        ball = new Ball(70, 70, 3, world); //bola de teste
        gameArea = new GameArea(new Vector2(20,20),new Vector2(40,80),new Vector2(90,90),new Vector2(70,40),world);


    }

    public void update(float delta) {
        world.step(1f/60f, 6, 2);
        ball.getBody().applyTorque(0,true);
    }

    public Ball getBall() {
        return ball;
    }

    public World getWorld() {
        return world;
    }

    public GameArea getGameArea() {return gameArea;}
}
