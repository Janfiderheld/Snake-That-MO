package com.muss_and_toeberg.snake_that.technical.controller;

/**
 * gives methods for everything related to saving and loading
 */
public interface IMemoryController {
    // constant values
    int NUMBER_PLAYERS = 5;
    int NUMBER_STATS = 4;
    String DELIMITER = ",";

    // constant indexes for usage of stats
    int INDEX_LENGTH = 0;
    int INDEX_BARRELS = 1;
    int INDEX_GAMES_NO = 2;
    int INDEX_LONG_RUN = 3;

    /**
     * reads the settings from the file
     * @return array containing the settings
     */
    int[] readSettingsFromFile();

    /**
     * returns the Names or the scores of the Highscore
     * @param getPoints 0 for names - 1 for points
     * @return String Array with the names or points
     */
    String[] getHighscoreNamesOrPoints(int getPoints);

    /**
     * adds the given Score to the 5 Highscores
     * @param playerName name of the player who scored
     * @param playerScore score to be added
     * @param placement position at which it should be added
     */
     void addScoreToHighscore(String playerName, int playerScore, int placement);

    /**
     * checks if a newly made Score belongs on the Leaderbord
     * @return placement of the new Score
     * @param score score of the run
     */
     int checkHighscorePlacement(int score);

    /**
     * saves the settings in the file
     */
    void saveSettings();

    /**
     * creates an empty highscore at the beginning of the game
     */
     void createEmptyHighscore();

    /**
     * Returns a single stat from the array
     * @param index which stat should be returned
     *              0 for total length
     *              1 for barrels hit
     *              2 for games played
     *              3 for longest run in seconds
     * @return the choosen stat
     */
    int getStat(int index);

    /**
     * increase barrel-hit counter
     */
    void addBarrel();

    /**
     * increases the games counter
     */
     void addPlayedGame();

    /**
     * @return current char used for delimiting
     */
    String getDelimiter();

    /**
     * increase total length counter
     */
    void addLength(int lengthToAdd);

    /**
     * starts the timer
     */
    void startTimer();

    /**
     * stops the timer and saves the time when its more than before
     */
    void stopTimer();

    /**
     * saves all the Stats to the File
     */
    void saveStats();
}