package com.lpoo.gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lpoo.gameobjects.Ball;
import com.lpoo.gameobjects.GameArea;
import com.lpoo.gameobjects.Redirecter;
import com.lpoo.gameobjects.Slasher;
import com.lpoo.slash.GameOverScreen;
import com.lpoo.slash.Slash;
import com.lpoo.slashhelpers.Function;
import com.lpoo.slashhelpers.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static com.lpoo.slashhelpers.Utilities.changeScreen;

/**
 * Created by Diogo on 26-04-2016.
 * Keeps all the information about the objects currently on the World.
 * Design Pattern: MVC - Model component (Other components: GameRenderer and InputHandler)
 */
public class GameWorld {
    private Game game;
    private World world;
    private GameArea gameArea;
    private Slasher slasher;
    private ArrayList<Ball> balls;
    private Redirecter redirecter=null;
    private boolean slasherIsMoving=false;
    private Music music;

    public int highscore=0;
    public int score;

    /**
     * Time in milisseconds at which the current gameArea was created.
     */
    private long gameAreaCreationTime;
    /**
     * The limit of seconds (counting from the gameArea creation) until which a slasher can be started. After that, game is over.
     */
    private int timerLimit;
    private int timer;
    private static final String filename="highscore.txt";

    /**
     * Default constructor.
     */
    public GameWorld(Game game) {
        this.game=game;
        //todos os objetos criados devem ter coordenadas entre (0,0) e (250,200), por razoes de scaling para o ecra (352,200)
        world = new World(new Vector2(0, 0), false); //mundo
        Vector2 pt1=new Vector2(50,50),pt2=new Vector2(30,175),pt3=new Vector2(200,140),pt4=new Vector2(220,25);
        gameArea = new GameArea(pt1,pt2,pt3,pt4,this);
        slasher = new Slasher(pt1,this);
        balls = new ArrayList<Ball>();
        createBalls(1);
        score = 0;
        highscore=readScoreFile();
        timerLimit=10;
        gameAreaCreationTime=new Date().getTime();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }

    /**
     * Constructor used for JUnit4.
     * @param pt1 corner in gameArea
     * @param pt2 corner in gameArea
     * @param pt3 corner in gameArea
     * @param pt4 corner in gameArea
     * @param slasherPos slasher position
     */
    public GameWorld(Vector2 pt1, Vector2 pt2, Vector2 pt3, Vector2 pt4, Vector2 slasherPos)
    {
        world=new World(new Vector2(0, 0), false);
        gameArea=new GameArea(pt1,pt2,pt3,pt4,this);
        slasher=new Slasher(slasherPos,this);
        balls = new ArrayList<Ball>();
        createBalls(1);
        score = 0;
        highscore=0;
        timerLimit=10;
        gameAreaCreationTime =new Date().getTime();
    }

