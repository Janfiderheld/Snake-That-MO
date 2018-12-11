package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class MainMenu implements Screen {

    private MainGame game;

    public MainMenu (final MainGame game){
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {



        game.batch.begin();
        game.batch.draw(game.texture,0,0,256,256);
        game.batch.end();

        if (Gdx.input.isTouched()){
            game.setScreen(new FirstLevel(game));
            dispose();
        }
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
