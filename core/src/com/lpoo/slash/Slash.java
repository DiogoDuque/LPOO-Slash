package com.lpoo.slash;

import com.badlogic.gdx.Game;
import com.lpoo.slashhelpers.AssetLoader;

public class Slash extends Game {


    @Override
    public void create() {
        System.out.println("Slash Game Created!");
        AssetLoader.load();
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}