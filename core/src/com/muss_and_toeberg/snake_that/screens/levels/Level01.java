package com.muss_and_toeberg.snake_that.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.muss_and_toeberg.snake_that.game_objects.Coin;
import com.muss_and_toeberg.snake_that.game_objects.Heart;
import com.muss_and_toeberg.snake_that.game_objects.Snake;
import com.muss_and_toeberg.snake_that.game_objects.obstacles.ExplodingBarrel;
import com.muss_and_toeberg.snake_that.game_objects.obstacles.QuadraticBlockHitBox;
import com.muss_and_toeberg.snake_that.technical.MainGame;
import com.muss_and_toeberg.snake_that.screens.NewHighscore;
import com.muss_and_toeberg.snake_that.screens.Settings;
import com.muss_and_toeberg.snake_that.technical.HitDirection;
import com.muss_and_toeberg.snake_that.technical.Menu;
import com.muss_and_toeberg.snake_that.technical.TouchInputProcessor;

import java.util.Iterator;
import java.util.Random;

/**
 * First Level of the game
 */
public class Level01 implements Screen {
    // Constant width & height values
    private final int SIZE_OF_HUD = 150;
    private final int HUD_BEGIN_Y = MainGame.CAMERA_HEIGHT - SIZE_OF_HUD;
    private final int TEXT_BEGIN_Y = MainGame.CAMERA_HEIGHT - 15;
    private final int BLOCK_X = (MainGame.CAMERA_WIDTH / 2) - (QuadraticBlockHitBox.HIT_BOX_SIZE / 2);
    private final int BLOCK_Y = ((MainGame.CAMERA_HEIGHT - SIZE_OF_HUD) / 2) - (QuadraticBlockHitBox.HIT_BOX_SIZE / 2);
    private final int BARREL_START_X = 1530;
    private final int BARREL_START_Y = 124;

    // Constant Values for the hearts
    private final int HEART_AMOUNT = 3;
    private final int HEART_BEGIN_X = MainGame.CAMERA_WIDTH - 250;
    private final int HEART_BEGIN_Y = MainGame.CAMERA_HEIGHT - 100;

    // Constant values for the vectors
    private final int MAX_VECTOR_LENGTH = 500;
    private final int LINE_LENGTH = 10;
    private final static float NORMAL_SPEED = 10;
    private final static float SLOW_SPEED = 2;

    // Objects to fill the screen with life
    private ShapeRenderer snakeRenderer;
    private MainGame game;
    private Heart[] hearts = new Heart[HEART_AMOUNT];
    private Texture background;
    private ExplodingBarrel barrel = new ExplodingBarrel(BARREL_START_X, BARREL_START_Y);

    // Obstacles and Player Character
    private static Snake snake = new Snake();
    private Coin coin = new Coin();
    private Texture blockTexture;
    private Texture hat;
    private QuadraticBlockHitBox block = new QuadraticBlockHitBox(BLOCK_X, BLOCK_Y);

    // all Vectors (2D) which are used
    static Vector2 startTouchVector = new Vector2(0, 0);
    static Vector2 endTouchVector = new Vector2(0, 0);
    static Vector2 lineTouchVector = new Vector2(0, 0);

    // local variables & objects
    private Random rndGenerator = new Random();
    private int lives = HEART_AMOUNT;
    private int score = 0;
    private boolean invincibilityOn = true;
    private float countInvincFrames = 0;

    // class variables
    public static boolean hasHitWall = true;
    public static boolean shouldGoBack = false;
    public static boolean gameHasStarted = true;
    private static float speed = NORMAL_SPEED;
    private static int coin_value = 0;

    /**
     * Constructor which is used to create all objects that only need to be created once
     * @param game game object which allows screen changing
     */
    public Level01(final MainGame game){
        this.game = game;

        TouchInputProcessor inputProcessor = new TouchInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);

        snakeRenderer = new ShapeRenderer();
        snake.createSnake(new Vector2(SLOW_SPEED,SLOW_SPEED), new Vector2(300, 300));

        for(int i = 0; i < HEART_AMOUNT; i++) {
            Heart tempHeart = new Heart();
            hearts[i] = tempHeart;
        }

        blockTexture = new Texture("textures/Brick.png");
        background = new Texture ("textures/backgroundPipes.png");
        hat = new Texture("textures/SantaHat.png");

        randomizeCircleObject(coin.getSize(), true);
        game.memController.startTimer();

