package com.muss_and_toeberg.snake_that.obstacles_and_elements;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Represents the snake (= player character)
 */
public class Snake {
    // Constant Values
    public static final int BODY_PART_SIZE = 50;
    private final int BODY_PART_START_AMOUNT = 30;
    private final int ADD_WHEN_COLLECTED = 3;
    private final int START_FOR_SUDOKU = 15;
    private final int BODY_PART_MAX_AMOUNT = 120;
    private final float BODY_PART_DISTANCE = 5;

    // Snake HitBoxes
    private Array<Rectangle> bodyParts = new Array<Rectangle>();
    private Rectangle bodyPartTemp;
    private Rectangle head;

    // 2D-Vectors
    private Vector2 direction = new Vector2(1, 1);
    private Vector2 movement = new Vector2(1, 1);

    // local variables
    private int countBodyParts = BODY_PART_START_AMOUNT;
    private int currentNeck = 0;

    /**
     * creates all the hitBoxes for the snake
     */
    public void createSnake() {
        head = new Rectangle();
        head.width = BODY_PART_SIZE;
        head.height = BODY_PART_SIZE;

        for (int i = 0; i < countBodyParts; i++){
            float tempX = head.x - movement.x * (i + 1);
            float tempY = head.y - movement.y * (i + 1);

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
     * always changes the position of the last rectangle to the one position of the head
     */
    public void moveSnakeBody() {
        bodyPartTemp = bodyParts.get(currentNeck++);
        bodyPartTemp.x = head.x;
        bodyPartTemp.y = head.y;

        if (currentNeck == countBodyParts){
            currentNeck = 0;
        }

        moveHead();
    }

    /**
     * moves the head of the snake
     */
    private void moveHead() {
        head.x = movement.x;
        head.y = movement.y;
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
     * sets the moving speed by scaling the direction vector
     * @param factor scaling factor
     */
    public void scaleDirection(float factor) {
        direction.x *= (factor / direction.len());
        direction.y *= (factor / direction.len());
    }

    /**
     * sets the direction to the given vector scaled by the given factor
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
            float tempX = head.x - movement.x * (countBodyParts + 1);
            float tempY = head.y - movement.y * (countBodyParts + 1);

            bodyParts.add(createNewHitBox(tempX, tempY));
            countBodyParts++;
        }
    }

    /**
     * checks if the snake collides with itself
     * @return true if collision appears
     */
    public boolean checkSudoku() {
        int startingValue = (currentNeck - START_FOR_SUDOKU)  % countBodyParts;
        if (startingValue < 0){
            startingValue += countBodyParts;
        }
        int bodyPartsToCheck = countBodyParts - START_FOR_SUDOKU;
        int currentBodyPart = startingValue;

        for(int checkedBodyParts = 0; checkedBodyParts < bodyPartsToCheck; checkedBodyParts++) {
            bodyPartTemp = bodyParts.get(currentBodyPart);
            currentBodyPart = (currentBodyPart - 1) % countBodyParts;
            if (currentBodyPart < 0){
                currentBodyPart += countBodyParts;
            }
            if(bodyPartTemp.overlaps(head)) {
                return true;
            }
        }

        return false;
    }
}