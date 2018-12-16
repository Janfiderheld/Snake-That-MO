package com.muss_and_toeberg.snake_that.levels_and_screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.muss_and_toeberg.snake_that.obstacles_and_elements.Coin;
import com.muss_and_toeberg.snake_that.obstacles_and_elements.Heart;
import com.muss_and_toeberg.snake_that.obstacles_and_elements.Snake;
import com.muss_and_toeberg.snake_that.obstacles_and_elements.QuadraticBlockHitBox;
import com.muss_and_toeberg.snake_that.technical.HitDirection;
import com.muss_and_toeberg.snake_that.technical.Menu;
import com.muss_and_toeberg.snake_that.technical.TouchInputProcessor;

import java.util.Iterator;
import java.util.Random;

/**
 * First Level of the game
 */
public class FirstLevel implements Screen {
    // Constant width & height values
    final int CAMERA_WIDTH = 1920;
    final int CAMERA_HEIGHT = 1080;
    final int SIZE_OF_HUD = 150;
    final int HUD_BEGIN_Y = CAMERA_HEIGHT - SIZE_OF_HUD;
    final int TEXT_BEGIN_Y = CAMERA_HEIGHT - 15;
    final int BLOCK_X = (CAMERA_WIDTH / 2) - (QuadraticBlockHitBox.HIT_BOX_SIZE / 2);
    final int BLOCK_Y = (CAMERA_HEIGHT / 2) - (QuadraticBlockHitBox.HIT_BOX_SIZE / 2);

    // Constant Values for the hearts
    final int HEART_AMOUNT = 3;
    final int HEART_BEGIN_X = CAMERA_WIDTH - 250;
    final int HEART_BEGIN_Y = CAMERA_HEIGHT - 100;

    // Constant values for the vectors
    final int MAX_VECTOR_LENGTH = 500;
    final int LINE_LENGTH = 10;
    final static float NORMAL_SPEED = 10;
    final static float SLOW_SPEED = 2;

    // Objects to fill the screen with life
    private ShapeRenderer snakeRenderer;
    private MainGame game;
    private Heart[] hearts = new Heart[HEART_AMOUNT];
    private Texture background;

    // Obstacles and Player Character
    private static Snake snake = new Snake();
    private Coin coin = new Coin();
    private Texture blockTexture;
    private QuadraticBlockHitBox block = new QuadraticBlockHitBox(BLOCK_X, BLOCK_Y);

    // all Vectors (2D) which are used
    static Vector2 startTouchVector = new Vector2(0, 0);
    static Vector2 endTouchVector = new Vector2(0, 0);
    static Vector2 lineTouchVector = new Vector2(0, 0);

    // local variables & objects
    private Random rndGenerator = new Random();
    private int lives = HEART_AMOUNT;
    private int score = 0;
    private boolean invincibilityOff = true;

    // class variables
    public static boolean hasHitWall = true;
    private static float speed = NORMAL_SPEED;
    private static int coin_value;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public FirstLevel (final MainGame game){
        this.game = game;

        TouchInputProcessor inputProcessor = new TouchInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);

        snakeRenderer = new ShapeRenderer();
        snake.createSnake(new Vector2(SLOW_SPEED,SLOW_SPEED), new Vector2(300, 300));

        for(int i = 0; i < HEART_AMOUNT; i++) {
            Heart tempHeart = new Heart();
            hearts[i] = tempHeart;
        }

        // for demonstration purposes: Brick instead of Waluigi
        blockTexture = new Texture("Brick.png");
        // blockTexture = new Texture("WaluigiBlock.png");
        background = new Texture ("background.png");
        coin.setNFCTexture(new Texture("NFC.png"));
        coin.setBitCoinTexture(new Texture("Bitcoin.png"));

        randomizeNewCoin();

