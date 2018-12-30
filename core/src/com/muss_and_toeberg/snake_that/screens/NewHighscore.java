package com.muss_and_toeberg.snake_that.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.muss_and_toeberg.snake_that.technical.Menu;

public class NewHighscore implements Screen {
    private MainGame game;

    //Buttons
    TextButton submitBtn;
    TextField textfield;
    TextField.TextFieldStyle textfieldstyle;

    //Graphical Elements
    protected Stage stage;
    Label.LabelStyle scoretableStyle;
    Label label;

    public NewHighscore(final MainGame game, final int placement, final int score){
        this.game = game;
        MainGame.currentMenu = Menu.Highscore;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        scoretableStyle = new Label.LabelStyle();
        scoretableStyle.font = game.fontHUD;

        //label
        label = new Label("New Highscore\nyou Placed "+(placement+1)+".",scoretableStyle);
        label.setPosition(1920/2-400,1080/2+200);

        //textfield
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttonsControl.pack"));
        skin.addRegions(buttonAtlas);
        textfieldstyle = new TextField.TextFieldStyle();
        textfieldstyle.font = new BitmapFont(Gdx.files.internal("fonts/Comic_Sans_HUD.fnt"));
        textfieldstyle.fontColor = new Color(0,0,0,1);
        textfieldstyle.background = skin.getDrawable("checked-button"); //TODO: Textfield drawable

        textfield = new TextField("",textfieldstyle);
        textfield.setMessageText("Your Name");
        textfield.setMaxLength(10);
        textfield.setPosition(1920/2-400,1080/2-100);
        textfield.setSize(1000,200);

        //submit Button
        submitBtn = new TextButton(MainGame.myLangBundle.get("submit"), MainGame.btnStyleMainMenuFont);
        submitBtn.setPosition(1920/2-100,200);
        submitBtn.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                Highscores.writeHighscores(textfield.getText(),String.valueOf(score),placement);
                game.setScreen(new Highscores(game));
                dispose();
            }
        });

        //add actors to stage
        stage.addActor(textfield);
        stage.addActor(label);
        stage.addActor(submitBtn);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
        game.camera.update();
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
