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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.muss_and_toeberg.snake_that.technical.MainGame;
import com.muss_and_toeberg.snake_that.technical.controller.IMemoryController;
import com.muss_and_toeberg.snake_that.technical.Menu;

/**
 * Screen which is used to enter a new Highscore
 */
public class NewHighscore implements Screen {
    // constant values
    private final int MAX_USERNAME_LENGTH = 12;

    // objects & graphical elements
    private MainGame game;
    private Stage stage;
    private TextField.TextFieldStyle txtStyleNameInput;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * method head based on the top answer
     * <a href="https://stackoverflow.com/questions/25837013/switching-between-screens-libgdx">here</a>
     * @param game game object which allows screen changing
     * @param placement placement of the player
     * @param score points the player earned
     */
    public NewHighscore(final MainGame game, final int placement, final int score){
        this.game = game;
        MainGame.currentMenu = Menu.Highscore;

        // creates the Stage
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);

        // creates the Label-Style for the Scoretable
        Label.LabelStyle scoretableStyle = new Label.LabelStyle();
        scoretableStyle.font = game.fontHUD;
        scoretableStyle.fontColor = Color.WHITE;

        // create the Heading
        Label lblHeading = new Label(MainGame.myLangBundle.format("headerNewHS", placement + 1), scoretableStyle);

        // create the textfield to enter the name
        createTextfieldStyle();
        final TextField txtUserName = new TextField("", txtStyleNameInput);
        txtUserName.setMessageText(MainGame.myLangBundle.get("player"));
        txtUserName.setMaxLength(MAX_USERNAME_LENGTH);
        txtUserName.setSize(1000,200);
        txtUserName.setAlignment(Align.center);

        // create the button
        final TextButton btnSubmit = new TextButton(MainGame.myLangBundle.get("submit"), MainGame.btnStyleMainMenuFont);
        btnSubmit.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                String nameToControl = txtUserName.getText();
                if(nameToControl.contains(game.memController.getDelimiter())) {
                    game.androidFunctions.createAndShowToast(MainGame.myLangBundle.format("delEntered", game.memController.getDelimiter()), true);
                    btnSubmit.setChecked(false);
                } else {
                    game.memController.addScoreToHighscore(nameToControl, score, placement);
                    game.setScreen(new Highscores(game));
                    dispose();
                }
            }
        });

        // adds all buttons and labels into a Table
        Table menuTable = new Table();
        menuTable.bottom().left();
        menuTable.pad(0,500,300,0);
        menuTable.add(lblHeading).space(25, 25, 50, 50).row();
        menuTable.add(txtUserName).space(50,0,50,0).width(1000).row();
        menuTable.add(btnSubmit).space(50, 125, 25, 125).width(450);

        //adds table to stage
        stage.addActor(menuTable);
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
