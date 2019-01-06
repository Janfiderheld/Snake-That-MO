package com.muss_and_toeberg.snake_that.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.muss_and_toeberg.snake_that.game_objects.Coin;
import com.muss_and_toeberg.snake_that.game_objects.Heart;
import com.muss_and_toeberg.snake_that.game_objects.Snake;
import com.muss_and_toeberg.snake_that.game_objects.obstacles.ExplodingBarrel;
import com.muss_and_toeberg.snake_that.game_objects.obstacles.Portal;
import com.muss_and_toeberg.snake_that.game_objects.
        obstacles.QuadraticBlockHitBox;
import com.muss_and_toeberg.snake_that.technical.MainGame;
import com.muss_and_toeberg.snake_that.screens.NewHighscore;
import com.muss_and_toeberg.snake_that.screens.Settings;
import com.muss_and_toeberg.snake_that.technical.HitDirection;
import com.muss_and_toeberg.snake_that.technical.Menu;
import com.muss_and_toeberg.snake_that.technical.TouchInputProcessor;

import java.util.Random;

/**
 * First Level of the game
 * @author Jan-Philipp Töberg
 */
public class Level01 implements Screen {
    // Constant width & height values
    private final int SIZE_OF_HUD = 150;
    private final int BLOCK_X = (MainGame.CAMERA_WIDTH / 2) -
            (QuadraticBlockHitBox.HIT_BOX_SIZE / 2);
    private final int BLOCK_Y = ((MainGame.CAMERA_HEIGHT - SIZE_OF_HUD) / 2) -
            (QuadraticBlockHitBox.HIT_BOX_SIZE / 2);
    private final int HEART_AMOUNT = 3;

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
    private ExplodingBarrel barrel = new ExplodingBarrel(0, 0);
    private Portal portalUpperLeft = new Portal(125, 680);
    private Portal portalBottomRight = new Portal(1700, 120);

    // Obstacles and Player Character
    private static Snake snake = new Snake();
    private Coin coin = new Coin();
    private Texture blockTexture;
    private Texture hat;
    private QuadraticBlockHitBox block =
            new QuadraticBlockHitBox(BLOCK_X, BLOCK_Y);

    // all Vectors (2D) which are used
    private static Vector2 startTouchVector = new Vector2(0, 0);
    private static Vector2 endTouchVector = new Vector2(0, 0);
    private static Vector2 lineTouchVector = new Vector2(0, 0);

    // local variables & objects
    private Random rndGenerator = new Random();
    private int lives = HEART_AMOUNT;
    private int score = 0;
    private boolean invincibilityOn = true;
    private float countInvincibilityFrames = 0;
    private boolean shouldDrawLine = false;

    // class variables
    public static boolean hasHitWall = true;
    public static boolean shouldGoBack = false;
    public static boolean gameHasStarted = true;
    private static float speed = NORMAL_SPEED;
    private static int coinValue = 0;

