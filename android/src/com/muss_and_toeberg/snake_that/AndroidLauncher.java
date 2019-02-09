package com.muss_and_toeberg.snake_that;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.muss_and_toeberg.snake_that.technical.IAndroidFunctions;
import com.muss_and_toeberg.snake_that.technical.MainGame;

/**
 * android specific class which is opened before the game goes into libGDX
 */
public class AndroidLauncher extends AndroidApplication implements IAndroidFunctions {
	private final AndroidLauncher context = this;

	/**
	 * gets called when the App gets started
	 * @param savedInstanceState bundle which contains the saved instances
	 */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                //TODO: denying Access on first launch Crashes the App
			}
		}

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new MainGame(this), config);
	}

	/**
	 * create and show an Android-Toast to the player
	 * @param messageToShow message to display
	 * @param shortToast should that be a short or a long toast
	 */
	@Override
	public void createAndShowToast(final String messageToShow, final boolean shortToast) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				int length = shortToast ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG;
				Toast.makeText(context, messageToShow, length).show();
			}
		});
	}
}
