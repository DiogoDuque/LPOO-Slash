package com.lpoo.slash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lpoo.slashhelpers.Utilities;

import static com.lpoo.gameworld.GameWorld.getScore;

/**
 * Created by Diogo Duque on 03/06/2016.
 */
public class GameOverScreen implements Screen {

    private Game game;
    private float width, height;
    private OrthographicCamera cam;
    private BitmapFont font;
    private SpriteBatch batch;
    private Sprite background;

    public GameOverScreen(Game game)
    {
        this.game=game;
        cam = new OrthographicCamera();
        width=Slash.screenDimensions.x*1.2f;
        height=Slash.screenDimensions.y*1.2f;
        cam.setToOrtho(false,width,height);
        font = new BitmapFont();
        font.getData().setScale(0.7f,0.7f);
        batch=new SpriteBatch();
        background=new Sprite(new Texture("gameover/background.png"));
        implementTouchDetector();
    }

    private Vector2 convertDimensions(Vector2 dims)
    {
        dims.x = dims.x*width/Gdx.app.getGraphics().getWidth();
        dims.y = dims.y*height/Gdx.app.getGraphics().getHeight();
        return dims;
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
                Gdx.app.log("GameOverScreen::inputs","x="+finger.x+", y="+finger.y);
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
        String scoreText = "SCORE :"+getScore();
        font.draw(batch, scoreText, 40, 125);

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
