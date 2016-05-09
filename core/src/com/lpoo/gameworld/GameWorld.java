package com.lpoo.gameworld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameobjects.Ball;
import com.lpoo.gameobjects.GameArea;
import com.lpoo.gameobjects.Slasher;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Diogo on 26-04-2016.
 */
public class GameWorld {
    private World world;
    private GameArea gameArea;
    private Slasher slasher;
    private ArrayList<Ball> balls;

    public GameWorld() {
        //todos os objetos criados devem ter coordenadas entre (0,0) e (176,100), por razoes de scaling para o ecra
        world = new World(new Vector2(0, 0), false); //mundo
        Vector2 pt1=new Vector2(20,20),pt2=new Vector2(30,90),pt3=new Vector2(80,80),pt4=new Vector2(70,10);
        gameArea = new GameArea(pt1,pt2,pt3,pt4,world);
        slasher = new Slasher(pt3);
        balls = new ArrayList<Ball>();
        createBalls(3);
    }

    /**
     * Creates n new balls in random positions inside the gameArea.
     */
    private void createBalls(int n)
    {
        Random rand = new Random();
        Vector2[] pts = gameArea.getPoints();
        Vector2 pt1=pts[0],pt2=pts[1],pt3=pts[2],pt4=pts[3];
        for(int i=0; i<n; i++)
        {
            float xMin,xMax,yMin,yMax;

            if(pt1.x>pt2.x) //esq
                xMin=pt1.x;
            else xMin=pt2.x;

            if(pt3.x<pt4.x) //dir
                xMax=pt3.x;
            else xMax=pt4.x;

            if(pt1.y>pt4.y) //cima
                yMin=pt1.y;
            else yMin=pt4.y;

            if(pt2.y<pt3.y) //baixo
                yMax=pt2.y;
            else yMax=pt3.y;

            float x=(xMax-xMin)*rand.nextFloat()+xMin;
            float y=(yMax-yMin)*rand.nextFloat()+yMin;

            balls.add(new Ball(x, y, world));
            System.out.println("GameWorld::createBalls() - Ball created at ("+x+","+y+")");
        }
    }

    public void update(float delta) {
        world.step(1f/60f, 6, 2);
        for(int i=0; i<balls.size(); i++)
            balls.get(i).getBody().applyTorque(0,true);
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public World getWorld() {
        return world;
    }

    public GameArea getGameArea() {return gameArea;}
}