        Gdx.gl.glClearColor((float)0.7, (float)0.7, (float)0.7, 0);
        // MainGame.currentMenu = Menu.Level;
    }

    /**
     * renders the screen (= fills it with everything)
     * gets called in a constant loop
     * @param delta time since the last render
     */
    @Override
    public void render(float delta) {
        // HACK
        if(MainGame.currentMenu != Menu.Level) {
            MainGame.currentMenu = Menu.Level;
        }
        // HACK

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the Grid-Background (uncomment if required)
		/*
		game.batch.begin();
		game.batch.draw(background, 0, 0);
		game.batch.end();
		*/

        snakeRenderer.setProjectionMatrix(game.camera.combined);
        // renders the snake
        snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        snakeRenderer.setColor(0, 1, 1, 1);
        snakeRenderer.rect(snake.getXValueHead(), snake.getYValueHead(), Snake.BODY_PART_SIZE, Snake.BODY_PART_SIZE);
        for (Iterator<Rectangle> iter = snake.getBody().iterator(); iter.hasNext(); ) {
            Rectangle body = iter.next();
            snakeRenderer.rect(body.x, body.y, Snake.BODY_PART_SIZE, Snake.BODY_PART_SIZE);
        }

        // renders the HUD
        snakeRenderer.rect(0, HUD_BEGIN_Y, CAMERA_WIDTH, SIZE_OF_HUD,
                Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY);
        snakeRenderer.end();

        //renders the Texture Objects & fills the HUD with text
        game.batch.begin();
        for(int i = 0; i < HEART_AMOUNT; i++) {
            Heart tempHeart = hearts[i];
            game.batch.draw(tempHeart.getImage(), HEART_BEGIN_X + (i * 80), HEART_BEGIN_Y);
        }
        game.batch.draw(blockTexture, BLOCK_X, BLOCK_Y);
        game.batch.draw(coin.getTexture(), coin.getXPosition(), coin.getYPosition());
        // TODO: Make these strings multilingual
        game.font.draw(game.batch, "Punkte: " + score, 5, TEXT_BEGIN_Y);
        game.font.draw(game.batch, "Leben:", CAMERA_WIDTH - 700, TEXT_BEGIN_Y);
        game.batch.end();

        startTouchVector.x = snake.getMovementInX() + (Snake.BODY_PART_SIZE / 2);
        startTouchVector.y = snake.getMovementInY() + (Snake.BODY_PART_SIZE / 2);

        snake.moveSnakeBody();

        // checks if the screen is currently touched (= can the snake be directed?)
        if (Gdx.input.isTouched() && hasHitWall) {
            endTouchVector.x = CAMERA_WIDTH * Gdx.input.getX() / Gdx.graphics.getWidth();
            endTouchVector.y = CAMERA_HEIGHT * (Gdx.graphics.getHeight() - Gdx.input.getY()) / Gdx.graphics.getHeight();

            if (startTouchVector.dst(endTouchVector) > MAX_VECTOR_LENGTH) {
                Vector2 connectionVect = new Vector2(endTouchVector.x - startTouchVector.x, endTouchVector.y - startTouchVector.y);
                lineTouchVector.x = connectionVect.x * (MAX_VECTOR_LENGTH / connectionVect.len()) + startTouchVector.x;
                lineTouchVector.y = connectionVect.y * (MAX_VECTOR_LENGTH / connectionVect.len()) + startTouchVector.y;
            } else {
                lineTouchVector = endTouchVector;
            }

            snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            snakeRenderer.setColor(0, 0, 1, 1);
            snakeRenderer.rectLine(startTouchVector, lineTouchVector, LINE_LENGTH);
            snakeRenderer.end();
        }

        // checks the different possible collisions
        checkCollisionWithWall();
        checkCollisionWithBlock();
        checkCollisionWithCoin();
        checkCollisionWithSnakeBody();
        checkCollisionWithObstacle();

        snake.increaseMovementVector();
        game.camera.update();
    }

    /**
     * disposes all Textures and similar objects at the end of the lifecycle
     */
    @Override
    public void dispose() {
        blockTexture.dispose();
        coin.disposeAllTextures();
        snakeRenderer.dispose();
        for(int i = 0; i < HEART_AMOUNT; i++) {
            Heart tempHeart = hearts[i];
            tempHeart.getImage().dispose();
        }
    }

    /**
     * changes the speed of the snake to reduced speed as soon as the screen is touched
     */
    public static void setDirectionVectorDown(){
        if (!hasHitWall) {
            return;
        }

        speed = SLOW_SPEED;
        snake.scaleDirection(speed);
    }

    /**
     * changes the speed of the snake to normal speed as soon as the touch is lifted
     */
    public static void setDirectionVectorUp(){
        if (!hasHitWall) {
            return;
        }

        speed = NORMAL_SPEED;
        Vector2 connectionVector = new Vector2(endTouchVector.x - startTouchVector.x, endTouchVector.y - startTouchVector.y);
        snake.setDirectionToScaledVector(speed, connectionVector);
    }

    /**
     * checks if the snake collides with the block in the middle (= Waluigi)
     */
    private void checkCollisionWithBlock() {
        HitDirection side = block.checkWhichCollisionSide(snake.getHeadAsRectangle());

        switch(side) {
            case LeftSide:
            case RightSide:
                snake.invertXDirection();
                break;
            case Upwards:
            case Downwards:
                snake.invertYDirection();
                break;
        }
    }

    /**
     * checks if the snake collides with the outer walls
     */
    private void checkCollisionWithWall() {
        if (snake.getMovementInX() + Snake.BODY_PART_SIZE >= CAMERA_WIDTH || snake.getMovementInX() <= 0) {
            snake.invertXDirection();
            hasHitWall = true;
        }

        if (snake.getMovementInY() + Snake.BODY_PART_SIZE >= CAMERA_HEIGHT - SIZE_OF_HUD || snake.getMovementInY() <= 0) {
            snake.invertYDirection();
            hasHitWall = true;
        }
    }

    /**
     * checks if the snake collides with a coin
     */
    private void checkCollisionWithCoin() {
        if (Intersector.overlaps(coin.getHitBox(), snake.getHeadAsRectangle())) {
            snake.addNewBodyPart();
            score += coin_value;
            if(coin_value != coin.getPointsForNFC()) {
                gainALive();
            }
            randomizeNewCoin();
        }
    }

    /**
     * checks if the snake collides with itself
     */
    private void checkCollisionWithSnakeBody() {
        if(!snake.checkSuicide()) {
            invincibilityOff = true;
        } else {
            if(invincibilityOff && !Gdx.input.isTouched()) {
                looseALive();
                invincibilityOff = false;
            }
        }
    }

    /**
     * checks if the snake collides with one of the Obstacles
     */
    private void checkCollisionWithObstacle() {
        // TODO: Fill with collision detection when obstacles are added
    }

    /**
     * player looses a live and one heart gets unfilled
     */
    private void looseALive() {
        for(int i = 0; i < HEART_AMOUNT; i++) {
            Heart tempHeart = hearts[i];
            if(tempHeart.isFilled()) {
                tempHeart.unfillTheHeart();
                lives--;
                return;
            }
        }
    }

    /**
     * player gains a live and one heart gets filled
     */
    private void gainALive() {
        for(int i = HEART_AMOUNT - 1; i >= 0; i--) {
            Heart tempHeart = hearts[i];
            if(!tempHeart.isFilled()) {
                tempHeart.fillTheHeart();
                lives++;
                return;
            }
        }
    }

    /**
     * changes the x and y coordinates of the coin at a random place
     * (which is not inside the block or the HUD)
     */
    private void randomizeNewCoin() {
        int coinX = rndGenerator.nextInt(CAMERA_WIDTH  - (coin.getSize() + QuadraticBlockHitBox.HIT_BOX_SIZE + coin.getRadius()));
        if(coinX > BLOCK_X - coin.getRadius() - 1) {
            coinX += QuadraticBlockHitBox.HIT_BOX_SIZE + coin.getSize() + 1;
        } else if (coinX < coin.getRadius()) {
            coinX += coin.getRadius();
        }

        int coinY = rndGenerator.nextInt(CAMERA_HEIGHT - (coin.getSize() + QuadraticBlockHitBox.HIT_BOX_SIZE + SIZE_OF_HUD + coin.getRadius() + 1));
        if(coinY >= BLOCK_Y - coin.getRadius() - 1) {
            coinY += QuadraticBlockHitBox.HIT_BOX_SIZE + coin.getSize() + 1;
        } else if (coinY < coin.getRadius()) {
            coinY += coin.getRadius();
        }

        coin.setXPosition(coinX);
        coin.setYPosition(coinY);
        coin_value = coin.setRandomTexture(rndGenerator.nextInt(100) + 1);
    }




    // currently not used implements of Screen
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }


    @Override
    public void show() {

    }
}