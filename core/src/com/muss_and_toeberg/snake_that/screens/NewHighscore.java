package com.muss_and_toeberg.snake_that.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.muss_and_toeberg.snake_that.technical.MainGame;
import com.muss_and_toeberg.snake_that.technical.Menu;

public class NewHighscore implements Screen {
    // constant values
    private final int MAX_USERNAME_LENGTH = 12;

    // objects & graphical elements
    private MainGame game;
    private Stage stage;
    private  TextField.TextFieldStyle txtStyleNameInput;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     * @param placement placement of the player
     * @param score points the player earned
     */
    public NewHighscore(final MainGame game, final int placement, final int score){
        this.game = game;
        MainGame.currentMenu = Menu.Highscore;

        // creates the Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // creates the Label-Style for the Scoretable
        Label.LabelStyle scoretableStyle = new Label.LabelStyle();
        scoretableStyle.font = game.fontHUD;
        scoretableStyle.fontColor = Color.WHITE;

        // create the Label
        Label lblHeading = new Label(MainGame.myLangBundle.format("headerNewHS", placement + 1), scoretableStyle);
        lblHeading.setPosition((game.CAMERA_WIDTH / 2) - 400,(game.CAMERA_HEIGHT / 2) + 200);

        // create the textfield to enter the name
        createTextfieldStyle();
        final TextField txtUserName = new TextField("", txtStyleNameInput);
        txtUserName.setMessageText(MainGame.myLangBundle.get("player"));
        txtUserName.setMaxLength(MAX_USERNAME_LENGTH);
        txtUserName.setPosition((game.CAMERA_WIDTH / 2) - 425,(game.CAMERA_HEIGHT / 2) - 100);
        txtUserName.setSize(1000,200);
        txtUserName.setAlignment(Align.center);

        // create the button
        TextButton btnSubmit = new TextButton(MainGame.myLangBundle.get("submit"), MainGame.btnStyleMainMenuFont);
        btnSubmit.setPosition((game.CAMERA_WIDTH / 2) - 100,200);
        btnSubmit.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.memController.addScoreToHighscore(txtUserName.getText(), score, placement);
                game.setScreen(new Highscores(game));
                dispose();
            }
        });

        //add actors to stage
        stage.addActor(txtUserName);
        stage.addActor(lblHeading);
        stage.addActor(btnSubmit);
    }

    /**
     * renders the screen (= fills it with everything)
     * gets called in a constant loop
     * @param delta time since the last render
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        game.camera.update();
    }

    /**
     * creates the Style for the Textfield
     */
    private void createTextfieldStyle() {
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttonsColors.pack"));
        skin.addRegions(buttonAtlas);

        txtStyleNameInput = new TextField.TextFieldStyle();
        txtStyleNameInput.font = new BitmapFont(Gdx.files.internal("fonts/ComicSans_HUD.fnt"));
        txtStyleNameInput.fontColor = Color.BLACK;
        txtStyleNameInput.background = skin.getDrawable("Down");
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
