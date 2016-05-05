package com.lpoo.slashhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Diogo on 27-04-2016.
 */
public class AssetLoader {

    public static Texture texture;
    public static TextureRegion ball, grass;
    public static Sprite ballTest;

    public static void load() {
        ballTest=new Sprite(new Texture("orange.png"));
        texture = new Texture(Gdx.files.internal("texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        grass = new TextureRegion(texture, 0, 0, 400, 200); //x,y,width,height
        grass.flip(false, true);

        ball = new TextureRegion(texture, 0, 200, 100, 300);
        ball.flip(false, true);
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }

}