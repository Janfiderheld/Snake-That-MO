package com.muss_and_toeberg.snake_that.game_objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

/**
 * Object which the player can collect to gain points or lives
 */
public class Coin {
    // Texture for the image and the hitBox
    private Texture image;
    private Circle hitBox = new Circle();

    // Constant Values
    private final int RADIUS = 32;
    private final int SIZE = RADIUS * 2;
    private final int POINT_PROBABILITY = 75;
    private final int SCORE_PER_COIN = 50;
    private final int RANDOM_POINTS_MAX = 100;

    // the Position on the coordinate system
    private int xPos = RADIUS;
    private int yPos = RADIUS;

    // local variables
    private Random rndGenerator = new Random();
    private int randomCoinValue;

    /**
     * sets a random Texture, sets the coin-radius and refreshes the hitBox-Position
     */
    public Coin() {
        setRandomTexture(POINT_PROBABILITY);
        hitBox.radius = RADIUS;
        refreshHitBox();
    }

    /**
     * @return currently used texture
     */
    public Texture getTexture() {
        return image;
    }

    /**
     * @return hitBox as a Circle-Object
     */
    public Circle getHitBox() {
        return hitBox;
    }

    /**
     * @return position of the coin on the x-axis
     */
    public int getXPosition() {
        return xPos - RADIUS;
    }

    /**
     * @return position of the coin on the y-axis
     */
    public int getYPosition() {
        return yPos - RADIUS;
    }

    /**
     * changes the x-Position and refreshes the HitBox
     * @param xPos - new Position on the x-axis
     */
    public void setXPosition(int xPos) {
        this.xPos = xPos;
        refreshHitBox();
    }

    /**
     * changes the y-Position and refreshes the HitBox
     * @param yPos - new Position on the y-axis
     */
    public void setYPosition(int yPos) {
        this.yPos = yPos;
        refreshHitBox();
    }

    /**
     * @return constant point-value the player gets for a NFC coin
     */
    public int getNotRandomPoints() {
        return SCORE_PER_COIN;
    }

    /**
     * @return radius of the coin
     */
    public int getRadius() {
        return RADIUS;
    }

    /**
     * @return size (= 2 * radius) of the coin
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Sets the texture to either the one for points or for lives at random (points = 75%)
     * @param rndValue random Value between 1 and 100
     * @return points the coin gives (constant for points and random between 1 and 100 for lives)
     */
    public int setRandomTexture(int rndValue) {
        if(rndValue < POINT_PROBABILITY) {
            setTextureForPoints();
            return SCORE_PER_COIN;
        } else {
            setTextureForLives();
            randomCoinValue = rndGenerator.nextInt(RANDOM_POINTS_MAX - 1) + 1;
            return randomCoinValue;
        }
    }

    /**
     * sets the local Texture to the lives-Texture
     */
    private void setTextureForLives() {
        image = new Texture(Gdx.files.internal("textures/CoinLive.png"));
    }

    /**
     * sets the local Texture to the points-Texture
     */
    private void setTextureForPoints() {
        image = new Texture(Gdx.files.internal("textures/CoinPoint.png"));
    }

    /**
     * refreshes the x and y Position of the hitBox
     */
    private void refreshHitBox() {
        hitBox.setPosition(xPos, yPos);
    }

    /**
     * disposes the texture
     */
    public void disposeTexture() {
        image.dispose();
    }
}