package com.mygdx.game;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MyGdxGame extends Game implements ApplicationListener {
	SpriteBatch batch;

	Texture img;
	// The class with the menu
	public static menuclass mclass;
	public static Gameclass gclass;

	@Override
	public void create () {
		Gdx.app.log("MyGdxGame: "," create");
		mclass = new menuclass(this);
		gclass = new Gameclass(this);
		setScreen(mclass);
	}

	@Override
	public void render () {

	super.render();}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
