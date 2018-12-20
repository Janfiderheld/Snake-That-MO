package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.muss_and_toeberg.snake_that.technical.Menu;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Main menu of the game
 */
public class MainMenu implements Screen {
    private MainGame game;

    //Buttons
    TextButton start_button;
    TextButton higscore_button;

    //Graphical Elements
    protected Stage stage;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    TextureAtlas buttonAtlas;
    Skin skin;
    Table menuTable;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public MainMenu (final MainGame game){
        this.game = game;
        MainGame.currentMenu = Menu.MainMenu;

        //creates the Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //creates the Style for the Buttons
        font = new BitmapFont(Gdx.files.internal("fonts/Comic_Sans.fnt"));
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;   // for some reason I cant use game.font
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");

        //create the Buttons
        start_button = new TextButton("Start Game", textButtonStyle);
        higscore_button = new TextButton("Highscores",textButtonStyle);

        //adds all the Elements into a Table
        menuTable = new Table();
        menuTable.bottom();
        menuTable.left();
        menuTable.add(start_button);              // Row 0, column 0
        menuTable.add(higscore_button);           // Row 0, column 1

        //add the Table to the Stage
        stage.addActor(menuTable);

        //add the action listeners to the Buttons
        start_button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new FirstLevel(game));
                dispose();
            }
        });

        higscore_button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new Highscores(game));
                dispose();
            }
        });
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
        //game.batch.draw(new Texture("texturesToKeep/backgroundGrid.png"), 0, 0);
        game.font.draw(game.batch, "Welcome to Snake_That!!! ", 200, 800);
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
