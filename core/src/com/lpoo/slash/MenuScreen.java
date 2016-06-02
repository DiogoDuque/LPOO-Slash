package com.lpoo.slash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import static com.lpoo.slashhelpers.Utilities.changeScreen;

/**
 * Created by Diogo on 02-06-2016.
 */
public class MenuScreen implements Screen {

    private Game game;

    public MenuScreen(Game game)
    {
        this.game=game;
    }

    public void changeScreenToGame() {changeScreen(game, new GameScreen(game));}

    @Override
    public void render(float delta) {
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("MenuScreen - resize called");
    }

    @Override
    public void show() {
        System.out.println("MenuScreen - show called");
    }

    @Override
    public void hide() {
        System.out.println("MenuScreen - hide called");
    }

    @Override
    public void pause() {
        System.out.println("MenuScreen - pause called");
    }

    @Override
    public void resume() {
        System.out.println("MenuScreen - resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }
}
