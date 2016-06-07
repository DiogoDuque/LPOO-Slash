package com.lpoo.slash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.gameworld.GameWorld;
import com.lpoo.slashhelpers.Utilities;

import java.util.ArrayList;

/**
 * Created by Diogo Duque on 03/06/2016.
 */
public class MenuScreen extends Resizer implements Screen {

    /**
     * Can be used to change screens.
     */
    private Game game;
    /**
     * The camera used in this Screen.
     */
    private OrthographicCamera cam;
    /**
     * Used for writing in the screen.
     */
    private BitmapFont font;
    /**
     * Batch to draw images and write information.
     */
    private SpriteBatch batch;
    /**
     * Sprite with this Screen's backgroud.
     */
    private Sprite background;

    /**
     * Constructor.
     * @param game
     */
    public MenuScreen(Game game)
    {
        super(1.2f);
        this.game=game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false,width,height); //width e height are from Resizer
        font = new BitmapFont();
        font.getData().setScale(0.7f,0.7f);
        batch=new SpriteBatch();
        background=new Sprite(new Texture("menu/background.png"));
        implementTouchDetector();
    }

    /**
     * used to detect input 'TouchUp', and when detected it will change screen.
     */
    private void implementTouchDetector()
    {
        Gdx.input.setInputProcessor(new InputProcessor() {

            ArrayList<Vector2> last2Touches = new ArrayList<Vector2>();

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                Vector2 finger = convertDimensions(new Vector2(screenX, screenY));
                if (finger.y > 123 && finger.y < 168) {
                    if (finger.x > 25 && finger.x < 398)
                        Utilities.changeScreen(game, new GameScreen(game));
                } else {
                    if(last2Touches.isEmpty()) //size=0
                        last2Touches.add(new Vector2(finger));
                    else if(last2Touches.get(last2Touches.size()-1).x < finger.x) //one more step towards easter egg
                    {
                        if(last2Touches.size()==2)
                            Utilities.changeScreen(game,new EasterEggScreen(game)); //changeScreen
                        else last2Touches.add(finger); //add touch
                    } else { //wrong move towards easter egg
                        last2Touches=new ArrayList<Vector2>();
                        last2Touches.add(finger);
                    }
                }

                Gdx.app.log("MenuScreen::inputs", "x=" + finger.x + ", y=" + finger.y);
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
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
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        cam.update();

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(cam.combined); //or your matrix to draw GAME WORLD, not UI
        batch.begin();
        font.setColor(Color.YELLOW);

        batch.draw(background, 0, 0, width, height);
        String highScore = " HIGHSCORE : "+ GameWorld.readScoreFile();
        font.draw(batch, highScore,175, 50);
        batch.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
