package com.muss_and_toeberg.snake_that.game_objects.obstacles;

import com.badlogic.gdx.math.Rectangle;
import com.muss_and_toeberg.snake_that.technical.HitDirection;

/**
 * describes the hitBox for the block as four small hitBoxes as his outlines
 * @author Jan-Philipp Töberg
 */
public class QuadraticBlockHitBox {
	// constant values
	public final static int HIT_BOX_SIZE = 256;
	private final int NO_OF_SIDES = 4;
	private final int HIT_BOX_THICKNESS = 1;

	// indexes for the four sides
	private final int INDEX_RIGHT = 0;
	private final int INDEX_LEFT = 1;
	private final int INDEX_UP = 2;
	private final int INDEX_DOWN = 3;

	// the four hitBoxes as an array
	private Rectangle[] fourSides = new Rectangle[NO_OF_SIDES];

	/**
	 * creates the four rectangles at the right places
	 * @param x x position of the bottom left point of the whole rectangle
	 * @param y y position of the bottom left point of the whole rectangle
	 */
	public QuadraticBlockHitBox(int x, int y) {
		fourSides[INDEX_RIGHT] = createRectangle(
				x + HIT_BOX_SIZE - HIT_BOX_THICKNESS, y, false);
		fourSides[INDEX_LEFT] = createRectangle(x, y, false);
		fourSides[INDEX_UP] = createRectangle(x,
				y + HIT_BOX_SIZE - HIT_BOX_THICKNESS, true);
		fourSides[INDEX_DOWN] = createRectangle(x, y, true);
	}

	/**
	 * creates a Rectangle (= hitBox) at the given location
	 * @param x x position of the hitBox
	 * @param y y position of the hitBox
	 * @param isUpDown true if the hitBox is the one for up or down
	 * @return hitBox as a rectangle
	 */
	private Rectangle createRectangle(int x, int y, boolean isUpDown) {
		Rectangle rect = new Rectangle();
		rect.x = x;
		rect.y = y;
		
		if(isUpDown) {
			rect.width = HIT_BOX_SIZE;
			rect.height = HIT_BOX_THICKNESS;
		} else {
			rect.width = HIT_BOX_THICKNESS;
			rect.height = HIT_BOX_SIZE;
		}
		
		return rect;
	}

	/**
	 * checks the collision with the snake
	 * @param snake head of the snake as a rectangle
	 * @return side from which the snake hit
	 */
	public HitDirection checkWhichCollisionSide(Rectangle snake) {
		int sum = 0x00;
		for(int count = 0; count < NO_OF_SIDES; count++) {
			if(fourSides[count].overlaps(snake)) {
				switch (count) {
					case INDEX_RIGHT:
						sum = sum | 0x01;
						break;
					case INDEX_LEFT:
						sum = sum | 0x02;
						break;
					case INDEX_UP:
						sum = sum | 0x04;
						break;
					case INDEX_DOWN:
						sum = sum | 0x08;
						break;
				}
			}
		}
		return getHitDirectionForSide(sum);
	}

	/**
	 *	gets the direction (= enum) for the index of an array
	 * @param indexOfSide index of the array
	 * @return side from which something hit the block
	 */
	private HitDirection getHitDirectionForSide(int indexOfSide) {
		switch (indexOfSide) {
			case 0x01:
				return HitDirection.Right;
			case 0x02:
				return HitDirection.Left;
			case 0x04:
				return HitDirection.Up;
			case 0x08:
				return HitDirection.Down;
			case 0x05:
				return HitDirection.UpAndRight;
			case 0x06:
				return HitDirection.UpAndLeft;
			case 0x09:
				return HitDirection.DownAndRight;
			case 0x0A:
				return HitDirection.DownAndLeft;
			case 0x00:
			default:
				return HitDirection.NoHit;
		}
	}
}