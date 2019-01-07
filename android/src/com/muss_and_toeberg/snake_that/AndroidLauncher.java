package com.muss_and_toeberg.snake_that;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.muss_and_toeberg.snake_that.technical.MainGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (this.checkSelfPermission(
			        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

				this.requestPermissions(new String[]{
				        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                //TODO: denying Access on first launch Crashes the App
			}
		}

		AndroidApplicationConfiguration config =
                new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		initialize(new MainGame(), config);
	}
}
