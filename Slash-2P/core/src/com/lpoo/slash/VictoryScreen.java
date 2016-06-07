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


/**
 * Created by João Gomes on 07/06/2016.
 */
public class VictoryScreen extends Resizer implements Screen {

    private Game game;
    private OrthographicCamera cam;
    private BitmapFont font;
    private SpriteBatch batch;
    private Sprite background;
    private int score;
    private int highscore;

    public VictoryScreen(Game game, int currentScore)
    {
        super(1.2f);
        this.game=game;
        cam = new OrthographicCamera();
        cam.setToOrtho(false,width,height);
        font = new BitmapFont();
        font.getData().setScale(0.7f,0.7f);
        batch=new SpriteBatch();
        background=new Sprite(new Texture("victory.png"));
        score=currentScore;
        highscore=GameWorld.readScoreFile();
        implementTouchDetector();
    }


    /**
     * used to detect input 'TouchUp', and when detected it will change screen.
     */
    private void implementTouchDetector()
    {
        Gdx.input.setInputProcessor(new InputProcessor() {

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                Vector2 finger = convertDimensions(new Vector2(screenX,screenY));
                if(finger.y>180)
                {
                    if(finger.x<211)
                        Utilities.changeScreen(game, new MenuScreen(game));
                    else Utilities.changeScreen(game, new GameScreen(game));
                }
                Gdx.app.log("GameOverScreen::inputs", "x=" + finger.x + ", y=" + finger.y);
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                Gdx.app.log("GameOverScreen::inputs","down");
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                Gdx.app.log("GameOverScreen::inputs","dragged");
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

        batch.draw(background, 0, 0, width, height);
        font.setColor(Color.YELLOW);
       // String newHighscoreText = "NEW HIGHSCORE !! "+ highscore;
        String scoreText = "SCORE: "+score;
       // String highscoreText = "HIGHSCORE: "+highscore;


            font.draw(batch, scoreText, 150, 150);


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