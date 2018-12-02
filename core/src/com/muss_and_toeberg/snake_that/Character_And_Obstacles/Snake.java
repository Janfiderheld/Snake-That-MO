package com.muss_and_toeberg.snake_that.Character_And_Obstacles;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

// Represents the player character (= snake)
public class Snake {
    // Constant Values
    private final int BODY_PART_SIZE = 50;
    private final int BODY_PART_START_AMOUNT = 30;
    private final int ADD_WHEN_COLLECTED = 5;
    private final int BODY_PART_MAX_AMOUNT = 120;
    private final int START_FOR_SUDOKU = 5;

    // Snake Hitboxes
    private Array<Rectangle> bodyParts = new Array<Rectangle>();
    private Rectangle bodyPartTemp;
    private Rectangle head;

    // 2D-Vectors
    private Vector2 direction = new Vector2(1, 1);
    private Vector2 movement = new Vector2(1, 1);

    // local variables
    private int countBodyParts = BODY_PART_START_AMOUNT;
    int currentNeck = 0;

    // creates all the hitBoxes for the snake
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

    // returns the X value of the HitBox for the first rectangle (= head)
    public float getXValueHead() {
        return head.x;
    }

    // returns the Y value of the HitBox for the first rectangle (= head)
    public float getYValueHead() {
        return head.y;
    }

    // returns the whole HitBox for the first rectangle (= head)
    public Rectangle getHeadAsRectangle() {
        return head;
    }

    // returns the Size of each Block
    public int getSizeOfOneBlock() {
        return BODY_PART_SIZE;
    }

    // returns the array with all body parts
    public Array<Rectangle> getBody() {
        return bodyParts;
    }

    // returns the current x-Value of the Movement-Vector (= current position)
    public float getMovementInX() {
        return movement.x;
    }

    // returns the current y-Value of the Movement-Vector (= current position)
    public float getMovementInY() {
        return movement.y;
    }

    // creates a new rectangle at the given position
    private Rectangle createNewHitBox(float x, float y) {
        Rectangle hitBox = new Rectangle();
        hitBox.width = BODY_PART_SIZE;
        hitBox.height = BODY_PART_SIZE;
        hitBox.x = x;
        hitBox.y = y;
        return hitBox;
    }

    // moves the whole body step by step (rectangle by rectangle)
    public void moveSnakeBody() {
        bodyPartTemp = bodyParts.get(currentNeck++);
        bodyPartTemp.x = head.x;
        bodyPartTemp.y = head.y;

        if (currentNeck == countBodyParts){
            currentNeck = 0;
        }

        moveHead();
    }

    // moves the head of the snake
    private void moveHead() {
        head.x = movement.x;
        head.y = movement.y;
    }

    // inverts the x-component of the direction
    public void invertXDirection() {
        direction.x *= (-1);
    }

    // inverts the y-component of the direction
    public void invertYDirection() {
        direction.y *= (-1);
    }

    // scales the direction by the given factor
    public void scaleDirection(int factor) {
        direction.x *= (factor / direction.len());
        direction.y *= (factor / direction.len());
    }

    // scales the direction by the given Factor and the given vector
    public void scaleDirectionWithVector(int factor, Vector2 newDirection) {
        direction.x = newDirection.x * (factor / newDirection.len());
        direction.y = newDirection.y * (factor / newDirection.len());
        direction.rotate(180);
    }

    // moves the whole snake forward
    public void moveWholeSnake() {
        movement.add(direction);
    }

    // adds a new rectangle to the body
    public void addNewBodyPart() {
        for (int i = 0; i < ADD_WHEN_COLLECTED; i++) {
            float tempX = head.x - movement.x * (countBodyParts + 1);
            float tempY = head.y - movement.y * (countBodyParts + 1);

            bodyParts.add(createNewHitBox(tempX, tempY));
            countBodyParts++;
        }
    }

    // checks if the snake collides with itself
    public boolean checkSudoku() {
        int startingValue = currentNeck  % countBodyParts;
        int bodyPartsToCheck = countBodyParts - START_FOR_SUDOKU;
        int checkedBodyParts = 0;

        for(int i = startingValue; checkedBodyParts < bodyPartsToCheck; i = i-- % countBodyParts) {
            bodyPartTemp = bodyParts.get(i);
            checkedBodyParts++;
            if(bodyPartTemp.overlaps(head)) {
                return true;
            }
        }

        return false;
    }
}