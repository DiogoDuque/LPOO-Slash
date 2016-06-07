package com.lpoo.slash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.operations.String;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Slash extends Game {
    public static final Vector2 screenDimensions = new Vector2(352,200);
    private static File filesDir;

    public Slash(File fileDir)
    {
        filesDir=fileDir;
    }
    public static File getFilesDir() {
        return filesDir;
    }

    public Slash()
    {}

    @Override
    public void create() {
        System.out.println("Slash Game Created!");

        setScreen(new MenuScreen(this));


    }


    @Override
    public void dispose() {
        super.dispose();

    }
}