package com.muss_and_toeberg.snake_that;

import com.badlogic.gdx.math.Rectangle;

// describes the hitbox for waluigi as four small hitboxes as his outlines
public class WaluigiHitBox {
	final int HIT_BOX_SIZE = 245;
	
	private int x;
	private int y;

	private Rectangle rightSide;
	private Rectangle leftSide;
	private Rectangle upSide;
	private Rectangle downSide;
	
	// Constructor which creates the four hitboxes
	public WaluigiHitBox(int x, int y) {
		this.x = x;
		this.y = y;
		
		leftSide = createRectangle(x - 1, y, false);
		rightSide = createRectangle(x + HIT_BOX_SIZE - 1, y, false);
		upSide = createRectangle(x, y + HIT_BOX_SIZE- 1, true);
		downSide = createRectangle(x, y - 1, true);
	}
	
	// creates a Rectangle (= Hitbox) at the given location	
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
	
	// checks the collision with the snake and returns from which side waluigi was hitbox
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