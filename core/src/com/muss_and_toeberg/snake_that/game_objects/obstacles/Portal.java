package com.muss_and_toeberg.snake_that.game_objects.obstacles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.muss_and_toeberg.snake_that.game_objects.Snake;

/**
 * Portal which come in pairs and set the player to the location of the other portal
 */
public class Portal extends Obstacle<Circle> {
    // objects & variables
    private Portal otherPortal;
    private boolean ignoreCollision = false;

    /**
     * constructor which places a portal at the given position
     * @param xPos position on the x-axis
     * @param yPos position on the y-axis
     */
    public Portal(int xPos, int yPos) {
        super(xPos, yPos, 90, new Texture(Gdx.files.internal("textures/Portal.png")));
    }

    /**
     * checks if the snake hits this portal
     * @param snake snake to check for
     * @return true when this portal was hit by the head this render
     */
    public boolean doesSnakeHitPortal(Snake snake) {
        boolean hasCollisionWithHead = Intersector.overlaps(this.getHitBox(), snake.getHeadAsRectangle());

        if(!this.ignoreCollision && hasCollisionWithHead) {
            this.otherPortal.ignoreTheCollision(true);
            snake.setMovement(this.otherPortal.getXPosition(), this.otherPortal.getYPosition());
        }

        if(this.ignoreCollision && !hasCollisionWithHead) {
            this.ignoreTheCollision(false);
        }

        return hasCollisionWithHead;
    }

    /**
     * sets if the collision to be ignored during the next hit
     * @param valueToSet value to set the boolean to
     */
    public void ignoreTheCollision(boolean valueToSet) {
        this.ignoreCollision = valueToSet;
    }

    /**
     * sets the corresponding portal
     * @param portal portal to set
     */
    public void setCorrespondingPortal(Portal portal) {
        this.otherPortal = portal;
    }
}
