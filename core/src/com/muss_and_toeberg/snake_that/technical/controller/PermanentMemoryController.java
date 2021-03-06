package com.muss_and_toeberg.snake_that.technical.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.muss_and_toeberg.snake_that.screens.Settings;
import com.muss_and_toeberg.snake_that.technical.MainGame;
import static com.muss_and_toeberg.snake_that.screens.Settings.NUMBER_SETTINGS;
import java.io.IOException;
import java.io.OutputStream;

/**
 * controls everything related to saving and loading when writing was permitted
 */
public class PermanentMemoryController implements IMemoryController {
	// file handling
    private final String SAVE_LOCATION_HIGHSCORE = "Android/data/com.muss_and_toeberg.snake_that/files/highscore.dat";
    private final String SAVE_LOCATION_STATS = "Android/data/com.muss_and_toeberg.snake_that/files/stats.dat";
    private final String SAVE_LOCATION_SETTINGS = "Android/data/com.muss_and_toeberg.snake_that/files/settings.dat";

    private FileHandle highscoreFile = Gdx.files.external(SAVE_LOCATION_HIGHSCORE);
    private FileHandle statsFile = Gdx.files.external(SAVE_LOCATION_STATS);
    private FileHandle settingsFile = Gdx.files.external(SAVE_LOCATION_SETTINGS);

    // other objects and variables
    private String[] currentHighscore;
    private int[] currentStats;
    private long timeAtStart;
    private final String[] DEFAULT_SETTINGS = {"1", "1", "0", "1", "5"};

    /**
     * Constructor which gets called at the beginning
     */
    public PermanentMemoryController() {
        currentHighscore = readHighscoresFromFile();
        currentStats = readStatsFromFile();
        Settings.setSettings(readSettingsFromFile());
    }