    /**
     * Constructor which is used to create all objects that only need
     * to be created once
     * @param game game object which allows screen changing
     */
    public Level01(final MainGame game){
        this.game = game;

        TouchInputProcessor inputProcessor = new TouchInputProcessor();
        Gdx.input.setInputProcessor(inputProcessor);

        snakeRenderer = new ShapeRenderer();
        snake.createSnake(new Vector2(SLOW_SPEED,SLOW_SPEED),
                new Vector2(300, 300));
        portalUpperLeft.setCorrespondingPortal(portalBottomRight);
        portalBottomRight.setCorrespondingPortal(portalUpperLeft);

        for(int count = 0; count < HEART_AMOUNT; count++) {
            hearts[count] = new Heart();
        }

        blockTexture = new Texture("textures/Brick.png");
        background = new Texture ("textures/backgroundPipes.png");
        hat = new Texture("textures/SantaHat.png");

        randomizeCircleObject(true);
        randomizeCircleObject(false);
        game.memController.startTimer();
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
        drawBackground();

        if(gameHasStarted) {
            startTouchVector.x = snake.getMovementInX() +
                    (Snake.BODY_PART_SIZE / 2);
            startTouchVector.y = snake.getMovementInY() +
                    (Snake.BODY_PART_SIZE / 2);

            snake.moveSnakeBody();
            checkAllCollisions();
            snake.increaseMovementVector();

            // checks if the screen is currently touched
            // --> can the snake be directed?
            if (Gdx.input.isTouched() && hasHitWall) {
                endTouchVector.x = MainGame.CAMERA_WIDTH * Gdx.input.getX()
                        / Gdx.graphics.getWidth();
                endTouchVector.y = MainGame.CAMERA_HEIGHT *
                        (Gdx.graphics.getHeight() - Gdx.input.getY())
                        / Gdx.graphics.getHeight();

                if (startTouchVector.dst(endTouchVector) > MAX_VECTOR_LENGTH) {
                    Vector2 connectionVector = new Vector2(endTouchVector.x
                            - startTouchVector.x,
                            endTouchVector.y - startTouchVector.y);
                    lineTouchVector.x = connectionVector.x *
                            (MAX_VECTOR_LENGTH / connectionVector.len())
                            + startTouchVector.x;
                    lineTouchVector.y = connectionVector.y *
                            (MAX_VECTOR_LENGTH / connectionVector.len())
                            + startTouchVector.y;
                } else {
                    lineTouchVector = endTouchVector;
                }
                shouldDrawLine = true;
            } else {
                shouldDrawLine = false;
            }
        }

        // renders & draws everything visible
        renderTheSnake();
        renderTheHUD();
        drawTheTextures();
        renderTheLine();

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
        for(int count = 0; count < HEART_AMOUNT; count++) {
            hearts[count].getImage().dispose();
        }
        shouldGoBack = false;
        gameHasStarted = true;
    }

    /**
     * renders the snakes head and body
     */
    private void renderTheSnake() {
        snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        snakeRenderer.setColor(Snake.getColor());
        snakeRenderer.rect(snake.getXValueHead(), snake.getYValueHead(),
                Snake.BODY_PART_SIZE, Snake.BODY_PART_SIZE);
        for (int count = 0; count < snake.getBody().size; count++) {
            Rectangle body = snake.getBody().get(count);
            snakeRenderer.rect(body.x, body.y, Snake.BODY_PART_SIZE,
                    Snake.BODY_PART_SIZE);
        }
        snakeRenderer.end();
    }

    /**
     * renders the HUD
     */
    private void renderTheHUD() {
        snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        snakeRenderer.rect(0, MainGame.CAMERA_HEIGHT - SIZE_OF_HUD,
                MainGame.CAMERA_WIDTH, SIZE_OF_HUD, Color.DARK_GRAY,
                Color.DARK_GRAY, Color.DARK_GRAY, Color.DARK_GRAY);
        snakeRenderer.end();
    }

    /**
     * renders the Line to show the direction
     */
    private void renderTheLine() {
        if(!shouldDrawLine) {
            return;
        }

        snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        snakeRenderer.setColor(Color.BLUE);
        snakeRenderer.rectLine(startTouchVector, lineTouchVector, LINE_LENGTH);
        snakeRenderer.end();
    }

    /**
     * draws the background image
     */
    private void drawBackground() {
        game.batch.begin();
        game.batch.draw(background, 0, 0);
        game.batch.end();
    }

    /**
     * draws all textures on the screen (under certain, specific conditions)
     */
    private void drawTheTextures() {
        game.batch.begin();

        game.batch.draw(blockTexture, BLOCK_X, BLOCK_Y);
        game.batch.draw(portalUpperLeft.getTexture(),
                portalUpperLeft.getXPosition(), portalUpperLeft.getYPosition());
        game.batch.draw(portalBottomRight.getTexture(),
                portalBottomRight.getXPosition(),
                portalBottomRight.getYPosition());
        game.batch.draw(coin.getTexture(), coin.getXPosition(),
                coin.getYPosition());

        if(!barrel.checkExploded()) {
            game.batch.draw(barrel.getTexture(), barrel.getXPosition(),
                    barrel.getYPosition());
        }

        if(Settings.checkForChristmas()) {
            game.batch.draw(hat, snake.getXValueHead(),
                    snake.getYValueHead() + Snake.BODY_PART_SIZE);
        }


        int beginTextInY = MainGame.CAMERA_HEIGHT - 15;
        if(gameHasStarted) {
            // fill the HUD with everything needed for playing
            game.fontHUD.draw(game.batch,
                    MainGame.myLangBundle.format("points", score),
                    5, beginTextInY);
            game.fontHUD.draw(game.batch, MainGame.myLangBundle.get("lives"),
                    MainGame.CAMERA_WIDTH - 700, beginTextInY);
            int beginHeartInX = MainGame.CAMERA_WIDTH - 250;
            for (int count = 0; count < HEART_AMOUNT; count++) {
                game.batch.draw(hearts[count].getImage(),
                        beginHeartInX + (count * 80),
                        MainGame.CAMERA_HEIGHT - 100);
            }
        } else {
            // fill the HUD with the messages when the game is lost
            game.fontHUD.draw(game.batch, MainGame.myLangBundle.get("gameOver"),
                    5, beginTextInY);
            game.fontDescription.draw(game.batch,
                    MainGame.myLangBundle.get("touch"),
                    (MainGame.CAMERA_WIDTH / 2) + 250, beginTextInY - 50);
        }

        game.batch.end();
    }

