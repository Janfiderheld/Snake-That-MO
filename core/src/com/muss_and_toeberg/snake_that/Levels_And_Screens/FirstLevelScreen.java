package com.muss_and_toeberg.snake_that.Levels_And_Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import com.muss_and_toeberg.snake_that.Character_And_Obstacles.*;
import com.muss_and_toeberg.snake_that.Technical.*;

import java.util.Iterator;
import java.util.Random;

// first Level of the Game
public class FirstLevelScreen extends ApplicationAdapter {
	// Objects to fill the screen with life
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private ShapeRenderer snakeRenderer;
	private Random rndGenerator = new Random();
	private BitmapFont font;
	private Texture heart1;
	private Texture heart2;
	private Texture heart3;

	// Constant Values
	final int CAMERA_WIDTH = 1920;
	final int CAMERA_HEIGHT = 1080;
	final int MAX_VECTOR_LENGTH = 500;
	final int PICTURE_SIZE = 256;
	final int LINE_LENGTH = 10;
	final int SIZE_OF_HUD = 150;
	final static float NORMAL_SPEED = 10;
	final static float SLOW_SPEED = 2;

	// Constant Position of Waluigi
	final int WAAA_X = (CAMERA_WIDTH / 2) - (PICTURE_SIZE / 2);
	final int WAAA_Y = (CAMERA_HEIGHT / 2) - (PICTURE_SIZE / 2);

	// Obstacles and Player Character
    private static Snake snake = new Snake();
    private Coin coin = new Coin();
	private Texture waluigiImg;
	private WaluigiHitBox waluigi = new WaluigiHitBox(WAAA_X, WAAA_Y);

	// all Vectors (2D) which are used
	static Vector2 startTouchVector = new Vector2(0, 0);
	static Vector2 endTouchVector = new Vector2(0, 0);
	static Vector2 lineTouchVector = new Vector2(0, 0);

	// variables
	private static float speed = 10;
	public static boolean stopMovement = false;
    private static int snakeSize = snake.getSizeOfOneBlock();
    public static boolean hasHitWall = true;

	// creates the screen
	// this method is called once at the beginning of the lifecycle
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

