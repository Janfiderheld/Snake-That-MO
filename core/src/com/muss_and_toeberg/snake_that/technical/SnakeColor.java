package com.muss_and_toeberg.snake_that.technical;

import com.badlogic.gdx.graphics.Color;

/**
 * represents the color of the snake
 * @author Niclas Muss
 */
public enum SnakeColor {
    Red,
    Green,
    Blue,
    Magenta,
    Yellow,
    Cyan;

    /**
     * takes an int and returns a resulting color
     * @param number a value which should be between 0 and 5
     * @return corresponding snake color
     */
    public static SnakeColor makeIntToSnakeColor(int number) {
        switch(number) {
            case 0:
                return Red;
            case 1:
                return Green;
            case 2:
                return Blue;
            case 3:
                return Magenta;
            case 4:
                return Yellow;
            case 5:
            default:
                return Cyan;
        }
    }

    /**
     * takes a snake color and returns the resulting number
     * @param color snake color
     * @return a number between 0 and 5
     */
    public static int makeSnakeColorToInt(SnakeColor color) {
        switch(color) {
            case Red:
                return 0;
            case Green:
                return 1;
            case Blue:
                return 2;
            case Magenta:
                return 3;
            case Yellow:
                return 4;
            case Cyan:
            default:
                return 5;
        }
    }

    /**
     * creates a color from the given snake Color
     * @param color snakeColor to make real
     * @return real, usable Color object
     */
    public static Color createColor(SnakeColor color) {
        switch (color) {
            case Red:
                return new Color(1, 0, 0, 1);
            case Green:
                return new Color(0, 1, 0, 1);
            case Blue:
                return new Color(0, 0, 1, 1);
            case Magenta:
                return new Color(1, 0, 1, 1);
            case Yellow:
                return new Color(1, 1, 0, 1);
            case Cyan:
            default:
                return new Color(0, 1, 1, 1);
        }
    }
}