package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.muss_and_toeberg.snake_that.technical.Menu;

/**
 * MainGame
 */
public class MainGame extends Game {
    // Objects which are used throughout the whole game
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public BitmapFont font;

    // constant values
    final int CAMERA_WIDTH = 1920;
    final int CAMERA_HEIGHT = 1080;

    // class variables
    public static Menu currentMenu;

    /**
     * gets called once when the game is created
     */
    @Override
    public void create() {
        this.setScreen(new MainMenu(this));
        batch = new SpriteBatch();
        currentMenu = Menu.None;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        batch.setProjectionMatrix(camera.combined);

        font = new BitmapFont(Gdx.files.internal("Comic_Sans.fnt"));
    }

    /**
     * renders the game in a constant loop
     */
    @Override
    public void render(){
        super.render();
    }

    /**
     * gets called when the game is disposed
     */
    public void dispose() {
        batch.dispose();
    }
}
