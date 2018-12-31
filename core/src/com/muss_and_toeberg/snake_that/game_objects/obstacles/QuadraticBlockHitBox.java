package com.muss_and_toeberg.snake_that.game_objects.obstacles;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.muss_and_toeberg.snake_that.technical.HitDirection;

/**
 * describes the hitBox for the block as four small hitBoxes as his outlines
 */
public class QuadraticBlockHitBox {
	// constant values
	public final static int HIT_BOX_SIZE = 256;
	final int NO_OF_SIDES = 4;

	// position of the waluigi
	private int x;
	private int y;

	// the four hitBoxes as an array
	// 0 - right	1 - left
	// 2 - up	3- down
	private Rectangle[] fourSides = new Rectangle[NO_OF_SIDES];

	/**
	 * creates the four rectangles at the right places
	 * @param x x position of the bottom left point of the whole rectangle
	 * @param y y position of the bottom left point of the whole rectangle
	 */
	public QuadraticBlockHitBox(int x, int y) {
		this.x = x;
		this.y = y;

		fourSides[0] = createRectangle(x + HIT_BOX_SIZE - 1, y, false);
		fourSides[1] = createRectangle(x, y, false);
		fourSides[2] = createRectangle(x, y + HIT_BOX_SIZE - 1, true);
		fourSides[3] = createRectangle(x, y, true);
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
			rect.height = 1;
		} else {
			rect.width = 1;
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
		for(int i = 0; i < NO_OF_SIDES; i++) {
			if(fourSides[i].overlaps(snake)) {
				switch (i) {
					case 0:
						sum = sum | 0x01;
						break;
					case 1:
						sum = sum | 0x02;
						break;
					case 2:
						sum = sum | 0x04;
						break;
					case 3:
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
				return HitDirection.RightSide;
			case 0x02:
				return HitDirection.LeftSide;
			case 0x04:
				return HitDirection.Upwards;
			case 0x08:
				return HitDirection.Downwards;
			case 0x05:
				return HitDirection.UpAndRight;
			case 0x06:
				return HitDirection.UpAndLeft;
			case 0x09:
				return HitDirection.DownAndRight;
			case 0x0A:
				return HitDirection.DownAndLeft;
			case 0:
			default:
				return HitDirection.NoHit;
		}
	}
}