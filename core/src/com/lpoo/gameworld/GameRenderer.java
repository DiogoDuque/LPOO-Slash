package com.lpoo.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.lpoo.gameobjects.Ball;
import com.lpoo.gameobjects.GameArea;

/**
 * Created by Diogo on 26-04-2016.
 */
public class GameRenderer {

    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;

    private GameWorld myWorld;
    private Ball ball;
    private GameArea gameArea;

    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    public GameRenderer(GameWorld world) {
        myWorld = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ball = myWorld.getBall();
        gameArea=myWorld.getGameArea();

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined); // Attach batcher to camera
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
        Vector2[] points = gameArea.getPoints();
        for(int i=0; i<points.length; i++) {
            Vector2 a = points[i];
            Vector2 b = points[i==points.length-1 ? 0 : i+1];
            shapeRenderer.line(a.x,a.y,b.x,b.y);
        }

        //Draw ball
        shapeRenderer.circle(ball.getBody().getPosition().x, ball.getBody().getPosition().y, ball.getRadius());

        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        /*batcher.setProjectionMatrix(cam.combined);
        debugMatrix = batcher.getProjectionMatrix().cpy().scale(resizeX(1),resizeY(1),1);
        batcher.begin();

        // The ball needs transparency, so we enable that again.
        batcher.enableBlending();

        // Pass in the runTime variable to get the current frame.
        batcher.draw(AssetLoader.ballTest,
                ball.getX()*dims.x/100, ball.getY()*dims.y/100, ball.getRadius()*dims.x/100, ball.getRadius()*dims.y/100);

        // End SpriteBatch
        batcher.end();

        debugRenderer.render(myWorld.getWorld(), debugMatrix);
*/    }

    public static Vector2 resize(Vector2 vec)
    {
        return new Vector2(resizeX(vec.x),resizeY(vec.y));
    }

    public static float resizeX (float x) {
        return x*Gdx.graphics.getWidth()/176;
    }

    public static float resizeY (float y) {
        return y*Gdx.graphics.getHeight()/100;
    }
}
