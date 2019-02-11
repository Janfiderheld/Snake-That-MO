package com.muss_and_toeberg.snake_that.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.muss_and_toeberg.snake_that.technical.MainGame;
import com.muss_and_toeberg.snake_that.technical.Menu;

/**
 * Screen which contains the Highscores
 */
public class Highscores implements Screen {
    // objects & graphical elements
    private MainGame game;
    private Stage stage;
    private boolean shouldBeRefreshed = false;
    private final TextButton btnWritePermission = new TextButton(MainGame.myLangBundle.get("writePerm"), MainGame.btnStyleMainMenuFont);

    /**
     * Constructor which is used to create all objects that only need to
     * be created once
     * method head based on the top answer
     * <a href="https://stackoverflow.com/questions/25837013/switching-between-screens-libgdx">here</a>
     * @param game game object which allows screen changing
     */
    public Highscores(final MainGame game){
        this.game = game;
        MainGame.currentMenu = Menu.Highscore;

        // creates the Stage
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);
        fillTheStage();
    }

    /**
     * fills the stage with everything
     */
    private void fillTheStage() {
        // creates the Label-Style for the Scoretable
        Label.LabelStyle scoretableStyle = new Label.LabelStyle();
        scoretableStyle.font = game.fontHUD;
        scoretableStyle.fontColor = Color.WHITE;

        // create the Labels
        Label[] lblNames = new Label[game.memController.NUMBER_PLAYERS];
        Label[] lblScores = new Label[game.memController.NUMBER_PLAYERS];
        String[] names = game.memController.getHighscoreNamesOrPoints(0);
        String[] points = game.memController.getHighscoreNamesOrPoints(1);
        for (int count = 0; count < game.memController.NUMBER_PLAYERS; count++) {
            lblNames[count] = new Label(names[count], scoretableStyle);
            lblScores[count] = new Label(points[count], scoretableStyle);
        }

        // create the Button
        TextButton btnBackMainMenu = new TextButton(MainGame.myLangBundle.get("backToMM"), MainGame.btnStyleMainMenuFont);
        btnBackMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.goBackToMainMenu(Highscores.this);
            }
        });

        // creates the Leaderboard
        Table leaderboard = new Table();
        leaderboard.bottom().left();
        leaderboard.pad(0,400,200,0);
        for (int count = 0; count < game.memController.NUMBER_PLAYERS; count++){
            leaderboard.add(new Label(String.valueOf(count + 1) + ". ", scoretableStyle));
            leaderboard.add(lblNames[count]);
            leaderboard.add(new Label(" - ", scoretableStyle));
            leaderboard.add(lblScores[count]);
            leaderboard.row();
        }

        // creates the MenuTable
        Table menuTable = new Table();
        menuTable.bottom().left();
        menuTable.add(btnBackMainMenu).width(MainMenu.BACK_MM_BUTTON_WIDTH).align(Align.bottomLeft);

        // adds everything related to the writing permission
        btnWritePermission.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.androidFunctions.askForWritePermission();
                btnWritePermission.setChecked(false);
                shouldBeRefreshed = true;
            }
        });

        // adds the permission button only if the permission has not been given
        if(!game.androidFunctions.checkWritePermission()) {
            menuTable.add(btnWritePermission).width(600).spaceLeft(775).align(Align.bottomRight);
        }

        // add the Tables to the Stage
        stage.addActor(leaderboard);
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
        game.batch.end();
        stage.draw();
        game.camera.update();
        game.checkBackAndCloseScreen(this);

        if(shouldBeRefreshed) {
            stage.clear();
            fillTheStage();
            shouldBeRefreshed = false;

            if(!game.androidFunctions.checkWritePermission()) {
                btnWritePermission.remove();
            }
        }
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
