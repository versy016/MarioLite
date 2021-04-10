package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Gameclass implements Screen {
    MyGdxGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;                   // Spritebatch for rendering
    Stage stage;
    private Texture walkSheet;                  // Texture to hold the spritesheet
    private TextureRegion[] walkFrames;         // Texture array for the frames
    private Animation walkAnimation; // Animation object for the sprite
    private TextureRegion currentFrame;         // The current frame to display
    private float stateTime;                    // The time that the program has been running

    public Gameclass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {

        // Load the spritesheet
        walkSheet = new Texture(Gdx.files.internal("Starting Assets/assets/running.png"));

        // Generate a two dimensional array of TextureRegion by splitting the spritesheet into individual regions
        TextureRegion[][] temp = TextureRegion.split(walkSheet, walkSheet.getWidth() / 4, walkSheet.getHeight() / 2);

        // Create a one dimensional array to hold the final TestureRegions
        walkFrames = new TextureRegion[4 * 2];

        // Loop through the 2D array and transfer the TextureRegion to the one dimensional array
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                walkFrames[index++] = temp[i][j];
            }
        }

        // Drop the TextureRegions into a new Animation object and set the framerate. As the
        // duration is set to 0.033, we will get 30 frames per second.
        walkAnimation = new Animation(0.1f, walkFrames);

        // Initialise the stateTime, aka how long the program has been running for.
        stateTime = 0.0f;

        batch = new SpriteBatch();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(w, h);

        batch.setProjectionMatrix(camera.combined);

        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Gdx.app.log("Gamescreen: ","menuScreen show called");
        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        // Increase the time the game has been running by adding deltaTime (the time since the
        // last update).
        stateTime += Gdx.graphics.getDeltaTime();

        // Grabe the current frame from the animation.
        currentFrame = (TextureRegion)walkAnimation.getKeyFrame(stateTime, true);
        stage.getBatch().begin();
        stage.getBatch().draw(currentFrame, 50,50);
        stage.getBatch().end();
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
        batch.dispose();
    }
}
