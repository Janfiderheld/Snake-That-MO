package com.muss_and_toeberg.snake_that.technical;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.muss_and_toeberg.snake_that.screens.MainGame;

import java.io.IOException;
import java.io.OutputStream;

/**
 * controls everything related to saving and loading
 */
public class MemoryController {
    // constant values
    public final int NUMBER_PLAYERS = 5;
    private final String SAVE_LOCATION_HIGHSCORE = "Android/data/com.muss_and_toeberg.snake_that/files/highscore.dat";
    private final String SAVE_LOCATION_STATS = "Android/data/com.muss_and_toeberg.snake_that/files/stats.dat";
    private final String DELIMITER = ",";

    // objects
    private FileHandle file = Gdx.files.external(SAVE_LOCATION_HIGHSCORE);
    private String[] currentHighscore;

    /**
     * Constructor which gets called at the beginning
     */
    public MemoryController() {
        currentHighscore = readHighscoresFromFile();
    }

    /**
     * reads the Highscore from the File
     * @return String-Array filled with the Highscores
     */
    public String[] readHighscoresFromFile() {
        if(!file.exists()) {
            createEmptyHighscore();
        }
        return file.readString().split("\\r?\\n");
    }

    /**
     * writes the current Highscore in the file
     * @param concatedHighscore Highscore to write
     */
    private void writeHighscoreInFile(String concatedHighscore) {
        OutputStream out = file.write(false);
        byte[] bytes = concatedHighscore.getBytes();
        try {
            out.write(bytes);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * returns the Names or the scores of the Highscore
     * @param getPoints 0 for names - 1 for points
     * @return String Array with the names or points
     */
    public String[] getHighscoreNamesOrPoints(int getPoints) {
        String[] tempToReturn = new String[NUMBER_PLAYERS];
        for(int count = 0; count < NUMBER_PLAYERS; count++) {
            tempToReturn[count] = currentHighscore[count].split(DELIMITER)[getPoints];
        }
        return tempToReturn;
    }

    /**
     * adds the given Score to the 5 Highscores
     * @param playerName name of the player who scored
     * @param playerScore score to be added
     * @param placement position at which it should be added
     */
    public void addScoreToHighscore(String playerName, int playerScore, int placement) {
        // restructures the older placements
        for (int i = NUMBER_PLAYERS - 1; i > placement; i--){
            currentHighscore[i] = currentHighscore[i - 1];
        }

        currentHighscore[placement] = playerName + DELIMITER + playerScore;
        writeHighscoreInFile(createStringToSave());
    }

    /**
     * creates a string which can be saved in a file from the array
     * @return string to save
     */
    private String createStringToSave() {
        String highscoresToWrite = "";
        for(int count = 0; count < NUMBER_PLAYERS; count++) {
            highscoresToWrite += currentHighscore[count] + "\n";
        }
        return highscoresToWrite;
    }

    /**
     * checks if a newly made Score belongs on the Leaderbord
     * @return placement of the new Score
     * @param score score of the run
     */
    public int checkHighscorePlacement(int score) {
        for(int count = 0; count < NUMBER_PLAYERS; count++) {
            int points = Integer.parseInt(currentHighscore[count].split(DELIMITER)[1]);
            if(score > points) {
                return count;
            }
        }
        return -1;
    }

    /**
     * creates an empty highscore at the beginning of the game
     */
    public void createEmptyHighscore() {
        String playerName = MainGame.myLangBundle.get("playName");
        for(int count = 0; count < NUMBER_PLAYERS; count++) {
            currentHighscore[count] = playerName + DELIMITER + 0;
        }
        writeHighscoreInFile(createStringToSave());
    }
}