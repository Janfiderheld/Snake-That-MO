package com.muss_and_toeberg.snake_that.obstacles_and_elements;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

/**
 * Object which the player can collect to gain points
 */
public class Coin {
    // Texture for the image and the hitBox
    private Texture image;
    private Texture imgNFC;
    private Texture imgBitCoin;
    private Circle hitBox = new Circle();

    // Constant Values
    private final int RADIUS = 32;
    private final int SIZE = RADIUS * 2;
    private final int NFC_PROBABILITY = 75;
    private final int NFC_SCORE = 50;
    private final int BITCOIN_STOCK_MAX = 100;

    // the Position on the coordinate system
    private int xPos = RADIUS;
    private int yPos = RADIUS;

    // local variables
    private Random rndGenerator = new Random();
    private int bitCoinStock;

    /**
     * sets a random Texture, sets the coin-radius and refreshes the hitBox-Position
     */
    public Coin() {
        setRandomTexture(NFC_PROBABILITY);
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
        this.xPos = xPos + RADIUS;
        refreshHitBox();
    }

    /**
     * changes the y-Position and refreshes the HitBox
     * @param yPos - new Position on the y-axis
     */
    public void setYPosition(int yPos) {
        this.yPos = yPos + RADIUS;
        refreshHitBox();
    }

    /**
     * @return constant point-value the player gets for a NFC coin
     */
    public int getPointsForNFC() {
        return NFC_SCORE;
    }

    /**
     * @return size (= 2 * radius) of the coin
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Sets the texture to either NFC or ButCoin at random (NFC = 75%)
     * @param rndValue random Value between 1 and 100
     * @return points the coin gives (constant for NFC and random between 1 and 100 for BitCoin
     */
    public int setRandomTexture(int rndValue) {
        if(rndValue < NFC_PROBABILITY) {
            setTextureToNFC();
            return NFC_SCORE;
        } else {
            setTextureToBitcoin();
            bitCoinStock = rndGenerator.nextInt(BITCOIN_STOCK_MAX - 1) + 1;
            return bitCoinStock;
        }
    }

    /**
     * sets the local Texture to the BitCoin-Icon
     */
    private void setTextureToBitcoin() {
        // image = new Texture(Gdx.files.internal("Bitcoin.png"));
        image = imgBitCoin;
    }

    /**
     * sets the local Texture to the NFC-Icon
     */
    private void setTextureToNFC() {
        // image = new Texture(Gdx.files.internal("NFC.png"));
        image = imgNFC;
    }

    /**
     * refreshes the x and y Position of the hitBox
     */
    private void refreshHitBox() {
        hitBox.x = xPos;
        hitBox.y = yPos;
    }

    // HACK
    // Since I don't know how to access our assets (= the images) from this class,
    // they are accessed by the LevelScreen and made into texture-objetcs there
    // with these two methods, they can be set
    public void setNFCTexture(Texture nfc) {
        imgNFC = nfc;
    }

    public void setBitCoinTexture(Texture bitCoin) {
        imgBitCoin = bitCoin;
    }
    // HACK
}
