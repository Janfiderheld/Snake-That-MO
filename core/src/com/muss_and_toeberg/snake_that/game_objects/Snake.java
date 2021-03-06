package com.muss_and_toeberg.snake_that.game_objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.muss_and_toeberg.snake_that.screens.Settings;
import com.muss_and_toeberg.snake_that.technical.SnakeColor;

/**
 * Represents the snake (= player character)
 */
public class Snake {
    // Constant Values
    public static final int BODY_PART_SIZE = 50;
    private final int BODY_PART_START_AMOUNT = 30;
    public final int ADD_WHEN_COLLECTED = 10;

    // Snake HitBoxes
    private Array<Rectangle> bodyParts;
    private Rectangle bodyPartTemp;
    private Rectangle head;

    // Color as a class object
    private static SnakeColor colorAsEnum = SnakeColor.makeIntToSnakeColor(Settings.checkForColor());
    private static Color color = SnakeColor.createColor(colorAsEnum);

    // 2D-Vectors
    private Vector2 direction;
    private Vector2 movement;

    // local variables
    private int countBodyParts = BODY_PART_START_AMOUNT;
    private int currentNeck = countBodyParts - 1;

    /**
     * creates all the hitBoxes for the snake & sets the Vectors
     * @param startDirection new starting direction
     * @param startPosition new starting position
     */
    public void createSnake(Vector2 startDirection, Vector2 startPosition) {
        direction = startDirection.cpy();
        movement = startPosition.cpy();
		
		bodyParts = new Array<Rectangle>();
		countBodyParts = BODY_PART_START_AMOUNT;
		currentNeck = countBodyParts - 1;
        head = createNewHitBox(getMovementInX(), getMovementInY());

        int distanceToNextPart = 10;
        for (int count = 0; count < countBodyParts; count++){
            float tempX = head.x - distanceToNextPart * (count + 1);
            float tempY = head.y - distanceToNextPart * (count + 1);

            bodyParts.add(createNewHitBox(tempX, tempY));
        }
    }

    /**
     * @return position of the first rectangle (= head) on the x-axis
     */
    public float getXValueHead() {
        return head.x;
    }

    /**
     * @return position of the first rectangle (= head) on the y-axis
     */
    public float getYValueHead() {
        return head.y;
    }

    /**
     * @return current Color of the snake
     */
    public static Color getColor() {
        return color;
    }

    /**
     * @return current Color of the snake as an enum entry
     */
    public static SnakeColor getColorAsEnum() {
        return colorAsEnum;
    }

    /**
     * sets the color of the snake
     * @param col enum representative of the color
     */
    public static void setColorByEnum(SnakeColor col) {
        colorAsEnum = col;
        color = SnakeColor.createColor(col);
    }

    /**
     * @return hitBox for the Head as a Rectangle-Object
     */
    public Rectangle getHeadAsRectangle() {
        return head;
    }

    /**
     * @return array containing all body parts (= Rectangles)
     */
    public Array<Rectangle> getBody() {
        return bodyParts;
    }

    /**
     * @return position of the head / movement vector on the x-axis
     */
    public float getMovementInX() {
        return movement.x;
    }

    /**
     * @return position of the head / movement vector on the y-axis
     */
    public float getMovementInY() {
        return movement.y;
    }

    /**
     * sets the current position to the given values (used by the portal)
     * @param xPos new position on the x-axis
     * @param yPos new position in the y-axis
     */
    public void setMovement(float xPos, float yPos) {
        movement.x = xPos;
        movement.y = yPos;
    }

    /**
     * creates a new rectangle at the given position
     * @param x position on the x-axis
     * @param y position on the y-axis
     * @return new body part
     */
    private Rectangle createNewHitBox(float x, float y) {
        Rectangle hitBox = new Rectangle();
        hitBox.width = BODY_PART_SIZE;
        hitBox.height = BODY_PART_SIZE;
        hitBox.x = x;
        hitBox.y = y;
        return hitBox;
    }

    /**
     * moves snake body forward in the direction of the head
     * always changes the position of the last rectangle to
     * the one position of the head
     */
    public void moveSnakeBody() {
        bodyPartTemp = bodyParts.get(currentNeck--);
        bodyPartTemp.x = head.x;
        bodyPartTemp.y = head.y;

        if (currentNeck < 0){
            currentNeck = countBodyParts - 1;
        }

        moveHead();
    }

    /**
     * moves the head of the snake
     */
    private void moveHead() {
        head.x = getMovementInX();
        head.y = getMovementInY();
    }

    /**
     * inverts the direction on the x-axis (= rotates vector by 180°)
     */
    public void invertXDirection() {
        direction.x *= (-1);
    }

    /**
     * inverts the direction on the y-axis (= rotates vector by 180°)
     */
    public void invertYDirection() {
        direction.y *= (-1);
    }

    /**
     * inverts both directions (= rotates vector by 180°)
     */
    public void invertBothDirections() {
        invertXDirection();
        invertYDirection();
    }

    /**
     * sets the moving speed by scaling the direction vector
     * @param factor scaling factor
     */
    public void scaleDirection(float factor) {
        direction.x *= (factor / direction.len());
        direction.y *= (factor / direction.len());
    }

    /**
     * sets the direction to the given vector scaled by the given factor
     * and rotates it since the vector points in the opposite direction
     * @param factor scaling factor
     * @param newDirection vector to scale
     */
    public void setDirectionToScaledVector(float factor, Vector2 newDirection) {
        direction.x = newDirection.x * (factor / newDirection.len());
        direction.y = newDirection.y * (factor / newDirection.len());
        direction.rotate(180);
    }

    /**
     * increases Movement Vector in the given direction
     */
    public void increaseMovementVector() {
        movement.add(direction);
    }

    /**
     * adds a new rectangle to the body
     */
    public void addNewBodyPart() {
        for (int i = 0; i < ADD_WHEN_COLLECTED; i++) {
            float tempX = head.x - getMovementInX() * (countBodyParts + 1);
            float tempY = head.y - getMovementInY() * (countBodyParts + 1);

            bodyParts.add(createNewHitBox(tempX, tempY));
            countBodyParts++;
        }
    }

    /**
     * checks if the snake collides with itself
     * @return true if collision appears
     */
    public boolean checkSuicide() {
        int noCollisionZone = 25;
        int currentBodyPart = (currentNeck + noCollisionZone)  % countBodyParts;
        int bodyPartsToCheck = countBodyParts - noCollisionZone;

        for(int checkedBodyParts = 0; checkedBodyParts < bodyPartsToCheck;
            checkedBodyParts++) {
            bodyPartTemp = bodyParts.get(currentBodyPart);
            currentBodyPart = (currentBodyPart + 1) % countBodyParts;
            if(bodyPartTemp.overlaps(head)) {
                return true;
            }
        }

        return false;
    }
}