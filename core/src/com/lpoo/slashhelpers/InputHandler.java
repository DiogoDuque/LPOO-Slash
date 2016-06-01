package com.lpoo.slashhelpers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slash.GameScreen;

/**
 * Created by Diogo on 27-04-2016.
 */
public class InputHandler implements InputProcessor {
    private GameWorld gameWorld;

    public InputHandler(GameWorld gameWorld)
    {
        this.gameWorld=gameWorld;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(gameWorld.getSlasher().getFinger()!=null)
            gameWorld.startSlashMovement();
        gameWorld.getSlasher().setFinger(null); //send info to Slasher TODO EDIT
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        gameWorld.getSlasher().setFinger(GameScreen.convertDimensions(new Vector2(screenX,screenY))); //keep sending info to gameRenderer
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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

}
