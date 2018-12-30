package com.muss_and_toeberg.snake_that.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.muss_and_toeberg.snake_that.technical.Menu;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Highscore Menu Screen
 */
public class Highscores implements Screen {
    private MainGame game;

    //Buttons
    TextButton btnBackMainMenu;

    //Graphical Elements
    protected Stage stage;
    Label.LabelStyle scoretableStyle;
    Table scoretable;
    Table menuTable;
    Label[] names;
    Label[] scores;

    //Reading and writing files
    FileHandle file;
    OutputStream out;

    String[] lines;

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

        //create the Style for the Scoretable
        scoretableStyle = new Label.LabelStyle();
        scoretableStyle.font = game.fontHUD;

        names = new Label[5];
        scores = new Label[5];

        lines = readHighscores();
        //show the Highscores
        for (int j=0;j<5;j++) {
            String[] temp = lines[j].split(",");
            names[j] = new Label(temp[0], scoretableStyle);
            scores[j] = new Label(temp[1], scoretableStyle);
        }

        //write the highscores file

        btnBackMainMenu = new TextButton(MainGame.myLangBundle.get("backToMM"), MainGame.btnStyleMainMenuFont);

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
        menuTable.add(btnBackMainMenu);

        //add the Tables to the Stage
        stage.addActor(scoretable);
        stage.addActor(menuTable);
        btnBackMainMenu.addListener(new ChangeListener() {
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

    /**
     * @return String Array Highscores and the corresponding Playernames
     */
    private static String[] readHighscores(){
        FileHandle file = Gdx.files.external("Android/data/com.muss_and_toeberg.snake_that/files/highscore.dat");

        //read the highscores file
        if (!file.exists()){
            //write placeholder Highscores
            OutputStream out = file.write(false);
            byte[] bytes;
            bytes = "Place1,10000\nPlace2,1000\nPlace3,100\nPlace4,10\nPlace5,1".getBytes();
            try {
                out.write(bytes);
                out.close();
            } catch (IOException e) {
                // ¯\_(ツ)_/¯
            }
        }

        //read the Highscores
        String[] lines = file.readString().split("\\r?\\n");
        return lines;
    }

    /**
     * @return placement of the new Score
     * checks if a newly made Score belongs on the Leaderbord
     * @param score score of the run
     */
    public static int checkForHigscore(int score){
        String[] lines = readHighscores();
        for (int i=0;i<5;i++){
            String[] line = lines[i].split(",");
            if (score > Integer.parseInt((line[1]))){
                //insert score
                return i;
            }
        }
        return -1;
    }

    /**
     * add a new Score to the Highscores
     * @param name name of the Player
     * @param score score of the run
     * @param place placement in the Leaderboard
     */
    public static void writeHighscores(String name, String score,int place){
        FileHandle file = Gdx.files.external("Android/data/com.muss_and_toeberg.snake_that/files/highscore.dat");
        String[]lines = readHighscores();
        //restructure scores
        for (int i=4;i>place;i--){
            lines[i]=lines[i-1];

        }
        //add the new Highscore
        lines[place] =name+","+score;
        String str = lines[0]+"\n"+lines[1]+"\n"+lines[2]+"\n"+lines[3]+"\n"+lines[4];
        OutputStream out = file.write(false);

        byte[] bytes;
        bytes = str.getBytes();
        try {
            out.write(bytes);
            out.close();
        } catch (IOException e) {
            // ¯\_(ツ)_/¯
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
