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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GameScreen extends Resizer implements Screen {

    private Game game; //to change Screens
    private GameWorld gameWorld;
    private GameRenderer renderer;
    private float runTime;

    private  float timer;
    private Socket socket;
    private static boolean multiplayer = true;
    private static int score =0 ;
    private static int friendScore= 0;
    private final float UPDATE_TIME = 1/60f;
    private HashMap<String, Integer> friendlyPlayers;
    private static boolean frindIsOnline = false;

    public GameScreen(Game game) {
        super(1);
        System.out.println("GameScreen::GameScreen() - screenWidth = " + width +
                ", screenHeight = " + height);
        this.game=game;
        gameWorld = new GameWorld(game);

        renderer = new GameRenderer(gameWorld,this);
        if(multiplayer){
            connectSocket();
            configSocketEvents();}
        friendlyPlayers = new HashMap<java.lang.String, Integer>();
        Gdx.input.setInputProcessor(new InputHandler(gameWorld, this));
    }

    public void updateServer(float dt) {
        timer += dt;
        if (timer >= UPDATE_TIME) {
            JSONObject data = new JSONObject();
            try {
                score = gameWorld.getScore();
                data.put("score", gameWorld.getScore());
                socket.emit("scoreUpdated", data);
            } catch (JSONException e) {
                Gdx.app.log("SockETIO", "ERROR UPDATING");
            }
        }
    }


    public static boolean victory(){
        boolean vic;

        if(score > friendScore)
            return true;
        else return false;
    }


    @Override
    public void render(float delta) {
        runTime += delta;
        gameWorld.update(delta);
        renderer.render(runTime);
        updateServer(delta);
    }
    private void configSocketEvents() {
        Gdx.app.log("SocketIO", "Connected");
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.log("SocketIO", "Connected");
            }
        }).on("socketID", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];

                try {
                    java.lang.String id = data.getString("id");
                    Gdx.app.log("SocketIO", "My ID: " + id);
                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "ERROR GETTING ID");
                }
            }
        }).on("newPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    java.lang.String id = data.getString("id");

                    Gdx.app.log("SocketIO", "New Player connect ID: " + id);
                    friendlyPlayers.put(id, 0);
                    //Gdx.app.log("SocketIO", "New Player connect score: " + score);



                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "ERROR GETTING newPlayer ID");
                }
            }
        }).on("playerDisconneted", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    java.lang.String id = data.getString("id");
                    socket.close();
                    friendlyPlayers.remove(id);

                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "ERROR getting disconneted player id");
                }
            }
        }).on("getPlayers", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONArray objects = (JSONArray) args[0];
                try {
                    for(int i = 0; i < objects.length(); i++){

                        friendlyPlayers.put(objects.getJSONObject(i).getString("id"), 0);
                        Gdx.app.log("SocketIO", "ERROR getting disconneted player id"+friendlyPlayers.get(objects.getJSONObject(i).getString("id")));
                    }
                } catch(JSONException e){
                    Gdx.app.log("SocktIO", "ERROR GETTING PLAYERS");
                }
            }
        }).on("scoreUpdated", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {
                    java.lang.String playerid = data.getString("id");
                    if(friendlyPlayers.get(playerid) != null){
                        friendScore=data.getInt("score");}
                    //Gdx.app.log("SocketIO", "friendScore"+friendScore);


                } catch (JSONException e) {
                    Gdx.app.log("SocketIO", "ERROR getting disconneted player id");
                }
            }
        });
    }


    private void connectSocket() {
        try{
            socket = IO.socket("http://172.30.20.211:15069");
            socket.connect();
            //System.out.println("PINTOOOOu");
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
        }
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