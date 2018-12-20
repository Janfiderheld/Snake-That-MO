package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.muss_and_toeberg.snake_that.technical.Menu;

/**
 * Highscore Menu Screen
 */
public class Highscores implements Screen {
    private MainGame game;

    //Buttons
    TextButton mainMenu_button;
    TextButtonStyle textButtonStyle;

    //Graphical Elements
    protected Stage stage;
    BitmapFont font;
    TextureAtlas buttonAtlas;
    Skin skin;
    Label.LabelStyle scoretableStyle;
    Table scoretable;
    Table menuTable;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public Highscores(final MainGame game){
        this.game = game;
        MainGame.currentMenu = Menu.Highscore;

        //creates the Stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        //creates the Style for the Buttons
        font = new BitmapFont(Gdx.files.internal("fonts/Comic_Sans.fnt"));
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;    // for some reason I cant use game.font
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");

        //create the Style for the Scoretable
        scoretableStyle = new Label.LabelStyle();
        scoretableStyle.font = font;    // for some reason I cant use game.font

        //creates the Labels for the Scoretable
        mainMenu_button = new TextButton("Main Menu", textButtonStyle);
        Label label1 = new Label("1.",scoretableStyle);
        Label label2 = new Label("2.",scoretableStyle);
        Label label3 = new Label("3.",scoretableStyle);
        Label label4 = new Label("4.",scoretableStyle);
        Label label5 = new Label("5.",scoretableStyle);

        Label score1 = new Label("99999999",scoretableStyle);
        Label score2 = new Label("really good score.",scoretableStyle);
        Label score3 = new Label("80085",scoretableStyle);
        Label score4 = new Label("1337",scoretableStyle);
        Label score5 = new Label("-132",scoretableStyle);

        //creates the tables
        scoretable = new Table();
        menuTable = new Table();
        scoretable.bottom();
        scoretable.left();

        //start of the score table
        scoretable.pad(0,400,200,0);
        //first place
        scoretable.add(label1);
        scoretable.add(score1);
        scoretable.row();
        //second place
        scoretable.add(label2);
        scoretable.add(score2);
        scoretable.row();
        //third place
        scoretable.add(label3);
        scoretable.add(score3);
        scoretable.row();
        //fourth place
        scoretable.add(label4);
        scoretable.add(score4);
        scoretable.row();
        //fifth place
        scoretable.add(label5);
        scoretable.add(score5);
        scoretable.row();

        //start of the Menutable
        menuTable.bottom();
        menuTable.left();
        menuTable.add(mainMenu_button);

        //add the Tables to the Stage
        stage.addActor(scoretable);
        stage.addActor(menuTable);
        mainMenu_button.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
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

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
