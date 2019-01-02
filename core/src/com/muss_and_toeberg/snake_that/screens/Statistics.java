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
import com.muss_and_toeberg.snake_that.technical.MemoryController;
import com.muss_and_toeberg.snake_that.technical.Menu;

public class Statistics implements Screen {
    // objects & graphical elements
    private MainGame game;
    private Stage stage;


    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public Statistics(final MainGame game) {
        this.game = game;
        MainGame.currentMenu = Menu.Statistics;

        // creates the Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // creates the Label-Style for the Statstable
        Label.LabelStyle statstableStyle = new Label.LabelStyle();
        statstableStyle.font = game.fontHUD;
        statstableStyle.fontColor = Color.WHITE;

        // create the Button
        TextButton btnBackMainMenu = new TextButton(MainGame.myLangBundle.get("backToMM"), MainGame.btnStyleMainMenuFont);
        btnBackMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.backToMainMenu(Statistics.this);
            }
        });

        // create the Table with the stats
        Table statsTable = new Table();
        statsTable.bottom().left();
        statsTable.pad(0,400,200,0);
        statsTable.add(new Label(MainGame.myLangBundle.format("length", game.memController.getStat(game.memController.INDEX_LENGTH)), statstableStyle)).row();
        statsTable.add(new Label(MainGame.myLangBundle.format("barrels", game.memController.getStat(game.memController.INDEX_BARRELS)), statstableStyle)).row();
        statsTable.add(new Label(MainGame.myLangBundle.format("games", game.memController.getStat(game.memController.INDEX_GAMES_NO)), statstableStyle)).row();
        statsTable.add(new Label(MainGame.myLangBundle.format("time", game.memController.getStat(game.memController.INDEX_LONG_RUN)), statstableStyle));

        // add the button into a Table
        Table menuTable = new Table();
        menuTable.bottom().left();
        menuTable.add(btnBackMainMenu).width(MainMenu.BACK_MM_BUTTON_WIDTH).align(Align.bottomLeft);

        //add the Table to the Stage
        stage.addActor(menuTable);
        stage.addActor(statsTable);
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