    /**
     * changes the speed of the snake to reduced speed
     * as soon as the screen is touched
     */
    public static void setDirectionVectorDown(){
        if (!hasHitWall) {
            return;
        }

        speed = SLOW_SPEED;
        snake.scaleDirection(speed);
    }

    /**
     * changes the speed of the snake to normal speed
     * as soon as the touch is lifted
     */
    public static void setDirectionVectorUp(){
        if (!hasHitWall) {
            return;
        }

        speed = NORMAL_SPEED;
        Vector2 connectionVector = new Vector2(endTouchVector.x -
                startTouchVector.x, endTouchVector.y - startTouchVector.y);
        snake.setDirectionToScaledVector(speed, connectionVector);
        hasHitWall = false;
    }

    /**
     * checks for all possible collisions
     */
    private void checkAllCollisions() {
        checkCollisionWithWall();
        checkCollisionWithBlock();
        checkCollisionWithCoin();
        checkCollisionWithSnakeBody();
        checkCollisionWithBarrel();
        checkCollisionWithPortals();
    }

    /**
     * checks if the snake collides with the block in the middle
     */
    private void checkCollisionWithBlock() {
        HitDirection side =
                block.checkWhichCollisionSide(snake.getHeadAsRectangle());

        switch(side) {
            case NoHit:
                return;
            case Left:
            case Right:
                snake.invertXDirection();
                break;
            case Up:
            case Down:
                snake.invertYDirection();
                break;
            case UpAndLeft:
            case UpAndRight:
            case DownAndLeft:
            case DownAndRight:
                snake.invertBothDirections();
                break;
        }
         hasHitWall = true;
    }

    /**
     * checks if the snake collides with the outer walls
     */
    private void checkCollisionWithWall() {
        if (snake.getMovementInX() + Snake.BODY_PART_SIZE >=
                MainGame.CAMERA_WIDTH || snake.getMovementInX() <= 0) {
            snake.invertXDirection();
            hasHitWall = true;
        }

        if (snake.getMovementInY() + Snake.BODY_PART_SIZE >=
                MainGame.CAMERA_HEIGHT - SIZE_OF_HUD
                || snake.getMovementInY() <= 0) {
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
            score += coinValue;
            if(coinValue != coin.getMaxPoints()) {
                gainALive();
            } else {
                game.soundControl.playPointsSound();
            }
            randomizeCircleObject(true);
        }
    }

