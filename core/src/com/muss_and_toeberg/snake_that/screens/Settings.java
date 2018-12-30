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
import com.muss_and_toeberg.snake_that.technical.Menu;
import com.muss_and_toeberg.snake_that.technical.SnakeColor;

/**
 * the settings menu where the player can turn the music on and off and choose the snake color
 */
public class Settings implements Screen {
    // constant values
    private int ON_OFF_BUTTON_WIDTH = 200;
    private int COLOR_BUTTON_WIDTH = 128;
    private int COLOR_BUTTON_AMOUNT = 6;

    // objects & graphical elements
    private MainGame game;
    private Stage stage;
    private TextButton.TextButtonStyle btnStyleSettingsOnOff;
    private Array<TextButton> colorButtons = new Array<TextButton>(COLOR_BUTTON_AMOUNT);

    // static option fields
    public static boolean musicTurnedOn = true;
    public static boolean soundTurnedOn = true;
    public static boolean christmasTheme = true;
    // TODO: Add the possibility to change the player name
    public static String playerName = "";

    // variables
    private float headerStartX;
    private float headerStartY;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public Settings(final MainGame game) {
        this.game = game;
        MainGame.currentMenu = Menu.Settings;

        headerStartX = game.CAMERA_WIDTH / 2 - 200;
        headerStartY = game.CAMERA_HEIGHT - 25;

        playerName = MainGame.myLangBundle.get("player");

        // creates the Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // creates the LabelStyle
        Label.LabelStyle lblStyle = new Label.LabelStyle();
        lblStyle.font = game.fontMainMenu;
        lblStyle.fontColor = Color.WHITE;

        // create the Labels
        Label lblMusic = new Label(MainGame.myLangBundle.get("music"), lblStyle);
        Label lblSound = new Label(MainGame.myLangBundle.get("sound"), lblStyle);
        Label lblChristmas = new Label(MainGame.myLangBundle.get("christmas"), lblStyle);
        Label lblColor = new Label(MainGame.myLangBundle.get("color"), lblStyle);

        // create the Buttons
        createButtonStyleSettingsOnOff();
        TextButton btnMusic = new TextButton(MainGame.myLangBundle.get("on"), btnStyleSettingsOnOff);
        TextButton btnSound = new TextButton(MainGame.myLangBundle.get("on"), btnStyleSettingsOnOff);
        TextButton btnChristmas = new TextButton(MainGame.myLangBundle.get("on"), btnStyleSettingsOnOff);
        TextButton btnBackMainMenu = new TextButton(MainGame.myLangBundle.get("backToMM"), MainGame.btnStyleMainMenuFont);

        // check if the buttons should be checked
        if(!Settings.musicTurnedOn) {
            btnMusic.setChecked(true);
            btnMusic.setText(MainGame.myLangBundle.get("off"));
        }

        if(!Settings.soundTurnedOn) {
            btnSound.setChecked(true);
            btnSound.setText(MainGame.myLangBundle.get("off"));
        }

        if(!Settings.christmasTheme) {
            btnChristmas.setChecked(true);
            btnChristmas.setText(MainGame.myLangBundle.get("off"));
        }

        // create the color buttons
        for(int count = 0; count < COLOR_BUTTON_AMOUNT; count++) {
            TextButton tempButton = new TextButton("", createButtonStyleColors(count));
            if(Snake.getColorAsEnum() == SnakeColor.makeIntToSnakeColor(count)) {
                tempButton.setChecked(true);
            }
            final int finalCount = count;
            tempButton.addListener(new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    if(((TextButton)actor).isChecked()) {
                        SnakeColor tempColor = SnakeColor.makeIntToSnakeColor(finalCount);
                        Snake.setColorByRGB(tempColor);
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
                if(Settings.musicTurnedOn) {
                    Settings.musicTurnedOn = false;
                    ((TextButton)actor).setText(MainGame.myLangBundle.get("off"));
                    game.soundControl.pauseBackgroundMusic();
                } else {
                    Settings.musicTurnedOn = true;
                    ((TextButton)actor).setText(MainGame.myLangBundle.get("on"));
                    game.soundControl.startBackgroundMusic();
                }
            }
        });

        btnSound.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(Settings.soundTurnedOn) {
                    Settings.soundTurnedOn = false;
                    ((TextButton)actor).setText(MainGame.myLangBundle.get("off"));
                } else {
                    Settings.soundTurnedOn = true;
                    ((TextButton)actor).setText(MainGame.myLangBundle.get("on"));
                }
            }
        });

        btnChristmas.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                if(Settings.christmasTheme) {
                    Settings.christmasTheme = false;
                    ((TextButton)actor).setText(MainGame.myLangBundle.get("off"));
                } else {
                    Settings.christmasTheme = true;
                    ((TextButton)actor).setText(MainGame.myLangBundle.get("on"));
                }
            }
        });

        btnBackMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        // adds all the elements into a Table
        Table menuTable = new Table();
        menuTable.bottom().left();
        menuTable.add(lblMusic);
        menuTable.add(btnMusic).space(10, 10, 10, 10).width(ON_OFF_BUTTON_WIDTH);
        menuTable.add(lblSound).spaceLeft(300);
        menuTable.add(btnSound).space(10, 10, 10, 10).width(ON_OFF_BUTTON_WIDTH).row();
        menuTable.add(lblChristmas);
        menuTable.add(btnChristmas).space(10, 10, 10, 10).width(ON_OFF_BUTTON_WIDTH).row();
        menuTable.add(lblColor).row();
        // TODO: change position and layout of the color buttons
        for(int count = 0; count < COLOR_BUTTON_AMOUNT; count++) {
            menuTable.add(colorButtons.get(count)).space(10, 10, 100, 10).width(COLOR_BUTTON_WIDTH);
        }
        menuTable.row();
        menuTable.add(btnBackMainMenu).width(550).align(Align.bottomLeft);

        //add the Table to the Stage
        stage.addActor(menuTable);
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
        game.fontHUD.draw(game.batch, MainGame.myLangBundle.get("settings"), headerStartX, headerStartY);
        game.batch.end();
        stage.draw();
        game.camera.update();
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
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttonsSettings.pack"));
        skin.addRegions(buttonAtlas);

        btnStyleSettingsOnOff = new TextButton.TextButtonStyle();
        btnStyleSettingsOnOff.font = game.fontMainMenu;
        btnStyleSettingsOnOff.up = skin.getDrawable("up-button");
        btnStyleSettingsOnOff.checked = skin.getDrawable("checked-button");
    }

    /**
     * creates a button style for the different color-choosing-buttons
     * @param chosenColor which color to create
     * @return button style
     */
    private TextButton.TextButtonStyle createButtonStyleColors(int chosenColor) {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttonsColors.pack"));
        skin.addRegions(buttonAtlas);

        TextButton.TextButtonStyle tempButtonStyle = new TextButton.TextButtonStyle();
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


    // currently not used implements of Screen
    @Override
    public void show() {

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
