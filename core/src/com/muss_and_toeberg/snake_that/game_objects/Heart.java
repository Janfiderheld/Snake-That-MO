package com.muss_and_toeberg.snake_that.game_objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * filled and unfilled hearts to represent the remaining lives
 * @author Jan-Philipp Töberg
 */
public class Heart {
	// local Variables
	private boolean isFilled;
	private Texture currentImg; 

	// textures for the hearts
	private Texture filledImg = new Texture(Gdx.files.internal("textures/HeartFilled.png"));
    private Texture unfilledImg = new Texture(Gdx.files.internal("textures/HeartUnfilled.png"));

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
	public void emptyTheHeart() {
		currentImg = unfilledImg;
		isFilled = false;
	}

    /**
     * fills the heart
     */
	public void fillTheHeart() {
		currentImg = filledImg;
		isFilled = true;
	}		
}