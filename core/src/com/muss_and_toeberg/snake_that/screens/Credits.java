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
 * Screen which contains the Credits
 * <a href="https://tldrlegal.com/license/apache-license-2.0-(apache-2.0)">Apache 2.0-License</a>
 * @author Niclas Muss
 */
public class Credits implements Screen {
    // objects & graphical elements
    private MainGame game;
    private Stage stage;

    // variables
    private float headerStartX;
    private float headerStartY;

    /**
     * Constructor which is used to create all objects that only need to be
     * created once
     * method head based on the top answer
     * <a href="https://stackoverflow.com/questions/25837013/switching-between-screens-libgdx">
     *     here</a>
     * @param game game object which allows screen changing
     */
    public Credits(final MainGame game) {
        this.game = game;
        MainGame.currentMenu = Menu.Credits;

        headerStartX = (MainGame.CAMERA_WIDTH / 2) - 200;
        headerStartY = MainGame.CAMERA_HEIGHT - 25;

        // creates the Stage
        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);

        // creates the label-Styles
        Label.LabelStyle lblStyleCredits = new Label.LabelStyle();
        lblStyleCredits.font = game.fontCredits;
        lblStyleCredits.fontColor = Color.WHITE;

        Label.LabelStyle lblStyleDescription = new Label.LabelStyle();
        lblStyleDescription.font = game.fontDescription;
        lblStyleDescription.fontColor = Color.WHITE;

        // creates the Labels
        Label lblDescription = new Label(MainGame.myLangBundle.get("desc"),
                lblStyleDescription);
        Label lblLibGDX = new Label(MainGame.myLangBundle.format("libGDX",
                "(apache.org/licenses/LICENSE-2.0)"),
                lblStyleDescription);
        Label lblSupport = new Label(
                MainGame.myLangBundle.format("support",
                "M.Sc. Andreas Schmelter",
                "Prof. Dr. rer. nat. Stefan Heiss"), lblStyleCredits);
        Label lblIdea = new Label(MainGame.myLangBundle.format("idea",
                "Jan-Philipp Töberg"), lblStyleCredits);
        Label lblProgramming = new Label(
                MainGame.myLangBundle.format("programming",
                "Niclas Muss", "Jan-Philipp Töberg"), lblStyleCredits);
        Label lblSoundAndMusic = new Label(
                MainGame.myLangBundle.format("sound&Music",
                "Eric Matyas (soundimage.org)"), lblStyleCredits);
        Label lblBackground = new Label(
                MainGame.myLangBundle.format("background",
                "Victoria Pontie"), lblStyleCredits);
        Label lblFlags = new Label(MainGame.myLangBundle.format("flags",
                "Gang of the Coconuts (free-country-flags.com)"),
                lblStyleCredits);
        Label lblRestTextures = new Label(
                MainGame.myLangBundle.format("restTextures",
                "Niclas Muss", "Jan-Philipp Töberg"), lblStyleCredits);

        // creates the button
        TextButton btnBackMainMenu = new TextButton(
                MainGame.myLangBundle.get("backToMM"),
                MainGame.btnStyleMainMenuFont);
        btnBackMainMenu.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.goBackToMainMenu(Credits.this);
            }
        });

        // adds all the elements into a Table
        Table menuTable = new Table();
        menuTable.bottom().left();
        menuTable.add(lblDescription).
                space(10,10,10,10).
                align(Align.left).row();
        menuTable.add(lblLibGDX).space(10,10,50,10).
                align(Align.left).row();
        menuTable.add(lblSupport).space(10,10,10,10).
                align(Align.left).row();
        menuTable.add(lblIdea).space(10,10,10,10).
                align(Align.left).row();
        menuTable.add(lblProgramming).
                space(10,10,10,10).
                align(Align.left).row();
        menuTable.add(lblSoundAndMusic).
                space(10,10,10,10).
                align(Align.left).row();
        menuTable.add(lblBackground).space(10,10,10,10).
                align(Align.left).row();
        menuTable.add(lblFlags).space(10, 10, 10, 10).
                align(Align.left).row();
        menuTable.add(lblRestTextures).
                space(10,10,10,10).
                align(Align.left).row();
        menuTable.add(btnBackMainMenu).width(MainMenu.BACK_MM_BUTTON_WIDTH).
                align(Align.bottomLeft);

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
        game.fontHUD.draw(game.batch, MainGame.myLangBundle.get("credits"),
                headerStartX, headerStartY);
        game.batch.end();
        stage.draw();
        game.camera.update();
        game.checkBackAndCloseScreen(this);
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