    /**
     * Creates n new balls in random positions inside the gameArea.
     */
    private void createBalls(int n)
    {
        Random rand = new Random();
        Vector2[] pts = gameArea.getPoints();
        Vector2 pt1=pts[0],pt2=pts[1],pt3=pts[2],pt4=pts[3];

        Function y2 = new Function(pts[0], pts[2]);
        Function y1 = new Function(pts[1], pts[3]);
        Vector2 center = y2.intersect(y1);


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


            float x=center.x;
            float y=(yMax-yMin)*rand.nextFloat()/2+yMin;

            balls.add(new Ball(x, y, world));
            System.out.println("GameWorld::createBalls() - Ball created at ("+x+","+y+")");
        }
    }

    /**
     * Update function
     * @param delta
     */
    public void update(float delta) {
        world.step(1f/60f, 6, 2);

        //update balls
        for(int i=0; i<balls.size(); i++) {
            balls.get(i).getBody().applyTorque(0, true);
            if(redirecter!=null)
                redirecter.attemptMoveBall(balls.get(i));
        }

        //update slasher
        if(slasherIsMoving)
        {
            String message=slasher.isMoving();
            if(message=="Slasher End Reached")
            {
                slasherIsMoving=false;
                updateGameArea();
                gameAreaCreationTime=new Date().getTime(); //reset timer
                if(score>=redirecter.scoreLimit && redirecter==null)
                    redirecter=new Redirecter(gameArea.getPoints());

            } else if(message=="Game Over") {
                slasherIsMoving=false;
                if (score > highscore)
                    highscore = score;
                updateScoreFile();
                music.pause();
                changeScreen(game, new GameOverScreen(game, score));
            }
        }
        else updateTimer();
        if(timer<=0)
        {
            if (score > highscore)
                highscore = score;
            updateScoreFile();
            changeScreen(game, new GameOverScreen(game, score));
            music.pause();
        }

    }

    public World getWorld() {
        return world;
    }

    public GameArea getGameArea() {return gameArea;}

    public Slasher getSlasher() {return slasher;}

    public void setSlasher(Slasher slasher) {
        this.slasher = slasher;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public Redirecter getRedirecter()
    {
        return redirecter;
    }

    public int getScore() {
        return score;
    }

    public int getHighscore() {
        return highscore;
    }

    public Music getMusic() {
        return music;
    }

    /**
     *
     * @return timer in seconds.
     */
    public long getTimer() {
        return timer;
    }

    /**
     *
     * @return boolean slasherIsMoving.
     */
    public boolean getSlasherIsMoving() {return slasherIsMoving;}

    /**
     * Called when the Slasher can (and will) cut the GameArea.
     * Cuts the GameArea by recreating it and creating one more ball.
     */
    private void updateGameArea()
    {
        Vector2[] points = new Vector2[4];
        Vector2[] oldPoints = gameArea.getPoints();
        Vector2 newPoint = new Vector2(slasher.getFinger());
        Vector2[] pointsTriangle = new Vector2[3];
        pointsTriangle[0] = slasher.getPosition();

        for( int j=0; j<4; j++)
        {
            if(gameArea.getToDelete()==oldPoints[j]){
                points[j]=newPoint;
                pointsTriangle[2] = newPoint;
                pointsTriangle[1]=gameArea.getToDelete();
                score++;
            }
            else points[j]=oldPoints[j];
        }

        //new objects
        gameArea.dispose();
        slasher = new Slasher(newPoint, this);
        int counter = checkBalls(pointsTriangle);

        gameArea=new GameArea(points[0],points[1],points[2],points[3],this);
        createBalls(counter+1);

        if(redirecter!=null) //generates the redirecter if it is already in use
            redirecter=new Redirecter(gameArea.getPoints());
        score += counter;


    }

    /**
     * Checks if the balls are still inside the gameArea.
     * Each ball outside the gameArea will be deleted, and the counter will be incremented.
     *
     * @return number of balls that are out of the gameArea.
     */
    private int checkBalls(Vector2[] pointsTriangle){
        int counter=0;

        for (int i = balls.size()-1; i >= 0;i--) {
            if (Utilities.pointInPolygon(pointsTriangle, balls.get(i))){
                Gdx.app.log("bola esta fora", "bola esta fora");
                Ball ball = balls.get(i);
                balls.remove(i);
                ball.dispose();
                counter++;
                //createBalls(1);
                System.out.println("GameWorld::numero de bolas checadas"+balls.size());
                System.out.println("GameWorld::createBalls() ");}
        }


        return counter;

    }

    /**
     * Called when slash starts to move/cut the gameArea.
     * Also sets slasherIsMoving to true.
     */
    public void startSlashMovement()
    {
        slasherIsMoving=true;
        slasher.startedMoving();
    }

    /**
     * Attemps to read the file with the high score and return it.
     * @return number in the file; 0 if file does not exist.
     */
    public static int readScoreFile()
    {
        BufferedReader reader = null;
        int highscore=0;
        try {
            File file = new File(Slash.getFilesDir()+"/"+filename);
            reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            highscore=reader.read();
            reader.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return highscore;
    }

    private void updateScoreFile()
    {
        BufferedWriter output = null;
        try {
            File file = new File(Slash.getFilesDir()+"/"+filename);
            if(!file.exists())
                file.createNewFile();
            output = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            output.write(highscore);
            System.out.println("Score file updated with success in " + Slash.getFilesDir() + " - " + file.getAbsoluteFile());
            if (output != null)
                output.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private void updateTimer()
    {
        long currTime=new Date().getTime();
        int diff=(int)(currTime-gameAreaCreationTime)/1000;
        timer=timerLimit-diff;
    }
}

