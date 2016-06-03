package com.lpoo.slash;

/**
 * Created by Diogo on 26-04-2016.
 */

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameworld.GameRenderer;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.InputHandler;

import static com.lpoo.slashhelpers.Utilities.changeScreen;

public class GameScreen implements Screen {

    private Game game; //to change Screens
    private GameWorld gameWorld;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen(Game game) {
        System.out.println("GameScreen::GameScreen() - screenWidth = " + Gdx.graphics.getWidth() +
                            ", screenHeight = " + Gdx.graphics.getHeight());
        this.game=game;
        //changeScreen(game, this);
        gameWorld = new GameWorld(game);

        renderer = new GameRenderer(gameWorld);

        Gdx.input.setInputProcessor(new InputHandler(gameWorld));
    }

    public static Vector2 convertDimensions(Vector2 dims)
    {
        dims.x = dims.x*Slash.screenDimensions.x/Gdx.app.getGraphics().getWidth();
        dims.y = dims.y*Slash.screenDimensions.y/Gdx.app.getGraphics().getHeight();
        return dims;
    }

    public void changeScreenToMenu()
    {
        changeScreen(game, new MenuScreen(game));
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