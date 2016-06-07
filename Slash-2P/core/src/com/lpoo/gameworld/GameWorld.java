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
import com.lpoo.slash.MenuScreen;
import com.lpoo.slash.Slash;
import com.lpoo.slash.VictoryScreen;
import com.lpoo.slashhelpers.Function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;



import static com.lpoo.slash.GameScreen.victory;
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

    private static int highscore=0;
    public int score;
    private static int finalscore= 0;

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


   // private Socket socket;
    private static boolean multiplayer = true;

    private static int friendScore= 0;

    private HashMap<String, Integer> friendlyPlayers;
    private final float UPDATE_TIME = 1/60f;
    private  float timer1;



    /**
     * Default constructor.
     */
    public GameWorld(Game game) {
        this.game=game;
        //todos os objetos criados devem ter coordenadas entre (0,0) e (250,200), por razoes de scaling para o ecra (352,200)
        world = new World(new Vector2(0, 0), false); //mundo
        Vector2 pt1=new Vector2(50,50),pt2=new Vector2(30,175),pt3=new Vector2(200,140),pt4=new Vector2(220,25);
        gameArea = new GameArea(pt1,pt2,pt3,pt4,world,this);
        slasher = new Slasher(pt1,this);
        balls = new ArrayList<Ball>();
        createBalls(1);
        score = 0;
        //highscore=readScoreFile();
        timerLimit=10;
        gameAreaCreationTime=new Date().getTime();
        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }

    /**
     * Constructor used for JUnit4.
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
                //updateScoreFile();
                music.pause();
                finalscore=score;
                if(victory())
                    changeScreen(game, new VictoryScreen(game, score));
                else changeScreen(game, new GameOverScreen(game, score));


            }
        }
        else updateTimer();
        if(timer<=0)
        {
            if (score > highscore)
                highscore = score;
            //updateScoreFile();
            finalscore=score;
            if(victory())
                changeScreen(game, new VictoryScreen(game,score));
            else changeScreen(game, new GameOverScreen(game, score));
            music.pause();
        }

    }

    public static int getFinalscore() {
        return finalscore;
    }

    public static void setFinalscore(int finalscore) {
        GameWorld.finalscore = finalscore;
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

    public static int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
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
    public void updateGameArea()
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

        gameArea=new GameArea(points[0],points[1],points[2],points[3],world, this);
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
    public int checkBalls(Vector2[] pointsTriangle){
        int counter=0;

        for (int i = balls.size()-1; i >= 0;i--) {
            if (pointInPolygon(pointsTriangle, balls.get(i))){
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

    private boolean pointInPolygon( Vector2[] pointsTriangle, Ball ball) {

        Vector2 center = new Vector2();
        boolean b = true;
        center.x = (pointsTriangle[0].x + pointsTriangle[1].x + pointsTriangle[2].x)/3;
        center.y = (pointsTriangle[0].y + pointsTriangle[1].y + pointsTriangle[2].y)/3;
        Vector2 ballPoint = new Vector2(ball.getBody().getPosition().x,ball.getBody().getPosition().y);
        Function f = new Function(center, ballPoint);
        for(int i = 0;i<pointsTriangle.length;i++){
            Function edge = new Function(pointsTriangle[i], pointsTriangle[((i+1)%3)]);
            Vector2 p = f.intersect(edge);

            if(isBetween(center, p, ballPoint))
                b = false;
        }
        return b;
    }

    public static boolean isBetween ( Vector2 center, Vector2 p, Vector2 ballPoint){



        System.out.println("GameWorld::isBetween() center:"+ center + " p" + p + "ball" + ballPoint);
        if(p == null)
            return false;
        //  if ((p.x >= center.x && p.x <= ballPoint.x) || (p.x <= center.x && p.x >= ballPoint.x))
        //    if ((p.y >= center.y && p.y <= ballPoint.y) ||(p.y <= center.y && p.y >= ballPoint.y))
        //      return true;
        if ((p.x >= center.x )&& (p.x <= ballPoint.x)){
            if ((p.y >= center.y) && (p.y <= ballPoint.y))
                return true;
            else if ((p.y <= center.y) &&( p.y >= ballPoint.y))
                return true;
            else return false;
        }
        if ((p.x <= center.x) && (p.x >= ballPoint.x)){
            if ((p.y >= center.y) && (p.y <= ballPoint.y))
                return true;
            else if ((p.y <= center.y) &&( p.y >= ballPoint.y))
                return true;
            else return false;
        }
        return false;
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
     * Changes the points (if needed) so that each of them is in his quadrant and not too close to the sides of the board.
     * @param points points that will be used to create a gameArea.
     */
    public void checkBounds(Vector2[] points){

        for(int i = 0 ; i <points.length;i++){

            //checks 'x' border of screen
            if(points[i].x <10)
                points[i].x = 10;
            else if(points[i].x >240)
                points[i].x = 240;

            //checks 'y' border of screen
            if(points[i].y <10)
                points[i].y = 10;
            else if(points[i].y >190)
                points[i].y = 190;

            //checks quadrants
            switch(i)
            {
                case 0: //1st quadrant - up left
                    if(points[i].x>GameArea.center.x)
                        points[i].x=GameArea.center.x;
                    if(points[i].y>GameArea.center.y)
                        points[i].y=GameArea.center.y;
                    break;

                case 1: //2nd quadrant - down left
                    if(points[i].x>GameArea.center.x)
                        points[i].x=GameArea.center.x;
                    if(points[i].y<GameArea.center.y)
                        points[i].y=GameArea.center.y;
                    break;

                case 2: //3rd quadrant - down right
                    if(points[i].x<GameArea.center.x)
                        points[i].x=GameArea.center.x;
                    if(points[i].y<GameArea.center.y)
                        points[i].y=GameArea.center.y;
                    break;

                case 3: //4th quadrant - up right
                    if(points[i].x<GameArea.center.x)
                        points[i].x=GameArea.center.x;
                    if(points[i].y>GameArea.center.y)
                        points[i].y=GameArea.center.y;
                    break;
            }


        }
        for (int i = 0;i<points.length;i++){
            if(points[i%4].x == points[(i+1)%4].x){
                points[i].x++;
            }
            if(points[i%4].y == points[(i+1)%4].y){
                points[i].y++;
            }
        }
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
            File file = new File(Slash.getFilesDir()+"/"+"example.txt");
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
            File file = new File(Slash.getFilesDir()+"/"+"example.txt");
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

