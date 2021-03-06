package com.muss_and_toeberg.snake_that.game_objects.obstacles;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;

/**
 * Parent-Class for the different obstacles
 * @param <THitBoxType> specifies the type of HitBox (if Rectangle or Circle)
 */
public abstract class Obstacle<THitBoxType> {
	// coordinates & sizes
    private float xPos;
    private float yPos;
    private int size;
    private int radius;

	// hitbox and texture
	private THitBoxType hitBox;
	private Texture image;
    private boolean hasRectangleHitBox;

    /**
     * Constructor which creates a new obstacle with the given parameters
     * @param xPos position on the x-axis
     * @param yPos position on the y-axis
     * @param size size of the obstacle
     */
    public Obstacle(float xPos, float yPos, int size, Texture imgToShow) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.size = size;
		image = imgToShow;
		
        if(hitBox instanceof Rectangle) {
            hasRectangleHitBox = true;
            hitBox = (THitBoxType) new Rectangle();
        } else {
            hasRectangleHitBox = false;
            hitBox = (THitBoxType) new Circle();
            this.radius = size / 2;
        }
        refreshHitBox();
    }

    /**
     * @return hitBox as either a Rectangle or a circle
     */
    public THitBoxType getHitBox() {
        return hitBox;
    }
	
	/**
	 * @return image to render as a texture
	 */
	public Texture getTexture() {
		return image;
	}

    /**
     * @return position on the x-axis
     */
    public float getXPosition() {
        if(checkHitBoxForRectangle()) {
            return xPos;
        } else {
            return xPos - radius;
        }
    }

    /**
     * @return position on the y-axis
     */
    public float getYPosition() {
        if(checkHitBoxForRectangle()) {
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
     * @return radius of the obstacle or -1 if it is no circle
     */
    public int getRadius() {
        if(!checkHitBoxForRectangle()) {
            return radius;
        } else {
            return -1;
        }

    }

    /**
     * sets the hitBox
     * @param newHitBox new HitBox (either rectangle or circle)
     */
    public void setHitBox(THitBoxType newHitBox) {
        if(newHitBox instanceof Rectangle) {
            hasRectangleHitBox = true;
            setPosition(((Rectangle) newHitBox).x, ((Rectangle) newHitBox).y);
        } else {
            hasRectangleHitBox = false;
            setPosition(((Circle)newHitBox).x, ((Circle)newHitBox).y);
        }
        this.hitBox = newHitBox;
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
     * sets the current position to the given value and refreshes the hitBox
     * @param xPos new position on the x-axis
     * @param yPos new position on the y-axis
     */
    public void setPosition(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        refreshHitBox();
    }
	
	/**
	 * @param newImage new Texture to render
	  */
	public void setTexture(Texture newImage) {
		image = newImage;
	}

    /**
     * sets the size to the given value and refreshes the hitBox
     * if the hitBox is a circle the radius is also changed
     * @param size new size of the Obstacle
     */
    public void changeSize(int size) {
        this.size = size;
        if(!checkHitBoxForRectangle()) {
            radius = size / 2;
        }
        refreshHitBox();
    }

    /**
     * @return true if the hitBox is an object of the rectangle-class
     */
    public boolean checkHitBoxForRectangle() {
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
        if (checkHitBoxForRectangle()) {
            ((Rectangle) hitBox).set(xPos, yPos, size, size);
        } else {
            ((Circle) hitBox).set(xPos, yPos, radius);
        }
    }

    /**
     * disposes the texture
     */
    public void dispose() {
        image.dispose();
    }
}