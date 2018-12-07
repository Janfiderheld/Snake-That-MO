package com.muss_and_toeberg.snake_that.obstacles_and_elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * filled and unfilled hearts to represent the remaining lives
 */
public class Heart {
	// local Variables
	private boolean isFilled;
	private Texture currentImg; 

	// textures for the hearts
	public static Texture filledImg = new Texture(Gdx.files.internal("Heart_filled.png"));
	public static Texture unfilledImg = new Texture(Gdx.files.internal("Heart_unfilled.png"));

    /**
     * fills the heart during its creation
     */
	public Heart() {
		fillTheHeart();
	}

    /**
     * @return true if heart is filled
     */
	public boolean isFilled() {
		return isFilled;
	}

    /**
     * @return Texture of the heart (filled or unfilled)
     */
	public Texture getImage() {
		return currentImg;
	}

    /**
     * empties the heart
     */
	public void unfillTheHeart() {
		currentImg = Heart.unfilledImg;
		isFilled = false;
	}

    /**
     * fills the heart
     */
	public void fillTheHeart() {
		currentImg = Heart.filledImg;
		isFilled = true;
	}		
}