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
    Label[] names;
    Label[] scores;
    int[] points;

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
        font = new BitmapFont(Gdx.files.internal("fonts/Comic_Sans_HUD.fnt"));
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;    // for some reason I cant use game.fontHUD
        textButtonStyle.up = skin.getDrawable("up-button");
        textButtonStyle.down = skin.getDrawable("down-button");
        textButtonStyle.checked = skin.getDrawable("checked-button");

        //create the Style for the Scoretable
        scoretableStyle = new Label.LabelStyle();
        scoretableStyle.font = font;    // for some reason I cant use game.fontHUD

        //get the scores and names for the scoretable
        points = new int[]{Integer.MAX_VALUE, 123456, 1337, 969, 0};

        names = new Label[]{new Label("Integer",scoretableStyle),
                new Label("J.Stalin",scoretableStyle),
                new Label("Joseph S.",scoretableStyle),
                new Label("Jan S.",scoretableStyle),
                new Label("Guy Fieri",scoretableStyle)};

        scores = new Label[]{new Label(String.valueOf(points[0]),scoretableStyle),
                new Label(String.valueOf(points[1]),scoretableStyle),
                new Label(String.valueOf(points[2]),scoretableStyle),
                new Label(String.valueOf(points[3]),scoretableStyle),
                new Label(String.valueOf(points[4]),scoretableStyle)};

        mainMenu_button = new TextButton("Main Menu", textButtonStyle);

        //creates the tables
        scoretable = new Table();
        menuTable = new Table();
        scoretable.bottom();
        scoretable.left();

        //start of the score table
        scoretable.pad(0,400,200,0);

        for (int i =0;i<5;i++){
            scoretable.add(new Label(String.valueOf(i+1)+".",scoretableStyle));
            scoretable.add(names[i]);
            scoretable.add(scores[i]);
            scoretable.row();
        }

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
