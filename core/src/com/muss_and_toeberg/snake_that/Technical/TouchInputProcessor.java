package com.muss_and_toeberg.snake_that.Technical;

import com.badlogic.gdx.InputProcessor;
import com.muss_and_toeberg.snake_that.Levels_And_Screens.*;

public class TouchInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        GameScreen.setDirectionVectDown();
        GameScreen.stopMovement = true;
		
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        GameScreen.setDirectionVectUp();
        GameScreen.stopMovement = false;
        GameScreen.hasHitWall = false;
		
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
