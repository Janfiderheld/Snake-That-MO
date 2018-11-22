package com.muss_and_toeberg.snake_that;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import static com.muss_and_toeberg.snake_that.HitDirection.*;

// first Level of the Game
public class GameScreen extends ApplicationAdapter {
	// Objects to fill the screen with life
	private OrthographicCamera camera;
	private SpriteBatch batch;

	// Constant Values
	final int WIDTH = 1920;
	final int HEIGHT = 1080;
	final int MAX_VECTOR_LENGTH = 500;
	final int PICTURE_SIZE = 256;
	final int LINE_LENGTH = 10;
	
	// Constant Position of Waluigi
	final int WAAA_X = (WIDTH / 2) - (PICTURE_SIZE / 2);
	final int WAAA_Y = (HEIGHT / 2) - (PICTURE_SIZE / 2);

	// Textures and Hitboxes for the character and obstacles
	private Texture snakeImg;
	private Texture waluigiImg;
	private WaluigiHitBox waluigi = new WaluigiHitBox(WAAA_X, WAAA_Y);
	private Rectangle snakeHitBox;

	// all Vectors (2D) which are used
	static Vector2 startTouchVector = new Vector2(0, 0);
	static Vector2 endTouchVector = new Vector2(0, 0);
	static Vector2 lineTouchVector = new Vector2(0, 0);
	static Vector2 snakeDirection = new Vector2(1, 1);
	static Vector2 snakeMovement = new Vector2(1, 1);

	// variables
	static int speed = 10;
	static boolean stopMovement = false;

	// creates the screen
	// this method is called once at the beginning of the lifecycle
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		
		batch = new SpriteBatch();
		snakeImg = new Texture("badlogic.jpg");
		waluigiImg = new Texture("WaluigiBlock.png");
		
		TouchInputProcessor inputProcessor = new TouchInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		Gdx.gl.glClearColor(1, 0, 0, 1);

		snakeHitBox = createNewSnake();
	}

	// renders the screen (= fills it with everything)
	// gets called in a constant loop
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(snakeImg, snakeMovement.x, snakeMovement.y);
        batch.draw(waluigiImg, WAAA_X, WAAA_Y);
        batch.end();

        startTouchVector.x = snakeMovement.x + (PICTURE_SIZE / 2);
        startTouchVector.y = snakeMovement.y + (PICTURE_SIZE / 2);

		snakeHitBox.x = snakeMovement.x;
		snakeHitBox.y = snakeMovement.y;

		if(Gdx.input.isTouched()) {
		    endTouchVector.x = Gdx.input.getX();
		    endTouchVector.y = HEIGHT - Gdx.input.getY();

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
		snakeMovement.add(snakeDirection);

		camera.update();
	}

	// disposes the textures as soon as the app is closed for good (aka end of Lifecycle)
	@Override
	public void dispose () {
		batch.dispose();
		snakeImg.dispose();
		waluigiImg.dispose();
	}

	// sets the direction vector to reduced speed as soon as the screen is touched
	public static void setDirectionVectDown(){
		speed = 2;
		snakeDirection.x = snakeDirection.x * (speed / snakeDirection.len());
		snakeDirection.y = snakeDirection.y * (speed / snakeDirection.len());
	}

	// sets the direction vector to normal speed as soon as the touch is lifted
	public static void setDirectionVectUp(){
		speed = 10;
		Vector2 connectionVect = new Vector2(endTouchVector.x - startTouchVector.x, endTouchVector.y - startTouchVector.y);
		snakeDirection.x = connectionVect.x * (speed / connectionVect.len());
		snakeDirection.y = connectionVect.y * (speed / connectionVect.len());
		snakeDirection.rotate(180);
	}

	// checks if the snake collides with something (currently only waluigi)
	private void checkCollisionWithWaluigi() {
		HitDirection side = waluigi.checkWhichCollisionSide(snakeHitBox);
		
		switch(side) {
		case LeftSide:
		case RightSide:
			snakeDirection.x *= (-1);
			break;
		case Upwards:
		case Downwards:
			snakeDirection.y *= (-1);
			break;
		}
	}

	// checks if the snake collides with the wall
	private void checkCollisionWithWall() {
        if (snakeMovement.x + PICTURE_SIZE >= WIDTH || snakeMovement.x <= 0) {
            snakeDirection.x *= -1;
        }

        if (snakeMovement.y + PICTURE_SIZE >= HEIGHT || snakeMovement.y <= 0) {
            snakeDirection.y *= -1;
        }
    }
	
	// create a new Hitbox for the snake
	private Rectangle createNewSnake() {
		Rectangle hitBox = new Rectangle();
		hitBox.width = PICTURE_SIZE;
		hitBox.height = PICTURE_SIZE;
		
		return hitBox;
	}
}
