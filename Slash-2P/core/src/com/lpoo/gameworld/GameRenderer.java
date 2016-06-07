package com.lpoo.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameobjects.Ball;
import com.lpoo.gameobjects.Redirecter;
import com.lpoo.slash.GameScreen;


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


    public GameRenderer(GameWorld world, GameScreen screen) {
        gameWorld = world;
        cam = new OrthographicCamera();

        cam.setToOrtho(true, screen.width, screen.height);
        font = new BitmapFont();
        font.getData().setScale(0.7f,-0.7f);
        batcher = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

    }

    public void render(float runTime) {
        batcher.setProjectionMatrix(cam.combined);
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

        //Draw Redirecter
        Redirecter redirecter = gameWorld.getRedirecter();
        if(redirecter!=null)
        {
            shapeRenderer.setColor(100/255f,0,1,1);
            shapeRenderer.circle(redirecter.getPosition().x,redirecter.getPosition().y,redirecter.radius);
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

        batcher.begin();
        font.setColor(Color.YELLOW);

        String highScoreText = "HIGHSCORE: "+gameWorld.getHighscore();
        String scoreText = "SCORE: "+gameWorld.getScore();
        String timer = "TIME: "+ gameWorld.getTimer();

        font.draw(batcher,highScoreText,260,50);
        font.draw(batcher, scoreText, 260, 100);
        font.draw(batcher,timer,260, 150);


        batcher.end();
    }
}
