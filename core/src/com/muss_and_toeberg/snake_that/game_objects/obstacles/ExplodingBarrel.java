package com.muss_and_toeberg.snake_that.game_objects.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class ExplodingBarrel extends Obstacle<Circle> {
    // constant values
    public final int POINTS_NEW_BARREL = 250;

    // variables
    private boolean isExploded = false;
    private int pointsWhenExplosion = 0;

    /**
     * default constructor, which should never (!) be used but needs to exist
     */
    public ExplodingBarrel() {
        super(0,0, 128, new Texture(Gdx.files.internal("textures/Barrel.png")));
    }

    /**
     * constructor which places a barrel at the given position
     * @param xPos position on the x-axis
     * @param yPos position on the y-axis
     */
    public ExplodingBarrel(int xPos, int yPos) {
        super(xPos, yPos, 128, new Texture(Gdx.files.internal("textures/Barrel.png")));
    }

    /**
     * @return true when the barrel is currently exploded
     */
    public boolean checkExploded() {
        return isExploded;
    }

    /**
     * @return points when the barrel exploded
     */
    public int getPointsLastExplosion() {
        return pointsWhenExplosion;
    }

    /**
     * sets the state of the explosion to happened or not
     * @param state new state of the explosion
     */
    public void setExplodedState(boolean state) {
        isExploded = state;
    }

    /**
     * explodes the barrel
     * @param points points when the explosion happened
     */
    public void explode(int points) {
        setExplodedState(true);
        pointsWhenExplosion = points;
    }

    /**
     * checks if the barrel can explode
     * @param snakeHead hitBox of the snake head
     * @return true if the barrel can safely explode
     */
    public boolean checkIfCanExplode(Rectangle snakeHead) {
        if(Intersector.overlaps(getHitBox(), snakeHead) && !isExploded) {
            return true;
        } else {
            return false;
        }
    }
}
