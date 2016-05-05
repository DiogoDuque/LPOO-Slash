package com.lpoo.gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.lpoo.gameobjects.Ball;
import com.lpoo.slashhelpers.AssetLoader;

/**
 * Created by Diogo on 26-04-2016.
 */
public class GameRenderer {

    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private int midPointY;
    private int gameHeight;
    private Ball ball;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, 204);
        ball = myWorld.getBall();

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined); // Attach batcher to camera
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
    }

    public void render(float runTime) {

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);

        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        batcher.draw(AssetLoader.grass, 0, midPointY + 23, 136, 43);

        // The bird needs transparency, so we enable that again.
        batcher.enableBlending();

        // Pass in the runTime variable to get the current frame.
        batcher.draw(AssetLoader.ball,
                ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius()*204/136);

        // End SpriteBatch
        batcher.end();
    }
}
