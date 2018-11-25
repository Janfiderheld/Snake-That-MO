package com.muss_and_toeberg.snake_that.Levels_And_Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.muss_and_toeberg.snake_that.Character_And_Obstacles.Levels_And_Screens.*;
import com.muss_and_toeberg.snake_that.Technical.*;

import java.util.Iterator;

// first Level of the Game
public class GameScreen extends ApplicationAdapter {
	// Objects to fill the screen with life
	private OrthographicCamera camera;
	private SpriteBatch batch;

	// Constant Values
	final int CAMERA_WIDTH = 1920;
	final int CAMERA_HEIGHT = 1080;
	final int MAX_VECTOR_LENGTH = 500;
	final int PICTURE_SIZE = 256;
	final int LINE_LENGTH = 10;

	// Constant Position of Waluigi
	final int WAAA_X = (CAMERA_WIDTH / 2) - (PICTURE_SIZE / 2);
	final int WAAA_Y = (CAMERA_HEIGHT / 2) - (PICTURE_SIZE / 2);

	// Obstacles and Player Character
    private static Snake snake = new Snake();
	private Texture waluigiImg;
	private WaluigiHitBox waluigi = new WaluigiHitBox(WAAA_X, WAAA_Y);

	// all Vectors (2D) which are used
	static Vector2 startTouchVector = new Vector2(0, 0);
	static Vector2 endTouchVector = new Vector2(0, 0);
	static Vector2 lineTouchVector = new Vector2(0, 0);

	// variables
	private static int speed = 10;
	public static boolean stopMovement = false;
    private static int snakeSize = snake.getSizeOfOneBlock();

	// creates the screen
	// this method is called once at the beginning of the lifecycle
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

		batch = new SpriteBatch();
		waluigiImg = new Texture("WaluigiBlock.png");

		TouchInputProcessor inputProcessor = new TouchInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		Gdx.gl.glClearColor(1, 0, 0, 1);

		snake.createSnake();
	}

	// renders the screen (= fills it with everything)
	// gets called in a constant loop
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(waluigiImg, WAAA_X, WAAA_Y);
		batch.end();

		// renders the Snake
		ShapeRenderer snakeRenderer = new ShapeRenderer();
		snakeRenderer.setProjectionMatrix(camera.combined);
		snakeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		snakeRenderer.setColor(0, 1, 1, 1);
		// Head
        snakeRenderer.rect(snake.getXValueHead(), snake.getYValueHead(), snakeSize, snakeSize);
		// Body
		for (Iterator<Rectangle> iter = snake.getBody().iterator(); iter.hasNext(); ){
			Rectangle body = iter.next();
			snakeRenderer.rect(body.x, body.y, snakeSize, snakeSize);
		}
		snakeRenderer.end();

		startTouchVector.x = snake.getMovementInX() + (snakeSize / 2);
		startTouchVector.y = snake.getMovementInY() + (snakeSize / 2);

		snake.moveSnakeBody();

		if(Gdx.input.isTouched()) {
			endTouchVector.x = CAMERA_WIDTH*Gdx.input.getX()/Gdx.graphics.getWidth();
			endTouchVector.y = CAMERA_HEIGHT*(Gdx.graphics.getHeight()-Gdx.input.getY())/Gdx.graphics.getHeight();

			if(startTouchVector.dst(endTouchVector) > MAX_VECTOR_LENGTH){
				Vector2 connectionVect = new Vector2(endTouchVector.x - startTouchVector.x, endTouchVector.y - startTouchVector.y);
				lineTouchVector.x = connectionVect.x * (MAX_VECTOR_LENGTH / connectionVect.len()) + startTouchVector.x;
				lineTouchVector.y = connectionVect.y * (MAX_VECTOR_LENGTH / connectionVect.len()) + startTouchVector.y;
			} else {
				lineTouchVector = endTouchVector;
			}

			ShapeRenderer renderer = new ShapeRenderer();
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeRenderer.ShapeType.Filled);
			renderer.setColor(0, 0, 1, 1);
			renderer.rectLine(startTouchVector, lineTouchVector, LINE_LENGTH);
			renderer.end();
		}

		checkCollisionWithWall();
		checkCollisionWithWaluigi();
		snake.moveWholeSnake();

		camera.update();
	}

	// disposes the textures as soon as the app is closed for good (aka end of Lifecycle)
	@Override
	public void dispose () {
		batch.dispose();
		waluigiImg.dispose();
	}

	// sets the direction vector to reduced speed as soon as the screen is touched
	public static void setDirectionVectDown(){
		speed = 2;
		snake.scaleDirection(speed);
	}

	// sets the direction vector to normal speed as soon as the touch is lifted
	public static void setDirectionVectUp(){
		speed = 10;
		Vector2 connectionVect = new Vector2(endTouchVector.x - startTouchVector.x, endTouchVector.y - startTouchVector.y);
		snake.scaleDirectionWithVector(speed, connectionVect);
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
		}

		if (snake.getMovementInY() + snakeSize >= CAMERA_HEIGHT || snake.getMovementInY() <= 0) {
            snake.invertYDirection();
		}
	}
}