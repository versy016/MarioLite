package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class menuclass implements Screen {
    MyGdxGame game;
    private Texture background;
    private SpriteBatch batch;
    private Stage stage;
    //background = new Texture("background.jpg");
   // final TextButton button = new TextButton("Click me",, "default");
    // constructor to keep a reference to the main Game class
    public menuclass(MyGdxGame game) {
        this.game = game;
        background = new Texture("Starting Assets/assets/background.jpg");
    }
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show() {
        Gdx.app.log("MenuScreen: ","menuScreen show called");
        create();
    }

    @Override
    public void render(float delta) {

        stage.getBatch().begin();
        stage.getBatch().draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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

    }
}
