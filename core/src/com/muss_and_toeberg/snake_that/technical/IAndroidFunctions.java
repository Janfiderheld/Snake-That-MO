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
}
