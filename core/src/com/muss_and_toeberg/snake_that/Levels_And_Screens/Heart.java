package com.muss_and_toeberg.snake_that.Levels_And_Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

// filled and unfilled hearts to represent the remaining lives
public class Heart {
	// Local Variables
	private boolean isFilled;
	private Texture currentImg; 

	public static Texture filledImg = new Texture(Gdx.files.internal("Heart_filled.png"));
	public static Texture unfilledImg = new Texture(Gdx.files.internal("Heart_unfilled.png"));
	
	// Costructor
	public Heart() {
		fillTheHeart();
	}
	
	// returns if the heart is currently filled
	public boolean isFilled() {
		return isFilled;
	}
	
	// returns the Texture to draw
	public Texture getImage() {
		return currentImg;
	}
	
	// empties the heart
	public void unfillTheHeart() {
		currentImg = Heart.unfilledImg;
		isFilled = false;
	}
	
	// fills the heart
	public void fillTheHeart() {
		currentImg = Heart.filledImg;
		isFilled = true;
	}		
}