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

        // create the Buttons
        TextButton btnBackMainMenu = new TextButton(MainGame.myLangBundle.get("backToMM"), MainGame.btnStyleMainMenuFont);
        btnBackMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        Table statsTable = new Table();
        statsTable.bottom().left();
        statsTable.pad(0,400,200,0);
        statsTable.add(new Label(MainGame.myLangBundle.get("length"), statstableStyle));
        statsTable.add(new Label(game.memController.getStat(0),statstableStyle));
        statsTable.row();
        statsTable.add(new Label(MainGame.myLangBundle.get("barrels"), statstableStyle));
        statsTable.add(new Label(game.memController.getStat(1),statstableStyle));
        statsTable.row();
        statsTable.add(new Label(MainGame.myLangBundle.get("time"), statstableStyle));
        statsTable.add(new Label(game.memController.getStat(2),statstableStyle));
        statsTable.row();

        // adds all the elements into a Table
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
