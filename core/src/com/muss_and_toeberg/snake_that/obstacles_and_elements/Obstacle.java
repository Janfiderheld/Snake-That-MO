package com.muss_and_toeberg.snake_that.obstacles_and_elements;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.muss_and_toeberg.snake_that.technical.IsNoCircleException;

/**
 * Parent-Class for the different obstacles
 * @param <THitBoxType> specifies the type of HitBox (if Rectangle or Circle)
 */
public class Obstacle<THitBoxType> {
    private THitBoxType hitBox;
    private float xPos;
    private float yPos;
    private int size;
    private int radius;
    private boolean hasRectangleHitBox;

    /**
     * Constructor which creates a new obstacle with the given parameters
     * @param xPos position on the x-axis
     * @param yPos position on the y-axis
     * @param size size of the obstacle
     */
    public Obstacle(float xPos, float yPos, int size) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
        if(hitBox instanceof Rectangle) {
            hasRectangleHitBox = true;
        } else {
            hasRectangleHitBox = false;
            this.radius = size / 2;
        }
    }

    /**
     * @return hitBox as either a Rectangle or a circle
     */
    public THitBoxType getHitBox() {
        return hitBox;
    }

    /**
     * @return position on the x-axis
     */
    public float getXPosition() {
        if(hasRectangleHitBox) {
            return xPos;
        } else {
            return xPos - radius;
        }
    }

    /**
     * @return position on the y-axis
     */
    public float getYPosition() {
        if(hasRectangleHitBox) {
            return yPos;
        } else {
            return yPos - radius;
        }
    }

    /**
     * @return size of the obstacle
     */
    public int getSize() {
        return size;
    }

    /**
     * @return radius of the obstacle
     * @throws Exception when the obstacle is a rectangle (= has no radius)
     */
    public int getRadius() throws IsNoCircleException {
        if(hasRectangleHitBox) {
            throw new IsNoCircleException();
        }
        return radius;
    }

    /**
     * sets the hitBox
     * @param newHitBox new HitBox (either rectangle or circle)
     */
    public void setHitBox(THitBoxType newHitBox) {
        if(newHitBox instanceof Rectangle) {
            hasRectangleHitBox = true;
        } else {
            hasRectangleHitBox = false;
        }
        this.hitBox = newHitBox;
        refreshHitBox();
    }

    /**
     * sets the current position to the given value and refreshes the hitBox
     * @param xPos new position on the x-axis
     */
    public void setXPosition(float xPos) {
        this.xPos = xPos;
        refreshHitBox();
    }

    /**
     *  sets the current position to the given value and refreshes the hitBox
     *  @param yPos new position on the y-axis
     */
    public void setYPosition(float yPos) {
        this.yPos = yPos;
        refreshHitBox();
    }

    /**
     * sets the size to the given value and refreshes the hitBox
     * if the hitBox is a circle the radius is also changed
     * @param size new size of the Obstacle
     */
    public void changeSize(int size) {
        this.size = size;
        if(!hasRectangleHitBox) {
            radius = size / 2;
        }
        refreshHitBox();
    }

    /**
     * @return true if the hitBox is an object of the rectangle-class
     */
    public boolean isHitBoxARectangle() {
        if(hasRectangleHitBox) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * refreshes all aspects of the HitBox
     */
    private void refreshHitBox() {
        if (hasRectangleHitBox) {
            ((Rectangle)hitBox).set(xPos, yPos, size, size);
        } else {
            ((Circle) hitBox).set(xPos, yPos, radius);
        }
    }
}
