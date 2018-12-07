package com.muss_and_toeberg.snake_that.obstacles_and_elements;

import com.badlogic.gdx.math.Rectangle;
import com.muss_and_toeberg.snake_that.technical.HitDirection;

/**
 * describes the hitBox for Waluigi as four small hitBoxes as his outlines
 */
public class WaluigiHitBox {
	// constant values
	final int HIT_BOX_SIZE = 255;

	// position of the waluigi
	private int x;
	private int y;

	// the four hitBoxes
	private Rectangle rightSide;
	private Rectangle leftSide;
	private Rectangle upSide;
	private Rectangle downSide;

	/**
	 * creates the four rectangles at the right places
	 * @param x x position of the bottom left point of the whole rectangle
	 * @param y y position of the bottom left point of the whole rectangle
	 */
	public WaluigiHitBox(int x, int y) {
		this.x = x;
		this.y = y;
		
		leftSide = createRectangle(x - 1, y, false);
		rightSide = createRectangle(x + HIT_BOX_SIZE - 1, y, false);
		upSide = createRectangle(x, y + HIT_BOX_SIZE- 1, true);
		downSide = createRectangle(x, y - 1, true);
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
		if(rightSide.overlaps(snake)) {
			return HitDirection.RightSide;
		} else if (leftSide.overlaps(snake)) {
			return HitDirection.LeftSide;
		}

		if(upSide.overlaps(snake)) {
			return HitDirection.Upwards;
		} else if(downSide.overlaps(snake)) {
			return HitDirection.Downwards;
		}

		return HitDirection.NoHit;
	}
}