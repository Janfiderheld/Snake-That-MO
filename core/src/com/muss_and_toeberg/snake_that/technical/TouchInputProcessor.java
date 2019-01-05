package com.muss_and_toeberg.snake_that.technical;

import com.badlogic.gdx.InputProcessor;
import com.muss_and_toeberg.snake_that.screens.levels.Level01;

/**
 * Implementation of the InputProcessor
 * makes it possible to react to the touching of the display
 * @author Jan-Philipp Töberg
 */
public class TouchInputProcessor implements InputProcessor {
    /**
     * reacts to the user touching the display
     * @param screenX x-position of the touch
     * @param screenY y-position of the touch
     * @param pointer indicates how many fingers touched
     * @param button not relevant for our usage
     * @return true if the event was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch(MainGame.currentMenu) {
            case MainMenu:
                break;
            case LevelSelection:
                break;
            case Level:
                if(Level01.gameHasStarted) {
                    Level01.setDirectionVectorDown();
                }
                break;
            case Settings:
                break;
            case Highscore:
                break;
            case Statistics:
                break;
            case None:
                break;
        }
        return true;
    }

    /**
     * reacts to the user lifting his/her finger(s) from the display
     * @param screenX x-position of the touch
     * @param screenY y-position of the touch
     * @param pointer indicates how many fingers touched
     * @param button not relevant for our usage
     * @return true if the event was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch(MainGame.currentMenu) {
            case MainMenu:
                break;
            case LevelSelection:
                break;
            case Level:
                if(Level01.gameHasStarted) {
                    Level01.setDirectionVectorUp();
                } else {
                    Level01.shouldGoBack = true;
                }
                break;
            case Settings:
                break;
            case Highscore:
                break;
            case Statistics:
                break;
            case None:
                break;
        }
        return true;
    }


    // irrelevant for our usage

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
