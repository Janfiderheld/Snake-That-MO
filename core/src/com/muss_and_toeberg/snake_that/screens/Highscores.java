package com.muss_and_toeberg.snake_that.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.muss_and_toeberg.snake_that.technical.Menu;

/**
 * Highscore Menu Screen
 */
public class Highscores implements Screen {
    // objects & graphical elements
    private MainGame game;
    private Stage stage;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public Highscores(final MainGame game){
        this.game = game;
        MainGame.currentMenu = Menu.Highscore;

        // creates the Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // creates the Label-Style for the Scoretable
        Label.LabelStyle scoretableStyle = new Label.LabelStyle();
        scoretableStyle.font = game.fontHUD;
        scoretableStyle.fontColor = Color.WHITE;

        // create the Labels
        Label[] lblNames = new Label[game.memController.NUMBER_PLAYERS];
        Label[] lblScores = new Label[game.memController.NUMBER_PLAYERS];
        String[] names = game.memController.getHighscoreNamesOrPoints(0);
        String[] points = game.memController.getHighscoreNamesOrPoints(1);
        for (int i = 0; i < game.memController.NUMBER_PLAYERS; i++) {
            lblNames[i] = new Label(names[i], scoretableStyle);
            lblScores[i] = new Label(points[i], scoretableStyle);
        }

        // create the Button
        TextButton btnBackMainMenu = new TextButton(MainGame.myLangBundle.get("backToMM"), MainGame.btnStyleMainMenuFont);
        btnBackMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        // creates the Leaderboard
        Table leaderboard = new Table();
        leaderboard.bottom().left();
        leaderboard.pad(0,400,200,0);
        for (int i = 0; i < game.memController.NUMBER_PLAYERS; i++){
            leaderboard.add(new Label(String.valueOf(i + 1) + ". ", scoretableStyle));
            leaderboard.add(lblNames[i]);
            leaderboard.add(new Label(" - ", scoretableStyle));
            leaderboard.add(lblScores[i]);
            leaderboard.row();
        }

        // creates the MenuTable
        Table menuTable = new Table();
        menuTable.bottom().left();
        menuTable.add(btnBackMainMenu).width(MainMenu.BACK_MM_BUTTON_WIDTH).align(Align.bottomLeft);

        //add the Tables to the Stage
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

        if (game.backReleased && Gdx.input.isKeyPressed(Input.Keys.BACK))
        {
            game.backReleased = false;
            backtoMainMenu();
        }
        else
        {
            game.backReleased = true;
        }
    }

    /**
     * return to the Main Menu Screen
     */
    public void backtoMainMenu (){
        game.setScreen(new MainMenu(game));
        dispose();
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
