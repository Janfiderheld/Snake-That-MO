package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.muss_and_toeberg.snake_that.technical.Menu;

/**
 * Main menu of the game
 */
public class MainMenu implements Screen {
    private MainGame game;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public MainMenu (final MainGame game){
        this.game = game;
        MainGame.currentMenu = Menu.MainMenu;
    }

    /**
     * renders the screen (= fills it with everything)
     * gets called in a constant loop
     * @param delta time since the last render
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.isTouched()){
            game.setScreen(new FirstLevel(game));
            dispose();
        }
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
