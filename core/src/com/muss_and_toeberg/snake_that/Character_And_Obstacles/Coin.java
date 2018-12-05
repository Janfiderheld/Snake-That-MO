package com.muss_and_toeberg.snake_that.Character_And_Obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

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

    // the Position on the coordinate system
    private int xPos = RADIUS;
    private int yPos = RADIUS;

    //
    private Random rndGenerator = new Random();
    private int bitCoinStock;

    // Constructor
    public Coin() {
        setRandomTexture(NFC_PROBABILITY);
        hitBox.radius = RADIUS;
        refreshHitBox();
    }

    // returns the texture
    public Texture getTexture() {
        return image;
    }

    // returns the hitBox as a circle
    public Circle getHitBox() {
        return hitBox;
    }

    // returns the position on the x-axis
    public int getXPosition() {
        return xPos - RADIUS;
    }

    // returns the position on the y-axis
    public int getYPosition() {
        return yPos - RADIUS;
    }

    // sets the position on the x-axis to the given value
    public void setXPosition(int xPos) {
        this.xPos = xPos + RADIUS;
        refreshHitBox();
    }

    // sets the position on the y-axis to the given value
    public void setYPosition(int yPos) {
        this.yPos = yPos + RADIUS;
        refreshHitBox();
    }

    // returns the radius of a coin
    public int getRadius() {
        return RADIUS;
    }

    // returns the Size of a coin
    public int getSize() {
        return SIZE;
    }

    // sets the texture to either NFC or Bitcoin at random
    public int setRandomTexture(int rndValue) {
        if(rndValue < NFC_PROBABILITY) {
            setTextureToNFC();
            return 50;
        } else {
            setTextureToBitcoin();
            bitCoinStock = rndGenerator.nextInt(100);
            return bitCoinStock;
        }
    }

    // sets the Texture to the Bitcoin Icon
    private void setTextureToBitcoin() {
        // image = new Texture(Gdx.files.internal("Bitcoin.png"));
        image = imgBitCoin;
    }

    // sets the Texture to the NFC Icon
    private void setTextureToNFC() {
        // image = new Texture(Gdx.files.internal("NFC.png"));
        image = imgNFC;
    }

    // refreshes the coordinates of the hitBox when they are changed
    private void refreshHitBox() {
        hitBox.x = xPos;
        hitBox.y = yPos;
    }

    // TODO: Find a way to make these two obsolete
    // Since I don't know how to access our assets (= the images) from this class,
    // they are accessed by the LevelScreen and made into texture-objetcs there
    // with these two methods, they can be set
    public void setNFCTexture(Texture nfc) {
        imgNFC = nfc;
    }

    public void setBitCoinTexture(Texture bitCoin) {
        imgBitCoin = bitCoin;
    }

}
