package com.muss_and_toeberg.snake_that.technical;

import com.badlogic.gdx.InputProcessor;
import com.muss_and_toeberg.snake_that.screens.levels.Level01;

/**
 * Implementation of the InputProcessor
 * makes it possible to react to the touching of the display
 */
public class TouchInputProcessor implements InputProcessor {
    /**
     * reacts to the user touching the display
     * @see InputProcessor#touchDown(int, int, int, int)
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
     * @see InputProcessor#touchUp(int, int, int, int)
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

    /**
     * NOT USED.
     * gets called when a key is pressed
     * @see InputProcessor#keyDown(int)
     * @param keycode key which was pressed
     * @return true if the event was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * NOT USED.
     * gets called when a key is released
     * @see InputProcessor#keyUp(int)
     * @param keycode key which was released
     * @return true if the event was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * NOT USED.
     * Called when a key was typed
     * @see InputProcessor#keyTyped(char)
     * @param character which was typed
     * @return true if the event was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * NOT USED.
     * Called when a finger or the mouse was dragged.
     * @see InputProcessor#touchDragged(int, int, int)
     * @param screenX position of the drag
     * @param screenY position of the drag
     * @param pointer event-pointer
     * @return true if the event was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * NOT USED.
     * Called when the mouse was moved without any buttons being pressed.
     * @see InputProcessor#mouseMoved(int, int)
     * @param screenX x-coordinate of the movement
     * @param screenY y-coordinate of the movement
     * @return true if the event was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * NOT USED.
     * Called when the mouse wheel was scrolled.
     * @see InputProcessor#scrolled(int)
     * @param amount scrolled amount
     * @return true if the event was processed
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
