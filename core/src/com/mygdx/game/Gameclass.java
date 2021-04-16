package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Gameclass implements Screen {
    MyGdxGame game;
    private Animation JumpAnimationend;
    private boolean letslide = false;

    public enum State{Sliding, Jumping, Running,Jumpend, Slidend;
    }
    public State currentstate;

    private OrthographicCamera camera;
    private SpriteBatch batch;                   // Spritebatch for rendering
    private Sprite playerSprite;
    private Sprite slimesprite;
    Stage stage;
    private Texture walkSheet;                  // Texture to hold the spritesheet
    private Texture jumpsheet;
    private Texture jumpendsheet;
    private Texture slidingsheet;
    private Texture slidingendsheet;
    private Texture slimesheet;

    Array<TextureRegion> JumpFrames1;

    private TextureRegion[] RunFrames;         // Texture array for the frames
    private TextureRegion[] Jumpframes;
    private TextureRegion[] Slideframes;
    private TextureRegion[] Slideframesend;
    private TextureRegion[] SlimeFrames;

    private TextureRegion[][] temp;
    private Animation RunningAnimation; // Animation for running
    private Animation JumpAnimation; // Animation for Jumping
    private Animation SlideAnimation; // Animation for sliding
    private Animation SlideAnimationend; // Animation for sliding
    private Animation SlimeAnimation;

    private TextureRegion currentframe;         // The current frame to display
    private TextureRegion slimeframe;

    private float stateTime;                    // The time that the program has been running

    int characterX ;
    int characterY ;
    public static final float MOVEMENT_SPEED = 400.0f;
    float dt;
    Vector2 playerDelta;
    public static final int gravity = -20;
    Vector3  velocity;
    Vector3 postion;
    boolean jumped = false;

    TiledMap tiledMap;
    OrthogonalTiledMapRenderer tiledMapRenderer;


    public Gameclass(MyGdxGame game) {
        this.game = game;
    }

    public void create() {

        // Load the spritesheet
        walkSheet = new Texture(Gdx.files.internal("Starting Assets/assets/running.png"));
        jumpsheet = new Texture(Gdx.files.internal("Starting Assets/assets/jumping start.png"));
        jumpendsheet = new Texture(Gdx.files.internal("Starting Assets/assets/jumping end.png"));
        slidingsheet = new Texture(Gdx.files.internal("Starting Assets/assets/sliding start.png"));
        slidingendsheet = new Texture(Gdx.files.internal("Starting Assets/assets/sliding end.png"));
        slimesheet = new Texture(Gdx.files.internal("Starting Assets/assets/slime.png"));


        // Generate a two dimensional array of TextureRegion by splitting the spritesheet into individual regions
        temp = TextureRegion.split(walkSheet, walkSheet.getWidth() / 4, walkSheet.getHeight() / 2);

        // one dimensional array to hold the running TextureRegions
        RunFrames = new TextureRegion[4 * 2];

        // Loop through the 2D array and transfer the TextureRegion to the one dimensional array
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                RunFrames[index++] = temp[i][j];
            }
        }

        // one dimensional array to hold the jump start TextureRegions
        JumpFrames1 = new Array<TextureRegion>();

        // Loop through the 2D array and transfer the TextureRegion to the one dimensional array
        for(int i = 0; i < 2 ; i++){
            JumpFrames1.add(new TextureRegion(jumpendsheet,jumpendsheet.getWidth()/2,jumpendsheet.getHeight()/1) );
        }

        temp = TextureRegion.split(jumpsheet,jumpsheet.getWidth()/3,jumpsheet.getHeight()/1);

        // one dimensional array to hold the jumping TextureRegions
        Jumpframes = new TextureRegion[3];

        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {
                Jumpframes[index++] = temp[i][j];
            }
        }

        temp = TextureRegion.split(slidingsheet, slidingsheet.getWidth()/2,slidingsheet.getHeight()/1);
        Slideframes = new TextureRegion[2];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                Slideframes[index++] = temp[i][j];
            }
        }
        temp = TextureRegion.split(slidingendsheet, slidingendsheet.getWidth()/1,slidingendsheet.getHeight()/1);
        Slideframesend = new TextureRegion[1];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                Slideframesend[index++] = temp[i][j];
            }
        }
        temp = TextureRegion.split(slimesheet, slimesheet.getWidth()/2,slimesheet.getHeight()/1);
        SlimeFrames = new TextureRegion[2];
        index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 2; j++) {
                SlimeFrames[index++] = temp[i][j];
            }
        }

        // Drop the TextureRegions into a new Animation object and set the framerate. As the
        // duration is set to 0.033, we will get 30 frames per second.
        RunningAnimation = new Animation(0.1f, RunFrames);
        JumpAnimation = new Animation(0.15f, Jumpframes);
        JumpAnimationend =  new Animation( 0.03f,JumpFrames1);
        SlideAnimation = new Animation(0.33f, Slideframes);
        SlideAnimationend = new Animation(0.1f, Slideframesend);
        SlimeAnimation = new Animation(0.15f, SlimeFrames);
        // Initialise the stateTime, aka how long the program has been running for.
        stateTime = 0.0f;

        playerDelta = new Vector2();

        batch = new SpriteBatch();

        characterX = 1;
        characterY = 1;

        //map
        tiledMap = new TmxMapLoader().load("Starting Assets/assets/level1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        //Camera
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w , h  );

        velocity = new Vector3(0,0,0);
        postion = new Vector3(0,400,0);

        stage = new Stage();
        currentframe = (TextureRegion) RunningAnimation.getKeyFrame(stateTime, true);
        slimeframe = (TextureRegion) SlimeAnimation.getKeyFrame(stateTime, true);

        currentstate = State.Running;

        playerSprite = new Sprite(currentframe);
        slimesprite = new Sprite(slimeframe);
        playerSprite.translateY(400);
        slimesprite.translateY(400);
        Gdx.input.setInputProcessor(stage);

    }
    private void update() {
        playerDelta.x =  MOVEMENT_SPEED * dt;

        playerSprite.translateX(playerDelta.x);
        slimesprite.translateX(playerDelta.x);
        camera.position.x += MOVEMENT_SPEED*dt;

        velocity.scl(dt);
        postion.add(0,velocity.y,0);
        velocity.scl(1/dt);
        playerSprite.setY(postion.y);


        if(postion.y > 440)
        {
            velocity.add(0,gravity,0);
            Timer.schedule(new Timer.Task() { @Override public void run() {  currentstate = State.Jumpend; } },0.15f);
        }
        if(postion.y <= 400){
            currentstate = State.Running;
            postion.y = 400;
        }
        if(letslide){
            currentstate = State.Sliding;
            Timer.schedule(new Timer.Task() { @Override public void run() {  currentstate = State.Slidend; } },0.33f);

            Timer.schedule(new Timer.Task() { @Override public void run() {  letslide = false; } },0.33f);

        }

        if(Gdx.input.justTouched()){
            if (Gdx.input.getY() < Gdx.graphics.getHeight() / 2){
                velocity.y = 450;
                velocity.add(0,gravity,0);
                currentstate = State.Jumping;
                jumped = true;
            }
            else {
                letslide = true;
            }
        }

    }
    public TextureRegion getcurrentstate(){

        if(currentstate == State.Running){
            currentframe = (TextureRegion) RunningAnimation.getKeyFrame(stateTime, true);
        }
        if(currentstate == State.Jumping){
            currentframe = (TextureRegion) JumpAnimation.getKeyFrame(stateTime, false);
        }
        if(currentstate == State.Jumpend){
            currentframe = (TextureRegion) JumpAnimationend.getKeyFrame(stateTime, false);
        }
        if(currentstate == State.Sliding){
            currentframe = (TextureRegion) SlideAnimation.getKeyFrame(stateTime, false);
        }
        if(currentstate == State.Slidend){
            currentframe = (TextureRegion) SlideAnimationend.getKeyFrame(stateTime, false);
        }

        return currentframe;

    }

    @Override
    public void show() {
        Gdx.app.log("Gamescreen: ","menuScreen show called");
        create();
    }

    @Override
    public void render(float delta) {
        dt = Gdx.graphics.getDeltaTime();
        currentframe = getcurrentstate();
        slimeframe = (TextureRegion) SlimeAnimation.getKeyFrame(stateTime, true);
        update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); //Allows transparent sprites/tiles
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();

        // Increase the time the game has been running by adding deltaTime (the time since the
        // last update).
        stateTime += Gdx.graphics.getDeltaTime();

        // Grabe the current frame from the animation.


        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.draw(currentframe,playerSprite.getX(),playerSprite.getY());
        int distance =800;
        for(int i = 0; i < 10; i++){

            batch.draw(slimeframe, distance*5*(i)-slimesprite.getX(),400);
            if(i%2==0 && i != 0)
                batch.draw(slimeframe, distance*4*(i),400);

            distance +=100;
        }


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
        batch.dispose();
        tiledMap.dispose();

    }
}
