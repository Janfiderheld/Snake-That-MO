package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.I18NBundle;
import com.muss_and_toeberg.snake_that.technical.Menu;
import java.util.Locale;

/**
 * MainGame
 */
public class MainGame extends Game {
    // Objects which are used throughout the whole game
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public BitmapFont font;
    public Music music;

    // constant values
    final int CAMERA_WIDTH = 1920;
    final int CAMERA_HEIGHT = 1080;

    // class variables
    public static Menu currentMenu;
    private FileHandle languageFileHandler;
    public static I18NBundle myLangBundle;

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

        music = Gdx.audio.newMusic(Gdx.files.internal("audio/despacito.mp3"));
        music.setLooping(true);
        music.play();

        font = new BitmapFont(Gdx.files.internal("fonts/Comic_Sans.fnt"));

        languageFileHandler = Gdx.files.internal("i18n/strings");
        myLangBundle = I18NBundle.createBundle(languageFileHandler);

        // Uncomment to check the german strings
        // Locale loc = new Locale("de");
        // myLangBundle = I18NBundle.createBundle(languageFileHandler, loc);
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
