package com.lpoo.slashhelpers;

import com.badlogic.gdx.InputProcessor;
import com.lpoo.gameobjects.Ball;

import java.util.ArrayList;

/**
 * Created by Diogo on 27-04-2016.
 */
public class InputHandler implements InputProcessor {
    private ArrayList<Ball> balls;

    public InputHandler(ArrayList<Ball> balls)
    {
        this.balls=balls;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

}
