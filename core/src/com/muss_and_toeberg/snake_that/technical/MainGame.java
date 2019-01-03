package com.muss_and_toeberg.snake_that.technical;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.muss_and_toeberg.snake_that.screens.MainMenu;
import com.muss_and_toeberg.snake_that.screens.Settings;
import java.util.Locale;

/**
 * MainGame
 */
public class MainGame extends Game {
    // Objects which are used throughout the whole game
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public AudioController soundControl;
    public MemoryController memController;

    // Fonts
    public BitmapFont fontHUD;
    public BitmapFont fontMainMenu;
    public BitmapFont fontCredits;
    public BitmapFont fontDescription;

    // constant values
    public final int CAMERA_WIDTH = 1920;
    public final int CAMERA_HEIGHT = 1080;

    // static objects
    public static Menu currentMenu;
    public static I18NBundle myLangBundle;
    public static TextButton.TextButtonStyle btnStyleMainMenuFont;

    // objects & variables
    private FileHandle languageFileHandler;
    private boolean backReleased = true;

    /**
     * gets called once when the game is created
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        currentMenu = Menu.None;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);
        batch.setProjectionMatrix(camera.combined);

        fontHUD = new BitmapFont(Gdx.files.internal("fonts/ComicSans_HUD.fnt"));
        fontMainMenu = new BitmapFont(Gdx.files.internal("fonts/ComicSans_MainMenu.fnt"));
        fontCredits = new BitmapFont(Gdx.files.internal("fonts/ComicSans_Credits.fnt"));
        fontDescription = new BitmapFont(Gdx.files.internal("fonts/ComicSans_Desc.fnt"));

        soundControl = new AudioController();
        memController = new MemoryController();

        Settings.setSettings(memController.readSettingsFromFile());
        languageFileHandler = Gdx.files.internal("i18n/strings");
        changeLocale(Settings.isLanguageGerman());

        Gdx.input.setCatchBackKey(true);

        createButtonStyleMainMenuFont();
        this.setScreen(new MainMenu(this));
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
     * changes the displayed language
     * @param changeToGerman true if the language should be changed to german
     */
    public void changeLocale(boolean changeToGerman) {
        Settings.setLanguage(changeToGerman);
        String encoding = "ISO-8859-1";
        if(changeToGerman) {
            myLangBundle = I18NBundle.createBundle(languageFileHandler, Locale.GERMAN, encoding);
        } else {
            myLangBundle = I18NBundle.createBundle(languageFileHandler, Locale.ENGLISH, encoding);
        }
    }

    /**
     * checks if the current screen should be disposed off
     * @param toDispose Screen to dispose (eventually)
     */
    public void checkBackAndCloseScreen(Screen toDispose) {
        if (backReleased && Gdx.input.isKeyPressed(Input.Keys.BACK))
        {
            backReleased = false;
            backToMainMenu(toDispose);
        } else {
            backReleased = true;
        }
    }

    /**
     * return to the Main Menu Screen and dispose the other
     * @param toDispose Screen to dispose
     */
    public void backToMainMenu(Screen toDispose){
        setScreen(new MainMenu(this));
        toDispose.dispose();
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