        Gdx.gl.glClearColor((float)0.7, (float)0.7, (float)0.7, 0);
    }

    /**
     * renders the screen (= fills it with everything)
     * gets called in a constant loop
     * @param delta time since the last render
     */
    @Override
    public void render(float delta) {
        if(MainGame.currentMenu != Menu.Level) {
            MainGame.currentMenu = Menu.Level;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        snakeRenderer.setProjectionMatrix(game.camera.combined);

        // draw the background
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();

        // renders & draws everything visible
        snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        renderTheSnake();
        snakeRenderer.rect(0, HUD_BEGIN_Y, MainGame.CAMERA_WIDTH, SIZE_OF_HUD,
                Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY);
        snakeRenderer.end();
        drawTheTextures();

        if(gameHasStarted) {
            startTouchVector.x = snake.getMovementInX() + (Snake.BODY_PART_SIZE / 2);
            startTouchVector.y = snake.getMovementInY() + (Snake.BODY_PART_SIZE / 2);

            snake.moveSnakeBody();

            // checks if the screen is currently touched (= can the snake be directed?)
            if (Gdx.input.isTouched() && hasHitWall) {
                endTouchVector.x = MainGame.CAMERA_WIDTH * Gdx.input.getX() / Gdx.graphics.getWidth();
                endTouchVector.y = MainGame.CAMERA_HEIGHT * (Gdx.graphics.getHeight() - Gdx.input.getY()) / Gdx.graphics.getHeight();

                if (startTouchVector.dst(endTouchVector) > MAX_VECTOR_LENGTH) {
                    Vector2 connectionVect = new Vector2(endTouchVector.x - startTouchVector.x, endTouchVector.y - startTouchVector.y);
                    lineTouchVector.x = connectionVect.x * (MAX_VECTOR_LENGTH / connectionVect.len()) + startTouchVector.x;
                    lineTouchVector.y = connectionVect.y * (MAX_VECTOR_LENGTH / connectionVect.len()) + startTouchVector.y;
                } else {
                    lineTouchVector = endTouchVector;
                }

                snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                snakeRenderer.setColor(Color.BLUE);
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
        }

        if (shouldGoBack) {
            changeTheScreen();
        }

        game.camera.update();
    }

    /**
     * disposes all Textures and similar objects at the end of the lifecycle
     */
    @Override
    public void dispose() {
        blockTexture.dispose();
        coin.disposeTexture();
        snakeRenderer.dispose();
        barrel.dispose();
        for(int i = 0; i < HEART_AMOUNT; i++) {
            Heart tempHeart = hearts[i];
            tempHeart.getImage().dispose();
        }
        shouldGoBack = false;
        gameHasStarted = true;
    }

    /**
     * renders the snakes head and body
     */
    private void renderTheSnake() {
        snakeRenderer.setColor(Snake.getColor());
        snakeRenderer.rect(snake.getXValueHead(), snake.getYValueHead(), Snake.BODY_PART_SIZE, Snake.BODY_PART_SIZE);
        for (Iterator<Rectangle> iter = snake.getBody().iterator(); iter.hasNext(); ) {
            Rectangle body = iter.next();
            snakeRenderer.rect(body.x, body.y, Snake.BODY_PART_SIZE, Snake.BODY_PART_SIZE);
        }
    }

    /**
     * draws all textures on the screen (under certain, specific conditions)
     */
    private void drawTheTextures() {
        game.batch.begin();

        game.batch.draw(blockTexture, BLOCK_X, BLOCK_Y);
        game.batch.draw(coin.getTexture(), coin.getXPosition(), coin.getYPosition());

        if(!barrel.checkExploded()) {
            game.batch.draw(barrel.getTexture(), barrel.getXPosition(), barrel.getYPosition());
        }

        if(Settings.isChristmasThemeOn()) {
            game.batch.draw(hat, snake.getXValueHead(),snake.getYValueHead() + Snake.BODY_PART_SIZE);
        }

        if(gameHasStarted) {
            // fill the HUD with everything needed for playing
            game.fontHUD.draw(game.batch, MainGame.myLangBundle.format("points", score), 5, TEXT_BEGIN_Y);
            game.fontHUD.draw(game.batch, MainGame.myLangBundle.get("lives"), MainGame.CAMERA_WIDTH - 700, TEXT_BEGIN_Y);
            for (int i = 0; i < HEART_AMOUNT; i++) {
                Heart tempHeart = hearts[i];
                game.batch.draw(tempHeart.getImage(), HEART_BEGIN_X + (i * 80), HEART_BEGIN_Y);
            }
        } else {
            game.fontHUD.draw(game.batch, MainGame.myLangBundle.get("gameOver"), 5, TEXT_BEGIN_Y);
            game.fontDescription.draw(game.batch, MainGame.myLangBundle.get("touch"), (MainGame.CAMERA_WIDTH / 2) + 250, TEXT_BEGIN_Y - 50);
        }

        game.batch.end();
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
        hasHitWall = false;
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
            case UpAndLeft:
            case UpAndRight:
            case DownAndLeft:
            case DownAndRight:
                snake.invertBothDirections();
                break;
        }
    }

    /**
     * checks if the snake collides with the outer walls
     */
    private void checkCollisionWithWall() {
        if (snake.getMovementInX() + Snake.BODY_PART_SIZE >= MainGame.CAMERA_WIDTH || snake.getMovementInX() <= 0) {
            snake.invertXDirection();
            hasHitWall = true;
        }

        if (snake.getMovementInY() + Snake.BODY_PART_SIZE >= MainGame.CAMERA_HEIGHT - SIZE_OF_HUD || snake.getMovementInY() <= 0) {
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
            game.memController.addLength(snake.ADD_WHEN_COLLECTED);
            score += coin_value;
            if(coin_value != coin.getNotRandomPoints()) {
                gainALive();
            } else {
                game.soundControl.playPointsSound();
            }
            randomizeCircleObject(coin.getSize(), true);
        }
    }

    /**
     * checks if the snake collides with itself
     * HACK: checks the time to keep the invincibility until the snake is completely unfolded
     */
    private void checkCollisionWithSnakeBody() {
        countInvincFrames++;
        if(!snake.checkSuicide()) {
            if(countInvincFrames > snake.BODY_PART_SIZE / SLOW_SPEED) {
                invincibilityOn = false;
            }
        } else {
            countInvincFrames = 0;
            if(Gdx.input.isTouched()) {
                invincibilityOn = true;
            } else {
                if(!invincibilityOn) {
                    looseALive(true);
                    invincibilityOn = true;
                }
            }
        }
    }

    /**
     * checks if the snake collides with one of the Obstacles
     */
    private void checkCollisionWithObstacle() {
        if(barrel.checkIfCanExplode(snake.getHeadAsRectangle())) {
            game.soundControl.playExplosionSound();
            barrel.explode(score);
            game.memController.addBarrel();
            looseALive(false);
            return;
        }
        if(barrel.checkExploded() && score - barrel.getPointsLastExplosion() >= barrel.POINTS_NEW_BARREL) {
            randomizeCircleObject(barrel.getSize(), false);
        }
    }

    /**
     * player looses a live and one heart gets unfilled
     */
    private void looseALive(boolean shouldPlaySound) {
        for(int i = 0; i < HEART_AMOUNT; i++) {
            Heart tempHeart = hearts[i];
            if(tempHeart.isFilled()) {
                tempHeart.emptyTheHeart();
                if(shouldPlaySound) {
                    game.soundControl.playLiveLoosingSound();
                }
                if(--lives == 0) {
                    looseTheGame();
                }
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
                game.soundControl.playLiveGainingSound();
                lives++;
                return;
            }
        }
    }

    /**
     * gets called when the player has lost his/her last live
     * returns the player back to the main menu
     */
    private void looseTheGame() {
        gameHasStarted = false;
        hasHitWall = true;
        countInvincFrames = 0;

        game.memController.addPlayedGame();
        game.memController.stopTimer();
        game.memController.saveStats();
    }

    /**
     * changes the screen when the games is over
     */
    public void changeTheScreen() {
        int placement = game.memController.checkHighscorePlacement(score);
        if (placement < 0){
            game.backToMainMenu(Level01.this);
        } else {
            game.setScreen(new NewHighscore(game, placement, score));
            dispose();
        }
    }

    /**
     * randomizes the position of either the coin or the barrel
     * @param sizeOfCircle size of the circle to randomize
     * @param isCoin true if a coin should be randomized
     */
    private void randomizeCircleObject(int sizeOfCircle, boolean isCoin) {
        int radius = sizeOfCircle / 2;
        int newX = rndGenerator.nextInt(game.CAMERA_WIDTH - (sizeOfCircle + QuadraticBlockHitBox.HIT_BOX_SIZE + radius));
        if(newX > BLOCK_X - radius - 1) {
            newX += QuadraticBlockHitBox.HIT_BOX_SIZE + sizeOfCircle + 1;
        } else if (newX < radius) {
            newX += radius;
        }
        int newY = rndGenerator.nextInt(game.CAMERA_HEIGHT - (sizeOfCircle + SIZE_OF_HUD + QuadraticBlockHitBox.HIT_BOX_SIZE + radius));
        if(newY > BLOCK_Y - radius - 1) {
            newY += QuadraticBlockHitBox.HIT_BOX_SIZE + sizeOfCircle + 1;
        } else if (newY < radius) {
            newY += radius;
        }

        if(isCoin) {
            coin.setPosition(newX, newY);
            coin_value = coin.setRandomTexture(rndGenerator.nextInt(100) + 1);
        } else {
            barrel.setPosition(newX, newY);
            barrel.setExplodedState(false);
        }
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