		batch = new SpriteBatch();
		TouchInputProcessor inputProcessor = new TouchInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);

		snakeRenderer = new ShapeRenderer();
		snake.createSnake();

		heart1 = new Texture("Heart_filled.png");
		heart2 = new Texture("Heart_filled.png");
		heart3 = new Texture("Heart_unfilled.png");

		waluigiImg = new Texture("WaluigiBlock.png");
		coin.setNFCTexture(new Texture("NFC.png"));
		coin.setBitCoinTexture(new Texture("Bitcoin.png"));
		randomizeNewCoin();

		Gdx.gl.glClearColor(1, 0, 0, 1);

		font = new BitmapFont (Gdx.files.internal("Comic_Sans.fnt"));
	}

	// renders the screen (= fills it with everything)
	// gets called in a constant loop
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		// renders the Snake
		snakeRenderer.setProjectionMatrix(camera.combined);
		snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		snakeRenderer.setColor(0, 1, 1, 1);
		// Head
		snakeRenderer.rect(snake.getXValueHead(), snake.getYValueHead(), snakeSize, snakeSize);
		// Body
		for (Iterator<Rectangle> iter = snake.getBody().iterator(); iter.hasNext(); ) {
			Rectangle body = iter.next();
			snakeRenderer.rect(body.x, body.y, snakeSize, snakeSize);
		}

		snakeRenderer.rect(0,CAMERA_HEIGHT - SIZE_OF_HUD,CAMERA_WIDTH, SIZE_OF_HUD, Color.BLACK,Color.OLIVE,Color.GOLD,Color.BROWN);
		snakeRenderer.end();

		//renders the Texture Objects
		batch.begin();
		batch.draw(heart1,CAMERA_WIDTH-700+450,CAMERA_HEIGHT-100);
		batch.draw(heart2,CAMERA_WIDTH-700+530,CAMERA_HEIGHT-100);
		batch.draw(heart3,CAMERA_WIDTH-700+610,CAMERA_HEIGHT-100);
		batch.draw(waluigiImg, WAAA_X, WAAA_Y);
		batch.draw(coin.getTexture(), coin.getXPosition(), coin.getYPosition());
		font.draw(batch, "Punkte: 1000" , 5, CAMERA_HEIGHT);
		font.draw(batch, "Leben:",CAMERA_WIDTH-700,CAMERA_HEIGHT);
		batch.end();

		startTouchVector.x = snake.getMovementInX() + (snakeSize / 2);
		startTouchVector.y = snake.getMovementInY() + (snakeSize / 2);

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

			snakeRenderer.setProjectionMatrix(camera.combined);
			snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			snakeRenderer.setColor(0, 0, 1, 1);
			snakeRenderer.rectLine(startTouchVector, lineTouchVector, LINE_LENGTH);
			snakeRenderer.end();
		}

		// checks the different possible collisions
		checkCollisionWithWall();
		checkCollisionWithWaluigi();
		checkCollisionWithCoin();

		snake.moveWholeSnake();
		camera.update();
	}

	// disposes the textures as soon as the app is closed for good (aka end of Lifecycle)
	@Override
	public void dispose () {
		batch.dispose();
		waluigiImg.dispose();
		coin.getTexture().dispose();
		snakeRenderer.dispose();
	}

	// sets the direction vector to reduced speed as soon as the screen is touched
	public static void setDirectionVectDown(){
		if (!hasHitWall) {
			return;
		}

		speed = SLOW_SPEED;
		snake.scaleDirection(speed);
	}

	// sets the direction vector to normal speed as soon as the touch is lifted
	public static void setDirectionVectUp(){
		if (!hasHitWall) {
			return;
		}

		speed = NORMAL_SPEED;
		Vector2 connectionVector = new Vector2(endTouchVector.x - startTouchVector.x, endTouchVector.y - startTouchVector.y);
		snake.scaleDirectionWithVector(speed, connectionVector);

	}

	// checks if the snake collides with something (currently only waluigi)
	private void checkCollisionWithWaluigi() {
		HitDirection side = waluigi.checkWhichCollisionSide(snake.getHeadAsRectangle());

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

	// checks if the snake collides with the wall
	private void checkCollisionWithWall() {
		if (snake.getMovementInX() + snakeSize >= CAMERA_WIDTH || snake.getMovementInX() <= 0) {
			snake.invertXDirection();
			hasHitWall = true;
		}

		if (snake.getMovementInY() + snakeSize >= CAMERA_HEIGHT - SIZE_OF_HUD || snake.getMovementInY() <= 0) {
			snake.invertYDirection();
			hasHitWall = true;
		}
	}

	// checks if the snake collides a coin
	private void checkCollisionWithCoin() {
		if (Intersector.overlaps(coin.getHitBox(), snake.getHeadAsRectangle())) {
			snake.addNewBodyPart();
			randomizeNewCoin();
		}
	}

	// changes the x and y coordinates of the coin at a random place
	// (which is not inside Waluigi and not to close to the snake head)
	private void randomizeNewCoin() {
        int coinX = rndGenerator.nextInt(CAMERA_WIDTH - (coin.getSize() + PICTURE_SIZE + snakeSize * 2));
        if(coinX >= WAAA_X - coin.getSize()) {
        	coinX += PICTURE_SIZE;
		}
		if(coinX >= snake.getXValueHead() - (snakeSize / 2 + coin.getSize() )) {
        	coinX += 2 * snakeSize;
		}

        int coinY = rndGenerator.nextInt(CAMERA_HEIGHT- SIZE_OF_HUD - (coin.getSize() + PICTURE_SIZE + snakeSize * 2));
        if(coinY > WAAA_Y - coin.getSize()) {
        	coinY += PICTURE_SIZE;
		}
		if(coinY > snake.getYValueHead() - (snakeSize / 2 + coin.getSize() )) {
			coinY += 2 * snakeSize;
		}

		int rndTexture = rndGenerator.nextInt(100);

		coin.setXPosition(coinX);
        coin.setYPosition(coinY);
        coin.setRandomTexture(rndTexture);
    }
}