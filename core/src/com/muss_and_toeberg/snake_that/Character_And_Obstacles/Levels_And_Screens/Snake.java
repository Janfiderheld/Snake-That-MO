package com.muss_and_toeberg.snake_that.Character_And_Obstacles.Levels_And_Screens;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

// Represents the player character (= snake)
public class Snake {
    // Constant Values
    private final int BLOCK_SIZE = 50;
    private final int BLOCK_AMOUNT = 30;

    // Snake Hitboxes
    private Array<Rectangle> bodyParts = new Array<Rectangle>();
    private Rectangle bodyPartTemp;
    private Rectangle head;
    private Rectangle bodyBack;
    private Rectangle bodyFront;

    // 2D-Vectors
    private Vector2 direction = new Vector2(1, 1);
    private Vector2 movement = new Vector2(1, 1);

    // creates all the hitBoxes for the snake
    public void createSnake() {
        head = new Rectangle();
        head.width = BLOCK_SIZE;
        head.height = BLOCK_SIZE;

        for (int i = 0; i < BLOCK_AMOUNT; i++){
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
        return BLOCK_SIZE;
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
        hitBox.width = BLOCK_SIZE;
        hitBox.height = BLOCK_SIZE;
        hitBox.x = x;
        hitBox.y = y;
        return hitBox;
    }

    // moves the whole body step by step (rectangle by rectangle)
    public void moveSnakeBody() {
        int length = bodyParts.size - 1;

        for(int i = 0; i < length; i++){
            bodyBack = bodyParts.get(length - i);
            bodyFront = bodyParts.get(length - (i + 1));

            bodyBack.x = bodyFront.x;
            bodyBack.y = bodyFront.y;
        }

        moveHeadAndNeck();
    }

    // moves the last (or first) two rectangles
    private void moveHeadAndNeck() {
        bodyPartTemp = bodyParts.get(0);
        bodyPartTemp.x = head.x;
        bodyPartTemp.y = head.y;
        bodyParts.set(0, bodyPartTemp);

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
        float tempX = head.x - movement.x * (BLOCK_AMOUNT + 1);
        float tempY = head.y - movement.y * (BLOCK_AMOUNT + 1);

        bodyParts.add(createNewHitBox(tempX, tempY));
    }
}