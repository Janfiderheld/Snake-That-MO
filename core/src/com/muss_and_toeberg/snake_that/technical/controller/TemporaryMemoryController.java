package com.muss_and_toeberg.snake_that.technical.controller;

import com.muss_and_toeberg.snake_that.screens.Settings;
import com.muss_and_toeberg.snake_that.technical.MainGame;

/**
 * controls everything related to saving and loading when writing was not permitted
 */
public class TemporaryMemoryController implements IMemoryController {
    // other objects and variables
    private String[] currentHighscore;
    private int[] currentStats;
    private long timeAtStart;
    private final int[] DEFAULT_SETTINGS = {1, 1, 0, 1, 5};

    /**
     * Constructor which gets called at the beginning
     */
    public TemporaryMemoryController() {
        createEmptyHighscore();
		createEmptyStats();
        Settings.setSettings(DEFAULT_SETTINGS);
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
        return;
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
        return;
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
    }

    /**
     * create empty stats at the beginning of the game
     */
    private void createEmptyStats(){
		currentStats = new int[NUMBER_STATS];
        for(int count = 0; count < NUMBER_STATS; count++) {
			currentStats[count] = 0;
        }
    }
}