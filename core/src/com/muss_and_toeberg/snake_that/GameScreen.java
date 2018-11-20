package com.muss_and_toeberg.snake_that;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ApplicationAdapter {
	//Gradle is gay
	private OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	Texture waa;
	final int WIDTH = 1920;
	final int HEIGHT = 1080;
	final int MAX_LENGTH = 500;
	static int geschwindigkeit = 10;

	private Rectangle waluigi;
	private Rectangle snake;

	static Vector2 startVector = new Vector2(0,0);
	static Vector2 endVector = new Vector2(0,0);
	static Vector2 endVector_final = new Vector2(0,0);
	static Vector2 direction = new Vector2(1,1);
	static Vector2 image = new Vector2(1,1);

	static boolean stopMovement = false;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		waa = new Texture("WaluigiBlock.png");
		TouchInputProcessor inputProcessor = new TouchInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		Gdx.gl.glClearColor(1, 0, 0, 1);

		waluigi = new Rectangle();
		waluigi.x = 1920/2-128;
		waluigi.y = 1080/2-128;
		waluigi.width = 256;
		waluigi.height = 256;

		snake = new Rectangle();
		snake.width = 256;
		snake.height = 256;
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(img, image.x, image.y);
        batch.draw(waa, 1920/2-128, 1080/2-128);
        batch.end();

        startVector.x = image.x + 128;
        startVector.y = image.y + 128;

		snake.x = image.x;
		snake.y = image.y;

		if(Gdx.input.isTouched()) {
		    endVector.x = Gdx.input.getX();
		    endVector.y = 1080 - Gdx.input.getY();

		    if(startVector.dst(endVector)>MAX_LENGTH){
		    	Vector2 connectionVect = new Vector2(endVector.x - startVector.x, endVector.y - startVector.y);
		    	endVector_final.x = connectionVect.x * (MAX_LENGTH / connectionVect.len()) + startVector.x;
		    	endVector_final.y = connectionVect.y * (MAX_LENGTH / connectionVect.len()) + startVector.y;
			}else{
		    	endVector_final = endVector;
			}

			ShapeRenderer renderer = new ShapeRenderer();
			renderer.setProjectionMatrix(camera.combined);
			renderer.begin(ShapeRenderer.ShapeType.Filled);
			renderer.setColor(0,0,1,1);
			renderer.rectLine(startVector, endVector_final,5);
			renderer.end();
		}

		if (image.x + 256 >= WIDTH || image.x <= 0) {
			direction.x = -1*direction.x;
		}

		if (image.y + 256 >= HEIGHT || image.y <= 0) {
			direction.y = -1*direction.y;
		}

		checkcollision();

		image.add(direction);

		camera.update();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		waa.dispose();
	}

	public static float getX(){
		return image.x;
	}

	public static float getY(){
		return image.y;
	}

	public static void setStartVector(){
		// startVector.x = getX()+128;
		// startVector.y = getY()+128;

		geschwindigkeit = 2;
		direction.x = direction.x * (geschwindigkeit / direction.len());
		direction.y = direction.y * (geschwindigkeit / direction.len());
	}

	public static void setDirectionVector(){
		geschwindigkeit = 10;
		Vector2 connectionVect = new Vector2(endVector.x - startVector.x, endVector.y - startVector.y);
		direction.x = connectionVect.x * (geschwindigkeit / connectionVect.len());
		direction.y = connectionVect.y * (geschwindigkeit / connectionVect.len());
		direction.rotate(180);
	}

	public void checkcollision(){
		if(!snake.overlaps(waluigi)) {
			return;
		}

		if(snake.x + 256 >= waluigi.x) {
			direction.x *= (-1);
		}

		if(snake.y + 256 >= waluigi.y) {
			direction.y *= (-1);
		}
	}

}
