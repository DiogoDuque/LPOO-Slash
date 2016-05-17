package com.lpoo.slash;

/**
 * Created by Diogo on 26-04-2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameworld.GameRenderer;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.InputHandler;

public class GameScreen implements Screen {

    static private Vector2 screenDimensions;
    private GameWorld gameWorld;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen() {
        System.out.println("GameScreen::GameScreen() - screenWidth = " + Gdx.graphics.getWidth() +
                            ", screenHeight = " + Gdx.graphics.getHeight());

        gameWorld = new GameWorld();

        screenDimensions = new Vector2(352,200);
        renderer = new GameRenderer(gameWorld, screenDimensions);

        Gdx.input.setInputProcessor(new InputHandler(gameWorld));
    }

    public static Vector2 convertDimensions(Vector2 dims)
    {
        dims.x = dims.x*screenDimensions.x/Gdx.app.getGraphics().getWidth();
        dims.y = dims.y*screenDimensions.y/Gdx.app.getGraphics().getHeight();
        return dims;
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        gameWorld.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        System.out.println("GameScreen - show called");
    }

    @Override
    public void hide() {
        System.out.println("GameScreen - hide called");
    }

    @Override
    public void pause() {
        System.out.println("GameScreen - pause called");
    }

    @Override
    public void resume() {
        System.out.println("GameScreen - resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }

}