    /**
     * checks if the snake collides with itself by counting frames
     * / amount of renders
     */
    private void checkCollisionWithSnakeBody() {
        countInvincibilityFrames++;
        if(!snake.checkSuicide()) {
            if(countInvincibilityFrames > Snake.BODY_PART_SIZE / SLOW_SPEED) {
                invincibilityOn = false;
            }
        } else {
            countInvincibilityFrames = 0;
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
     * checks if the snake hits one of the portals
     */
    private void checkCollisionWithPortals() {
        if(!portalBottomRight.checkSnakeHitPortal(snake)) {
            portalUpperLeft.checkSnakeHitPortal(snake);
        }
    }

    /**
     * checks if the snake collides with the exploding barrel
     */
    private void checkCollisionWithBarrel() {
        if(barrel.checkCanExplode(snake.getHeadAsRectangle())) {
            game.soundControl.playExplosionSound();
            barrel.explode(score);
            game.memController.addBarrel();
            looseALive(false);
            return;
        }

        if(barrel.checkExploded() && score - barrel.getPointsLastExplosion()
                >= barrel.POINTS_NEW_BARREL) {
            randomizeCircleObject(false);
        }
    }

    /**
     * player looses a live and one heart gets unfilled
     */
    private void looseALive(boolean shouldPlaySound) {
        for(int count = 0; count < HEART_AMOUNT; count++) {
            if(hearts[count].checkIfFilled()) {
                hearts[count].emptyTheHeart();
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
        for(int count = HEART_AMOUNT - 1; count >= 0; count--) {
            if(!hearts[count].checkIfFilled()) {
                hearts[count].fillTheHeart();
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
        countInvincibilityFrames = 0;

        game.memController.addPlayedGame();
        game.memController.stopTimer();
        game.memController.saveStats();
    }

    /**
     * changes the screen when the games is over
     */
    private void changeTheScreen() {
        int placement = game.memController.checkHighscorePlacement(score);
        if (placement < 0){
            game.goBackToMainMenu(Level01.this);
        } else {
            game.setScreen(new NewHighscore(game, placement, score));
            dispose();
        }
    }

    /**
     * creates new random values for the x and the y-axis
     * @param sizeOfCircle size of the circle object
     * @return array containing at index 0 the x and at index 1 the y-value
     */
    private int[] createRandomValues(int sizeOfCircle) {
        int radius = sizeOfCircle / 2;
        int newX = rndGenerator.nextInt(MainGame.CAMERA_WIDTH -
                (sizeOfCircle + QuadraticBlockHitBox.HIT_BOX_SIZE + radius));
        if(newX > BLOCK_X - radius - 1) {
            newX += QuadraticBlockHitBox.HIT_BOX_SIZE + sizeOfCircle + 1;
        } else if (newX < radius) {
            newX += radius;
        }

        int newY = rndGenerator.nextInt(MainGame.CAMERA_HEIGHT -
                (SIZE_OF_HUD + sizeOfCircle + QuadraticBlockHitBox.HIT_BOX_SIZE
                        + radius));
        if(newY > BLOCK_Y - radius - 1) {
            newY += QuadraticBlockHitBox.HIT_BOX_SIZE + sizeOfCircle + 1;
        } else if (newY < radius) {
            newY += radius;
        }

        return new int[]{newX, newY};
    }

    /**
     * randomizes a circle object (barrel or coin) until it is not
     * inside one of the portals
     * @param isCoin true if the object to randomized is a coin
     */
    private void randomizeCircleObject(boolean isCoin) {
        boolean isFinished = false;
        Circle tempHitBox;
        int[] rndValues;

        if(isCoin) {
            tempHitBox = coin.getHitBox();
        } else {
            tempHitBox = barrel.getHitBox();
        }

        while(!isFinished) {
            rndValues = createRandomValues(Math.round(tempHitBox.radius * 2));
            tempHitBox.setPosition(rndValues[0], rndValues[1]);
            if(!tempHitBox.overlaps(portalBottomRight.getHitBox()) &&
                    !tempHitBox.overlaps(portalUpperLeft.getHitBox())) {
                isFinished = true;
            }
        }

        if(isCoin) {
            coin.setHitBox(tempHitBox);
            coinValue = coin.setRandomTexture(rndGenerator.nextInt(100) + 1);
        } else {
            barrel.setHitBox(tempHitBox);
            barrel.setExplodedState(false);
        }
    }

    /**
     * NOT USED.
     * Called when this screen becomes the current screen for a Game.
     * --> everything is done in the constructor.
     * @see Screen#show() ()
     */
    @Override
    public void show() {

    }

    /**
     * NOT USED.
     * would be called when the screen gets resized
     * @param width new width of the screen
     * @param height new height of the screen
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * NOT USED.
     * Called when the Application is paused, usually when it's not active
     * or visible on screen.
     * @see Screen#pause()
     */
    @Override
    public void pause() {

    }

    /**
     * NOT USED.
     * Called when the Application is resumed from a paused state,
     * usually when it regains focus.
     * @see Screen#resume() ()
     */
    @Override
    public void resume() {

    }

    /**
     * NOT USED.
     * Called when this screen is no longer the current screen for a Game.
     * --> we dispose everything when its not used.
     * @see Screen#hide()
     */
    @Override
    public void hide() {

    }
}