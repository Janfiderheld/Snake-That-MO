package com.muss_and_toeberg.snake_that.technical;

/**
 * contains all android specific things (like Toast) which can not be called from within libGDX
 */
public interface IAndroidFunctions {

	/**
	 * create and show an Android-Toast to the player
	 * @param messageToShow message to display
	 * @param shortToast should that be a short or a long toast
	 */
	void createAndShowToast(final String messageToShow, final boolean shortToast);

	/**
	 * ask the player for write permission on the device
	 * (needed for saving highscores & settings)
	 */
	void askForWritePermission();

	/**
	 * check if the user already gave his permission
	 * @return true if permission was given
	 */
	boolean checkWritePermission();

	/**
	 * opens a given url via a browser intent
	 * @param urlToOpen self-explanatory
	 */
	void openUrlViaIntent(String urlToOpen);
}