    /**
     * writes the given String in the corresponding file
     * @param stringToWrite strings to write
     * @param whichFile file to Write in
     *                  0 for Highscore
     *                  1 for Stats
     *                  2 for Settings
     */
    private void writeStringIntoFile(String stringToWrite, int whichFile) {
        OutputStream out = null;
        switch(whichFile) {
            case 0:
                out = highscoreFile.write(false);
                break;
            case 1:
                out = statsFile.write(false);
                break;
            case 2:
                out = settingsFile.write(false);
                break;
        }
        try {
            out.write(stringToWrite.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads the Highscore from the File
     * @return String-Array filled with the Highscores
     */
    private String[] readHighscoresFromFile() {
        if(!highscoreFile.exists()) {
            createEmptyHighscore();
        }
        return highscoreFile.readString().split("\\r?\\n");
    }


    /**
     * reads the Stats from the File
     * @return int-Array filled with the Stats
     */
    private int[] readStatsFromFile(){
        if(!statsFile.exists()) {
            createEmptyStats();
        }

        String[] statsAsStrings = portFileToCurrentState(statsFile.readString().split(DELIMITER), NUMBER_STATS, "0");
        int[] tempToReturn = new int[NUMBER_STATS];
        for(int count = 0; count < NUMBER_STATS; count++) {
            tempToReturn[count] = Integer.parseInt(statsAsStrings[count]);
        }
        return tempToReturn;
    }

    /**
     * reads the settings from the file
     * @return array containing the settings
     */
    private int[] readSettingsFromFile() {
        if(!settingsFile.exists()) {
            createEmptySettings();
        }

        String[] settingsAsString = portFileToCurrentState(settingsFile.readString().split(DELIMITER), NUMBER_SETTINGS, "0");
        settingsAsString = checkIfSettingFileIsWeird(settingsAsString);
        int[] tempToReturn = new int[NUMBER_SETTINGS];
        for(int count = 0; count < NUMBER_SETTINGS; count++) {
			tempToReturn[count] = Integer.parseInt(settingsAsString[count]);
        }
        return tempToReturn;
    }

    /**
     * checks if the file is weird and still unported to the newest version
     * @param settingsAsString settings String to check
     * @return the (definitly) ported version
     */
    private String[] checkIfSettingFileIsWeird(String[] settingsAsString) {
        try {
            Integer.parseInt(settingsAsString[0]);
        } catch (NumberFormatException nfEx) {
            return DEFAULT_SETTINGS;
        }
        return settingsAsString;
    }

    /**
     * returns the Names or the scores of the Highscore
     * @param getPoints 0 for names - 1 for points
     * @return String Array with the names or points
     */
	@Override
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
	@Override
    public void addScoreToHighscore(String playerName, int playerScore, int placement) {
        // restructures the older placements
        for (int count = NUMBER_PLAYERS - 1; count > placement; count--){
            currentHighscore[count] = currentHighscore[count - 1];
        }

        currentHighscore[placement] = playerName + DELIMITER + playerScore;
        writeStringIntoFile(createHighscoreString(), 0);
    }

    /**
     * checks if a newly made Score belongs on the Leaderbord
     * @return placement of the new Score
     * @param score score of the run
     */
	@Override
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
     * saves the settings in the file
     */
	@Override
    public void saveSettings() {
        writeStringIntoFile(createStringToSave(false), 2);
    }

    /**
     * creates an empty highscore at the beginning of the game
     */
    @Override
    public void createEmptyHighscore() {
        String playerName = MainGame.myLangBundle.get("playName");
        currentHighscore = new String[NUMBER_PLAYERS];
        for(int count = 0; count < NUMBER_PLAYERS; count++) {
            currentHighscore[count] = playerName + DELIMITER + 0;
        }
        writeStringIntoFile(createHighscoreString(), 0);
    }

    /**
     * Returns a single stat from the array
     * @param index which stat should be returned
     *              0 for total length
     *              1 for barrels hit
     *              2 for games played
     *              3 for longest run in seconds
     * @return the choosen stat
     */
	@Override
    public int getStat(int index){
        return currentStats[index];
    }

    /**
     * increase barrel-hit counter
     */
	@Override
    public void addBarrel() {
        currentStats[INDEX_BARRELS]++;
    }

    /**
     * increases the games counter
     */
	@Override
    public void addPlayedGame() {
        currentStats[INDEX_GAMES_NO]++;
    }

    /**
     * @return current char used for delimiting
     */
	@Override
    public String getDelimiter() {
        return DELIMITER;
    }

    /**
     * increase total length counter
     */
	@Override
    public void addLength(int lengthToAdd) {
        currentStats[INDEX_LENGTH] += lengthToAdd;
    }

    /**
     * starts the timer
     */
	@Override
    public void startTimer() {
        timeAtStart = System.currentTimeMillis();
    }

    /**
     * stops the timer and saves the time when its more than before
     */
	@Override
    public void stopTimer() {
        int timeForGame = (int) (System.currentTimeMillis() - timeAtStart) / 1000;
        if(timeForGame > currentStats[INDEX_LONG_RUN]) {
            currentStats[INDEX_LONG_RUN] = timeForGame;
        }
    }

    /**
     * saves all the Stats to the File
     */
	@Override
    public void saveStats(){
        writeStringIntoFile(createStringToSave(true), 1);
    }

    /**
     * creates a string which can be saved in a highscoreFile from the array
     * @return string to save
     */
    private String createHighscoreString() {
        String highscoresToWrite = "";
        for(int count = 0; count < NUMBER_PLAYERS; count++) {
            highscoresToWrite += currentHighscore[count] + "\n";
        }
        return highscoresToWrite;
    }

    /**
     * create an Empty Stat File
     */
    private void createEmptyStats(){
        currentStats = new int[NUMBER_STATS];
        for(int count = 0; count < NUMBER_STATS; count++) {
            currentStats[count] = 0;
        }
        writeStringIntoFile(createStringToSave(true), 1);
    }

    /**
     * creates a string which can be saved in a file
     * @param shouldCreateStats true if a stats string should be created
     * @return string to save
     */
    private String createStringToSave(boolean shouldCreateStats){
        String tempToReturn = "";
        int maxAmount = shouldCreateStats ? NUMBER_STATS : NUMBER_SETTINGS;
        for(int count = 0; count < maxAmount; count++) {
            if(shouldCreateStats) {
                tempToReturn += currentStats[count] + DELIMITER;
            } else {
                tempToReturn += Settings.getSettings()[count] + DELIMITER;
            }
        }
        return tempToReturn;
    }

    /**
     * creates empty settings
     */
    private void createEmptySettings() {
        for(int count = 0; count < NUMBER_SETTINGS; count++) {
            Settings.getSettings()[count] = 1;
        }
        writeStringIntoFile(createStringToSave(false), 2);
    }

    /**
     * if the file on the current device has a lesser amount of stats or
     * settings then the current gameVersion,
     * the string array gets "updated" on the latest version
     * @param arrayToPort array which could be updated
     * @param maxNumber number of entries the array should have after this
     * @param strToFillWith string to fill the new places in the array with
     * @return the updated array
     */
    private String[] portFileToCurrentState(String[] arrayToPort, int maxNumber, String strToFillWith) {
        if(arrayToPort.length >= maxNumber) {
            return arrayToPort;
        }
        String[] tempToReturn = new String[maxNumber];
        for(int count = 0; count < arrayToPort.length; count++) {
            tempToReturn[count] = arrayToPort[count];
        }
        for(int count = arrayToPort.length; count < maxNumber; count++) {
            tempToReturn[count] = strToFillWith;
        }
        return tempToReturn;
    }
}