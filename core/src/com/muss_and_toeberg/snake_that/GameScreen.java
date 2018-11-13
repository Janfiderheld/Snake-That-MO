package com.muss_and_toeberg.snake_that;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GameScreen extends ApplicationAdapter {
	//Gradle is gay
	private OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	static int x = 420;
	static int y = 69;
	final int WIDTH = 1920;
	final int HEIGHT = 1080;
	boolean oneDirection = true;
	boolean twoDirection = true;
	static Vector3 startVector = new Vector3(0,0,0);
	static Vector3 endVector = new Vector3(0,0,0);

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		TouchInputProcessor inputProcessor = new TouchInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		if(Gdx.input.isTouched()) {
		    endVector.x = Gdx.input.getX();
		    endVector.y = Gdx.input.getY();


			ShapeRenderer renderer = new ShapeRenderer();
			renderer.begin(ShapeRenderer.ShapeType.Line);
			renderer.setColor(0,1,0,1);
			renderer.line(startVector, endVector);
			renderer.end();

			camera.unproject(startVector);
			camera.unproject(endVector);
		}


		batch.begin();
		batch.draw(img, x, y);
		batch.end();


		if(x+256 == WIDTH){
			oneDirection = false;
		}
		else if(x == 0){
			oneDirection = true;
		}

		if(y+256 == HEIGHT){
			twoDirection = false;
		}
		else if(y == 0){
			twoDirection = true;
		}

		if (oneDirection){
			x++;
		} else {
			x--;
		}

		if (twoDirection){
			y++;
		} else {
			y--;
		}
		camera.update();
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public static int getX(){
		return x;
	}

	public static int getY(){
		return y;
	}

	public static void setStartVector(Vector3 vector){
		startVector = vector;
	}

	public static void setEndVector(Vector3 vector){
		endVector = vector;
	}
}
