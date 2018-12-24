package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.muss_and_toeberg.snake_that.technical.Menu;

public class Statistics implements Screen {
    private MainGame game;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public Statistics(final MainGame game) {
        this.game = game;
        MainGame.currentMenu = Menu.Statistics;
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
        game.fontHUD.draw(game.batch, "Work in Progress", 200, 800);
        game.batch.end();
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
