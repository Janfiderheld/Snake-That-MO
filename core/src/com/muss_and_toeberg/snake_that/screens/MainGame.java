package com.muss_and_toeberg.snake_that.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.muss_and_toeberg.snake_that.technical.AudioController;
import com.muss_and_toeberg.snake_that.technical.Menu;

/**
 * MainGame
 */
public class MainGame extends Game {
    // Objects which are used throughout the whole game
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public BitmapFont fontHUD;
    public BitmapFont fontMainMenu;
    public AudioController soundControl;

    // constant values
    final int CAMERA_WIDTH = 1920;
    final int CAMERA_HEIGHT = 1080;

    // static objects
    public static Menu currentMenu;
    public static I18NBundle myLangBundle;
    public static TextButton.TextButtonStyle btnStyleMainMenuFont;

    // objects
    private FileHandle languageFileHandler;

    /**
     * gets called once when the game is created
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        currentMenu = Menu.None;
        soundControl = new AudioController();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        batch.setProjectionMatrix(camera.combined);

        fontHUD = new BitmapFont(Gdx.files.internal("fonts/Comic_Sans_HUD.fnt"));
        fontMainMenu = new BitmapFont(Gdx.files.internal("fonts/Comic_Sans_MainMenu.fnt"));

        languageFileHandler = Gdx.files.internal("i18n/strings");
        myLangBundle = I18NBundle.createBundle(languageFileHandler);

        createButtonStyleMainMenuFont();

        this.setScreen(new MainMenu(this));

        // Uncomment to check the german strings
        // Locale loc = new Locale("de");
        // myLangBundle = I18NBundle.createBundle(languageFileHandler, loc);
    }

    /**
     * creates the button style with the mainMenu font
     */
    private void createButtonStyleMainMenuFont() {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttonsControl.pack"));
        skin.addRegions(buttonAtlas);

        btnStyleMainMenuFont = new TextButton.TextButtonStyle();
        btnStyleMainMenuFont.font = fontMainMenu;
        btnStyleMainMenuFont.up = skin.getDrawable("up-button");
        btnStyleMainMenuFont.down = skin.getDrawable("down-button");
        btnStyleMainMenuFont.checked = skin.getDrawable("checked-button");
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
