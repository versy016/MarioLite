package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class menuclass implements Screen {
    MyGdxGame game;
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;
    private Skin skin;
    Button playbutton;
    Image img;

    // constructor to keep a reference to the main Game class
    public menuclass(MyGdxGame game) {
        this.game = game;
    }
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        background = new Texture("Starting Assets/assets/background.jpg");
        skin = new Skin(Gdx.files.internal("Starting Assets/assets/uiskin.json"));
        final TextButton playbutton = new TextButton("Play", skin, "default");
        final TextButton exitbutton = new TextButton("Exit", skin, "default");

        Image img = new Image(background);
        img.setSize(2050,1100);
        playbutton.setWidth(600f);
        playbutton.setHeight(100f);
        playbutton.getLabel().setFontScale(5);
        playbutton.setColor(Color.GOLD);
        playbutton.setPosition(750, 600);

        exitbutton.setWidth(600f);
        exitbutton.setHeight(100f);
        exitbutton.getLabel().setFontScale(5);
        exitbutton.setColor(Color.GOLD);
        exitbutton.setPosition(750, 400);


        playbutton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                game.setScreen(MyGdxGame.gclass);
                dispose();
            }
        });

        exitbutton.addListener(new ClickListener()
        {
            @Override
            public void clicked (InputEvent event, float x, float y)
            {
                Gdx.app.exit();
            }
        });
        stage.addActor(img);
        stage.addActor(playbutton);
        stage.addActor(exitbutton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen: ","menuScreen show called");
        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        stage.draw();
        batch.end();

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
        background.dispose();
    }
}
