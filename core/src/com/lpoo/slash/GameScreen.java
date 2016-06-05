package com.lpoo.slash;

/**
 * Created by Diogo on 26-04-2016.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lpoo.gameworld.GameRenderer;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.InputHandler;

public class GameScreen extends Resizer implements Screen {

    private Game game; //to change Screens
    private GameWorld gameWorld;
    private GameRenderer renderer;
    private float runTime;


    public GameScreen(Game game) {
        super(1);
        System.out.println("GameScreen::GameScreen() - screenWidth = " + Gdx.graphics.getWidth() +
                ", screenHeight = " + Gdx.graphics.getHeight());
        this.game=game;
        gameWorld = new GameWorld(game);

        renderer = new GameRenderer(gameWorld,this);

        Gdx.input.setInputProcessor(new InputHandler(gameWorld, this));
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        gameWorld.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("GameScreen - resize called");
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