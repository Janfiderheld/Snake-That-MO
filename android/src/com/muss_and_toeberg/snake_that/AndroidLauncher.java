package com.muss_and_toeberg.snake_that;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.muss_and_toeberg.snake_that.technical.IAndroidFunctions;
import com.muss_and_toeberg.snake_that.technical.MainGame;

import java.util.List;

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

		if(!checkWritePermission()) {
			askForWritePermission();
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

	/**
	 * ask the player for write permission on the device
	 * (needed for saving highscores & settings)
	 */
	@Override
	public void askForWritePermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
		}
	}

	/**
	 * check if the user already gave his permission
	 * @return true if permission was given
	 */
	@Override
	public boolean checkWritePermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
		} else {
			return false;
		}
	}

	/**
	 * opens a given url via a browser intent, if the device has an app for opening urls
	 * @param urlToOpen self-explanatory
	 */
	@Override
	public void openUrlViaIntent(String urlToOpen) {
		PackageManager packageManager = getPackageManager();
		Uri urlForIntent = Uri.parse(urlToOpen);
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, urlForIntent);
		List<ResolveInfo> allAppsForUrl= packageManager.queryIntentActivities(browserIntent, 0);
		if(allAppsForUrl.size() > 0) {
			startActivity(browserIntent);
		} else {
			createAndShowToast(MainGame.myLangBundle.get("noBrowser"), true);
		}
	}
}
