package com.lpoo.slash;

/**
 * Created by Diogo on 26-04-2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.lpoo.gameworld.GameRenderer;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.InputHandler;

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        System.out.println("GameScreen - screenWidth = " + screenWidth + ", screenHeight = " + screenHeight);
        System.out.println("GameScreen - gameWidth = " + gameWidth + ", gameHeight = " + gameHeight);

        int midPointY = (int) (gameHeight / 2);

        world = new GameWorld(midPointY);
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);

        Gdx.input.setInputProcessor(new InputHandler(world.getBall()));
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
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