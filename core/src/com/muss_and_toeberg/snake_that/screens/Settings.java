package com.muss_and_toeberg.snake_that.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.muss_and_toeberg.snake_that.game_objects.Snake;
import com.muss_and_toeberg.snake_that.technical.MainGame;
import com.muss_and_toeberg.snake_that.technical.Menu;
import com.muss_and_toeberg.snake_that.technical.SnakeColor;

/**
 * Screen which contains the Settings
 * @author Niclas Muss
 */
public class Settings implements Screen {
    // constant values
    private int ON_OFF_BUTTON_WIDTH = 200;
    private int COLOR_BUTTON_WIDTH = 128;
    private int COLOR_BUTTON_AMOUNT = 6;
    private int LANG_BUTTON_WIDTH = 164;
    public static final int NUMBER_SETTINGS = 4;

    // objects & graphical elements
    private MainGame game;
    private Stage stage;
    private TextButton.TextButtonStyle btnStyleSettingsOnOff;
    private TextButton.TextButtonStyle btnStyleLanguage;
    private Array<TextButton> colorButtons = new Array<TextButton>(COLOR_BUTTON_AMOUNT);

    // static option fields
    private static boolean[] settings = new boolean[NUMBER_SETTINGS];
    private static final int INDEX_MUSIC = 0;
    private static final int INDEX_SOUND = 1;
    private static final int INDEX_CHRISTMAS = 2;
    private static final int INDEX_LANGUAGE = 3;

    // variables
    private float headerStartX;
    private float headerStartY;
    private int resetButtonWidth = 700;

