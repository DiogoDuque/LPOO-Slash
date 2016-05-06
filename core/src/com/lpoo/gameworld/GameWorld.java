package com.lpoo.gameworld;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameobjects.Ball;
import com.lpoo.gameobjects.GameArea;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Diogo on 26-04-2016.
 */
public class GameWorld {
    private World world;
    private GameArea gameArea;
    private ArrayList<Ball> balls;

    public GameWorld() {
        //todos os objetos criados devem ter coordenadas entre (0,0) e (176,100), por razoes de scaling para o ecra
        world = new World(new Vector2(0, 0), false); //mundo
        Vector2 pt1=new Vector2(20,20),pt2=new Vector2(30,90),pt3=new Vector2(80,80),pt4=new Vector2(70,10);
        gameArea = new GameArea(pt1,pt2,pt3,pt4,world);
        balls = new ArrayList<Ball>();
        createBalls(1);
    }

    /**
     * Creates n new balls in random positions inside the gameArea.
     */
    private void createBalls(int n)
    {
        Random rand = new Random();
        for(int i=0; i<n; i++)
        {
            //(x,y) entre [5,5] e [95,95]
            float x=rand.nextInt(91)+5;
            float y=rand.nextInt(91)+5;
            /*Vector2[] pts = gameArea.getPoints();
            //verificar se ultrapassa alguma linha
            if(y<=50) //baixo
            {
                float m = (pts[1].y-pts[2].y)/(pts[1].x-pts[2].x);
                float b = pts[1].y - m*pts[1].x;
                float yLimit = m*x+b;
                if(y+Ball.getRadius()<=yLimit) //verificacao se passa o limite superior
                {
                    i--;
                    continue;
                }
            }
            else //cima
            {
                float m = (pts[0].y-pts[3].y)/(pts[0].x-pts[3].x);
                float b = pts[0].y - m*pts[0].x;
                float yLimit = m*x+b;
                if(y-Ball.getRadius()>=yLimit)
                {
                    i--;
                    continue;
                }
            }
            if(x<=50) //esquerda
            {
                float m = (pts[0].y-pts[1].y)/(pts[0].x-pts[1].x);
                float b = pts[0].y - m*pts[0].x;
                float xLimit = (y-b)/m;
                if(x-Ball.getRadius()<=xLimit)
                {
                    i--;
                    continue;
                }
            }
            else //direita
            {
                float m = (pts[2].y-pts[3].y)/(pts[2].x-pts[3].x);
                float b = pts[2].y - m*pts[2].x;
                float xLimit = (y-b)/m;
                if(x+Ball.getRadius()>=xLimit)
                {
                    i--;
                    continue;
                }
            }//*/
            //se passou em todas as verificacoes, pode ser criado o objeto
            balls.add(new Ball(x, y, world));
            System.out.println("Ball: x="+x+", y="+y);
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
