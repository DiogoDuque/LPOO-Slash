package com.lpoo.slash;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lpoo.slashhelpers.Utilities;

/**
 * Created by Diogo on 07-06-2016.
 */
public class EasterEggScreen extends Resizer implements Screen {

    private Game game;
    private OrthographicCamera camera;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private final int frames = 4;

    /**
     * Constructor.
     * @param game Game needed for changing screens.
     */
    public EasterEggScreen(Game game) {
        super(1);
        implementTouchDetector();
        this.game=game;
        camera=new OrthographicCamera();
        camera.setToOrtho(false, width, height);

        walkSheet = new Texture(Gdx.files.internal("easteregg/animationSheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,walkSheet.getWidth()/frames,walkSheet.getHeight());
        walkFrames = new TextureRegion[frames];
        int index = 0;
        for (int i = 0; i < frames; i++) {
            walkFrames[index++] = tmp[0][i];
        }
        walkAnimation = new Animation(0.1f, walkFrames);
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    private void implementTouchDetector()
    {
        Gdx.input.setInputProcessor(new InputProcessor() {

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                Utilities.changeScreen(game,new MenuScreen(game));
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
    public void show() {

    }

    @Override
    public void render(float delta) {
        spriteBatch.setProjectionMatrix(camera.combined);
        camera.update();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame,0,0,width,height);
        spriteBatch.end();
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
