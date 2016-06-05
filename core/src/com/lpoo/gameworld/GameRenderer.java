package com.lpoo.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lpoo.gameobjects.Ball;
import com.lpoo.slash.Slash;

import static com.lpoo.gameworld.GameWorld.getScore;
import static com.lpoo.gameworld.GameWorld.getTimer;

/**
 * Created by Diogo on 26-04-2016.
 * Renders everything in gameWorld to the screen.
 * Design Pattern: MVC - View component (Other components: InputHandler and GameWorld)
 */
public class GameRenderer {

    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;

    private GameWorld gameWorld;
    private BitmapFont font;
    private BitmapFont font2;


    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    private String scoreText;
    private String timer;

    public GameRenderer(GameWorld world) {
        gameWorld = world;
        cam = new OrthographicCamera();

        cam.setToOrtho(true, Slash.screenDimensions.x, Slash.screenDimensions.y);
        //gameArea = gameWorld.getGameArea();
        //slasher = gameWorld.getSlasher();
        //balls = gameWorld.getBalls();
        font = new BitmapFont();
        font.getData().setScale(0.7f,0.7f);
        font2 = new BitmapFont();
        font2.getData().setScale(0.7f,0.7f);
        batcher = new SpriteBatch();
       // batcher.setProjectionMatrix(cam.combined); // Attach batcher to camera
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        debugRenderer = new Box2DDebugRenderer();
    }

    public void render(float runTime) {
        cam.update();

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Draw GameArea
        shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, 1);
        Vector2[] points = gameWorld.getGameArea().getPoints();
        for(int i=0; i<points.length; i++) {
            Vector2 a = points[i];
            Vector2 b = points[i==points.length-1 ? 0 : i+1];
            shapeRenderer.line(a.x,a.y,b.x,b.y);
        }

        //Draw Slasher
        shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 0 / 255.0f, 1);
        Vector2 fingerPos = gameWorld.getSlasher().getFinger();
        Vector2 slasherPos = gameWorld.getSlasher().getPosition();
        shapeRenderer.circle(slasherPos.x,slasherPos.y,Ball.getRadius());
        if(gameWorld.getSlasherIsMoving())
        {
            Vector2 tempSlasher = gameWorld.getSlasher().getBodyPosition();
            shapeRenderer.circle(tempSlasher.x,tempSlasher.y,Ball.getRadius());
            shapeRenderer.line(slasherPos,tempSlasher);
        }
        else if(fingerPos!=null)
        {
            //draw future Slasher
            shapeRenderer.circle(fingerPos.x,fingerPos.y,Ball.getRadius());
            //draw line
            shapeRenderer.line(slasherPos,fingerPos);
        }

        //Draw Balls
        shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, 1); //same color as gameArea
        for(int i = 0; i< gameWorld.getBalls().size(); i++)
            shapeRenderer.circle(gameWorld.getBalls().get(i).getBody().getPosition().x,
                                gameWorld.getBalls().get(i).getBody().getPosition().y,
                                Ball.getRadius());

        // End ShapeRenderer
        shapeRenderer.end();

        /*// Begin SpriteBatch
        batcher.setProjectionMatrix(cam.combined);*/
        //cam.update();

        cam.setToOrtho(false, Slash.screenDimensions.x, Slash.screenDimensions.y);
        batcher.setProjectionMatrix(cam.combined); //or your matrix to draw GAME WORLD, not UI
        batcher.begin();
        font2.setColor(Color.YELLOW);
        font.setColor(Color.YELLOW);

        scoreText = "SCORE :"+getScore();
        timer = "TIME :"+ getTimer();

        font.draw(batcher, scoreText, 300, 150);
        font2.draw(batcher,timer,300, 100);


        batcher.end();
    }
}
