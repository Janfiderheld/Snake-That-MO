package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends Game {

    public SpriteBatch batch;
    public Texture texture;

    public OrthographicCamera camera;
    final int CAMERA_WIDTH = 1920;
    final int CAMERA_HEIGHT = 1080;

    public BitmapFont font;

    public static boolean levelStarted = false;

    @Override
    public void create() {
        this.setScreen(new MainMenu(this));
        batch = new SpriteBatch();
        texture =  new Texture("WaluigiBlock.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

        font = new BitmapFont(Gdx.files.internal("Comic_Sans.fnt"));
    }

    @Override
    public void render(){
        super.render();
    }

    public void dispose() {
        batch.dispose();
    }
}
