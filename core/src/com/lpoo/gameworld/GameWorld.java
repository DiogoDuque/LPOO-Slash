package com.lpoo.gameworld;

import com.badlogic.gdx.Gdx;
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
        //todos os objetos criados devem ter coordenadas entre (0,0) e (250,200), por razoes de scaling para o ecra (352,200)
        world = new World(new Vector2(0, 0), false); //mundo
        Vector2 pt1=new Vector2(50,150),pt2=new Vector2(30,175),pt3=new Vector2(200,140),pt4=new Vector2(220,25);
        gameArea = new GameArea(pt1,pt2,pt3,pt4,world);
        slasher = new Slasher(pt1,this);
        balls = new ArrayList<Ball>();
        createBalls(1);
    }

    /**
     * Constructor used for debugging and JUnit4.
     * @param world World object for physics.
     * @param gameArea area where the balls will wander.
     * @param slasher slasher for this gameArea.
     */
    public GameWorld(World world, GameArea gameArea, Slasher slasher)
    {
        this.world=world;
        this.gameArea=gameArea;
        this.slasher=slasher;
        balls = new ArrayList<Ball>();
        createBalls(1);
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

        //update balls
        for(int i=0; i<balls.size(); i++)
            balls.get(i).getBody().applyTorque(0,true);
    }

    public World getWorld() {
        return world;
    }

    public GameArea getGameArea() {return gameArea;}

    public Slasher getSlasher() {return slasher;}

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    /**
     * TODO
     * Called when the Slasher can (and will) cut the GameArea.
     * Cuts the GameArea by recreating it and creating one more ball.
     */
    public void updateGameArea()
    {
        Vector2[] points = new Vector2[4];
        Vector2 newPoint = new Vector2(slasher.getFinger());
        Vector2[] oldPoints = gameArea.getPoints();
        for(int i=0; i<4; i++)
        {
            if(gameArea.getToDelete()==oldPoints[i])
                points[i]=newPoint;
            else points[i]=oldPoints[i];
        }
        Vector2 toDelete = gameArea.getToDelete();
        gameArea.dispose();
        gameArea=new GameArea(points[0],points[1],points[2],points[3],world);
        slasher=new Slasher(newPoint,this);
        int counter = checkBalls();
        createBalls(counter+1);
    }

    /**
     * Checks if the balls are still inside the gameArea.
     * Each ball outside the gameArea will be deleted, and the counter will be incremented.
     *
     * @return number of balls that are out of the gameArea.
     */
    public int checkBalls(){
        int counter=0;
        for (int i = 0; i < balls.size();i++) {
            if (!pointInPolygon(4, gameArea.getPoints(), balls.get(i))){
                Gdx.app.log("bola esta fora", "bola esta fora");
                Ball ball = balls.get(i);
                balls.remove(i);
                ball.dispose();
                counter++;
                System.out.println("GameWorld::createBalls() ");
            }

        }
        return counter;

    }

    public boolean pointInPolygon(int polyCorners, Vector2[] points, Ball ball) {
        int i, j = polyCorners - 1;
        boolean oddNodes = false;

        for (i = 0; i < polyCorners; i++) {
            if ((points[i].y < ball.getY() && points[j].y >= ball.getY()
                    || points[j].y < ball.getY() && points[i].y >= ball.getY())
                    && (points[i].x <= ball.getX() || points[j].x <= ball.getX())) {
                if (points[i].x + (ball.getY() - points[i].y) / (points[j].y - points[i].y) * (points[j].x - points[i].x) < ball.getX()) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;
        }
        return oddNodes;
    }
}
