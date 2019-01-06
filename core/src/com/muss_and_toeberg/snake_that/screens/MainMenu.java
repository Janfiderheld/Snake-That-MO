package com.muss_and_toeberg.snake_that.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.muss_and_toeberg.snake_that.screens.levels.Level01;
import com.muss_and_toeberg.snake_that.technical.MainGame;
import com.muss_and_toeberg.snake_that.technical.Menu;

/**
 * Screen which contains the Main menu
 * @author Niclas Muss
 */
public class MainMenu implements Screen {
    // constant values
    private final int BUTTONS_PER_ROW = 3;
    public final static int BACK_MM_BUTTON_WIDTH = 550;

    // objects & graphical elements
    private MainGame game;
    private Stage stage;

    /**
     * Constructor which is used to create all objects that only need to
     * be created once
     * @param game game object which allows screen changing
     */
    public MainMenu (final MainGame game){
        this.game = game;
        MainGame.currentMenu = Menu.MainMenu;
        float buttonWidth = MainGame.CAMERA_WIDTH / BUTTONS_PER_ROW;

        // creates the Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // create the Buttons
        TextButton btnStartGame = new TextButton(
                MainGame.myLangBundle.get("startGame"),
                MainGame.btnStyleMainMenuFont);
        TextButton btnHighscore = new TextButton(
                MainGame.myLangBundle.get("score"),
                MainGame.btnStyleMainMenuFont);
        TextButton btnStatistics = new TextButton(
                MainGame.myLangBundle.get("stats"),
                MainGame.btnStyleMainMenuFont);
        TextButton btnSettings = new TextButton(
                MainGame.myLangBundle.get("settings"),
                MainGame.btnStyleMainMenuFont);
        TextButton btnCredits = new TextButton(
                MainGame.myLangBundle.get("credits"),
                MainGame.btnStyleMainMenuFont);
        TextButton btnQuitGame = new TextButton(
                MainGame.myLangBundle.get("quit"),
                MainGame.btnStyleMainMenuFont);

        // add the action listeners to the Buttons
        btnStartGame.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new Level01(game));
                dispose();
            }
        });

        btnHighscore.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new Highscores(game));
                dispose();
            }
        });

        btnStatistics.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Statistics(game));
                dispose();
            }
        });

        btnSettings.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new Settings(game));
                dispose();
            }
        });

        btnCredits.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new Credits(game));
                dispose();
            }
        });

        btnQuitGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                dispose();
            }
        });

        // adds all the elements into a Table
        Table menuTable = new Table();
        menuTable.bottom().left();
        menuTable.add(btnStartGame).width(buttonWidth);
        menuTable.add(btnHighscore).width(buttonWidth);
        menuTable.add(btnStatistics).width(buttonWidth).row();
        menuTable.add(btnSettings).width(buttonWidth);
        menuTable.add(btnCredits).width(buttonWidth);
        menuTable.add(btnQuitGame).width(buttonWidth);

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
        game.fontHUD.draw(game.batch, MainGame.myLangBundle.get("headerMM"),
                100, 800);
        game.batch.end();
        stage.draw();
        game.camera.update();
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