    /**
     * Constructor which is used to create all objects that only need to
     * be created once
     * method head based on the top answer
     * <a href="https://stackoverflow.com/questions/25837013/switching-between-screens-libgdx">
     *     here</a>
     * @param game game object which allows screen changing
     */
    public Settings(final MainGame game) {
        this.game = game;
        MainGame.currentMenu = Menu.Settings;

        // calculate the variables
        headerStartX = MainGame.CAMERA_WIDTH / 2 - 350;
        headerStartY = MainGame.CAMERA_HEIGHT - 25;
        if(Settings.checkForGermanLanguage()) {
            resetButtonWidth = 915;
        }

        // creates the Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // creates the LabelStyle
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = game.fontMainMenu;
        lblStyle.fontColor = Color.WHITE;

        // create the Labels
        Label lblMusic = new Label(MainGame.myLangBundle.get("music"),
                lblStyle);
        Label lblSound = new Label(MainGame.myLangBundle.get("sound"),
                lblStyle);
        Label lblChristmas = new Label(MainGame.myLangBundle.get("christmas"),
                lblStyle);
        Label lblLanguage = new Label(MainGame.myLangBundle.get("lang"),
                lblStyle);
        Label lblColor = new Label(MainGame.myLangBundle.get("color"),
                lblStyle);

        // create the Buttons
        createButtonStyleSettingsOnOff();
        createButtonStyleLanguage();
        TextButton btnMusic = new TextButton(MainGame.myLangBundle.get("on"),
                btnStyleSettingsOnOff);
        TextButton btnSound = new TextButton(MainGame.myLangBundle.get("on"),
                btnStyleSettingsOnOff);
        TextButton btnChristmas = new TextButton(
                MainGame.myLangBundle.get("on"), btnStyleSettingsOnOff);
        TextButton btnLanguage = new TextButton("", btnStyleLanguage);
        TextButton btnReset = new TextButton(MainGame.myLangBundle.get("reset"),
                MainGame.btnStyleMainMenuFont);
        TextButton btnBackMainMenu = new TextButton(
                MainGame.myLangBundle.get("backToMM"),
                MainGame.btnStyleMainMenuFont);

        // check if the buttons should be checked
        if(!Settings.checkMusicTurnedOn()) {
            btnMusic.setChecked(true);
            btnMusic.setText(MainGame.myLangBundle.get("off"));
        }

        if(!Settings.checkSoundsTurnedOn()) {
            btnSound.setChecked(true);
            btnSound.setText(MainGame.myLangBundle.get("off"));
        }

        if(!Settings.checkForChristmas()) {
            btnChristmas.setChecked(true);
            btnChristmas.setText(MainGame.myLangBundle.get("off"));
        }

        if(!Settings.checkForGermanLanguage()) {
            btnLanguage.setChecked(true);
        }

        // create the color buttons
        for(int count = 0; count < COLOR_BUTTON_AMOUNT; count++) {
            TextButton tempButton = new TextButton("",
                    createButtonStyleColors(count));
            if(Snake.getColorAsEnum() == SnakeColor.makeIntToSnakeColor(count)) {
                tempButton.setChecked(true);
            }
            final int finalCount = count;
            tempButton.addListener(new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    if(((TextButton)actor).isChecked()) {
                        SnakeColor tempColor =
                                SnakeColor.makeIntToSnakeColor(finalCount);
                        Snake.setColorByEnum(tempColor);
                        uncheckAllColorButtons(tempColor);
                    }
                }
            });
            colorButtons.add(tempButton);
        }

        // add the action listeners to the Buttons
        btnMusic.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(Settings.checkMusicTurnedOn()) {
                    Settings.setMusic(false);
                    ((TextButton)actor).setText(
                            MainGame.myLangBundle.get("off"));
                    game.soundControl.pauseBackgroundMusic();
                } else {
                    Settings.setMusic(true);
                    ((TextButton)actor).setText(
                            MainGame.myLangBundle.get("on"));
                    game.soundControl.startBackgroundMusic();
                }
                game.memController.saveSettings();
            }
        });

        btnSound.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(Settings.checkSoundsTurnedOn()) {
                    Settings.setSound(false);
                    ((TextButton)actor).setText(
                            MainGame.myLangBundle.get("off"));
                } else {
                    Settings.setSound(true);
                    ((TextButton)actor).setText(
                            MainGame.myLangBundle.get("on"));
                }
                game.memController.saveSettings();
            }
        });

        btnChristmas.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(Settings.checkForChristmas()) {
                    Settings.setChristmasTheme(false);
                    ((TextButton)actor).setText(
                            MainGame.myLangBundle.get("off"));
                } else {
                    Settings.setChristmasTheme(true);
                    ((TextButton)actor).setText(
                            MainGame.myLangBundle.get("on"));
                }
                game.memController.saveSettings();
            }
        });

        btnLanguage.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(Settings.checkForGermanLanguage()) {
                    game.changeLocale(false);
                } else {
                    game.changeLocale(true);
                }
                game.memController.saveSettings();
                game.setScreen(new Settings(game));
                dispose();
            }
        });

        btnReset.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.memController.createEmptyHighscore();
                ((TextButton)actor).setChecked(false);
            }
        });

        btnBackMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.goBackToMainMenu(Settings.this);
            }
        });

        // adds all the Labels and On/Off-Buttons into a Table
        Table menuTable = new Table();
        menuTable.bottom().left();
        menuTable.pad(0,25,400,0);
        menuTable.add(lblMusic);
        menuTable.add(btnMusic).space(10, 10, 10, 10).
                width(ON_OFF_BUTTON_WIDTH);
        menuTable.add(lblSound).spaceLeft(300);
        menuTable.add(btnSound).space(10, 10, 10, 10).
                width(ON_OFF_BUTTON_WIDTH).row();
        menuTable.add(lblChristmas);
        menuTable.add(btnChristmas).
                space(10, 10, 10, 10).
                width(ON_OFF_BUTTON_WIDTH);
        menuTable.add(lblLanguage).spaceLeft(300);
        menuTable.add(btnLanguage).
                space(10, 10, 10, 10).
                width(LANG_BUTTON_WIDTH).row();
        menuTable.add(lblColor);

        // adds the color buttons into a Table
        Table colorTable = new Table();
        colorTable.bottom().left();
        colorTable.pad(0,25,250,0);
        for(int count = 0; count < COLOR_BUTTON_AMOUNT; count++) {
            colorTable.add(colorButtons.get(count)).
                    space(10, 200, 100, 10).
                    width(COLOR_BUTTON_WIDTH);
        }

        // adds the buttons to a Table
        Table btnTable = new Table();
        btnTable.bottom().left();
        btnTable.add(btnBackMainMenu).width(MainMenu.BACK_MM_BUTTON_WIDTH).
                align(Align.bottomLeft);
        btnTable.add(btnReset).width(resetButtonWidth).
                spaceLeft(MainGame.CAMERA_WIDTH - resetButtonWidth -
                        MainMenu.BACK_MM_BUTTON_WIDTH);

        //add the Tables to the Stage
        stage.addActor(menuTable);
        stage.addActor(colorTable);
        stage.addActor(btnTable);
    }

    /**
     * renders the screen (= fills it with everything)
     * gets called in a constant loop
     * @param delta time since the last render
     */
    @Override
    public void render(float delta) {
        game.batch.begin();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.fontHUD.draw(game.batch, MainGame.myLangBundle.get("settings"),
                headerStartX, headerStartY);
        game.batch.end();
        stage.draw();
        game.camera.update();
        game.checkBackAndCloseScreen(this);
    }

    /**
     * @return true when the music is on
     */
    public static boolean checkMusicTurnedOn() {
        return settings[INDEX_MUSIC];
    }

    /**
     * @return true when the sound is on
     */
    public static boolean checkSoundsTurnedOn() {
        return settings[INDEX_SOUND];
    }

    /**
     * @return true when the christams theme is on
     */
    public static boolean checkForChristmas() {
        return settings[INDEX_CHRISTMAS];
    }

    /**
     * @return true when the language is german
     */
    public static boolean checkForGermanLanguage() {
        return settings[INDEX_LANGUAGE];
    }

    /**
     * sets the language to the given parameter
     * @param toGerman value to set
     */
    public static void setLanguage(boolean toGerman) {
        settings[INDEX_LANGUAGE] = toGerman;
    }

    /**
     * sets the Music to the given value
     * @param on true if music should be on
     */
    public static void setMusic(boolean on) {
        settings[INDEX_MUSIC] = on;
    }

    /**
     * sets the Sound to the given value
     * @param on true if sound should be on
     */
    public static void setSound(boolean on) {
        settings[INDEX_SOUND] = on;
    }

    /**
     * sets the Christmas Theme to the given value
     * @param on true if christmas should be on
     */
    public static void setChristmasTheme(boolean on) {
        settings[INDEX_CHRISTMAS] = on;
    }

    /**
     * @return settings-array (boolean values)
     */
    public static boolean[] getSettings() {
        return settings;
    }

    /**
     * sets the settings array to the given values
     * @param newSettings new array to set
     */
    public static void setSettings(boolean[] newSettings) {
        settings = newSettings;
    }

    /**
     * unchecks all other color buttons except the one which was just checked
     * @param color of the button which was just checked
     */
    private void uncheckAllColorButtons(SnakeColor color) {
        for (int count = 0; count < COLOR_BUTTON_AMOUNT; count++) {
            TextButton tempButton = colorButtons.get(count);
            if (SnakeColor.makeSnakeColorToInt(color) != count) {
                tempButton.setChecked(false);
            }
        }
    }

    /**
     * creates the style for the On-And-Off-Buttons
     */
    private void createButtonStyleSettingsOnOff() {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(
                Gdx.files.internal("buttons/buttonsSettings.pack"));
        skin.addRegions(buttonAtlas);

        btnStyleSettingsOnOff = new TextButton.TextButtonStyle();
        btnStyleSettingsOnOff.font = game.fontMainMenu;
        btnStyleSettingsOnOff.up = skin.getDrawable("up-button");
        btnStyleSettingsOnOff.checked = skin.getDrawable("checked-button");
    }

    /**
     * creates the style for the language button
     * the flags were made by the Gang of the Coconuts:
     * https://www.free-country-flags.com/index.php
     */
    private void createButtonStyleLanguage() {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(
                Gdx.files.internal("buttons/buttonLanguage.pack"));
        skin.addRegions(buttonAtlas);

        btnStyleLanguage = new TextButton.TextButtonStyle();
        btnStyleLanguage.font = game.fontCredits;
        btnStyleLanguage.up = skin.getDrawable("german");
        btnStyleLanguage.checked = skin.getDrawable("english");
    }

    /**
     * creates a button style for the different color-choosing-buttons
     * @param chosenColor which color to create
     * @return button style
     */
    private TextButton.TextButtonStyle createButtonStyleColors(int chosenColor) {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(
                Gdx.files.internal("buttons/buttonsColors.pack"));
        skin.addRegions(buttonAtlas);

        TextButton.TextButtonStyle tempButtonStyle =
                new TextButton.TextButtonStyle();
        tempButtonStyle.font = game.fontMainMenu;

        switch (chosenColor) {
            case 0:
                // Red
                tempButtonStyle.checked = skin.getDrawable("Red_Marked");
                tempButtonStyle.up = skin.getDrawable("Red");
                break;
            case 1:
                // Green
                tempButtonStyle.checked = skin.getDrawable("Green_Marked");
                tempButtonStyle.up = skin.getDrawable("Green");
                break;
            case 2:
                // Blue
                tempButtonStyle.checked = skin.getDrawable("Blue_Marked");
                tempButtonStyle.up = skin.getDrawable("Blue");
                break;
            case 3:
                // Magenta
                tempButtonStyle.checked = skin.getDrawable("Magenta_Marked");
                tempButtonStyle.up = skin.getDrawable("Magenta");
                break;
            case 4:
                // Yellow
                tempButtonStyle.checked = skin.getDrawable("Yellow_Marked");
                tempButtonStyle.up = skin.getDrawable("Yellow");
                break;
            case 5:
                // Cyan
                tempButtonStyle.checked = skin.getDrawable("Cyan_Marked");
                tempButtonStyle.up = skin.getDrawable("Cyan");
                break;
        }

        return tempButtonStyle;
    }

    /**
     * disposes all used resources
     */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * NOT USED.
     * Called when this screen becomes the current screen for a Game.
     * --> everything is done in the constructor.
     * @see Screen#show() ()
     */
    @Override
    public void show() {

    }

    /**
     * NOT USED.
     * would be called when the screen gets resized
     * @param width new width of the screen
     * @param height new height of the screen
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * NOT USED.
     * Called when the Application is paused, usually when it's not active
     * or visible on screen.
     * @see Screen#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * NOT USED.
     * Called when the Application is resumed from a paused state,
     * usually when it regains focus.
     * @see Screen#resume() ()
     */
    @Override
    public void resume() {

    }

    /**
     * NOT USED.
     * Called when this screen is no longer the current screen for a Game.
     * --> we dispose everything when its not used.
     * @see Screen#hide()
     */
    @Override
    public void hide() {

    }
}
