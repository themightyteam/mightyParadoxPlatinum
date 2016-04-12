package ludum.mighty.paradox.player;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

/**
 * @author jorge
 *
 */
public class Player {

	private boolean touchingSomething; // true if player is on ground or
										// touching something (so it can jump)
	public static final int STATE_PLAYING = 1;
	public static final int STATE_DEAD = 2;
	public static final int STATE_PARADOX = 3;
	public static final int STATE_VICTORY = 4;
	public static final int STATE_TIMEOUT = 5; 

	private boolean isReplaying;
	private int activeInLevel; // the level where the user controls this player

	private Array<RecordedStep> steps;

	RecordedStep previousStep;

	int buttonsPushed;

	private int playerState;

	Vector2 maxVelocity = new Vector2(10, 10);
	Vector2 impulse = new Vector2(6, 8);
	/**
	 * 
	 */
	public Player() {
		touchingSomething = false;
		steps = new Array<RecordedStep>(true, 7500);
		previousStep = new RecordedStep(0, -1, -1, false);
		steps.add(previousStep);

		this.buttonsPushed = 0;
	}

	public int getButtonsPushed() {
		return buttonsPushed;
	}

	public void setButtonsPushed(int buttonsPushed) {
		this.buttonsPushed = buttonsPushed;
	}

	public Array<RecordedStep> getRecordedSteps() {
		// fix a bug in libgdx's Array that overwrites index 0 with
		// the last value added
		this.steps.set(0, new RecordedStep(0, -1, -1, false));

		return steps;
	}

	public void setRecordedSteps(Array<RecordedStep> array) {
		steps = array;
	}

	public void addRecordedStep(long timestamp, float x, float y, boolean fire) {
		if ((this.previousStep.x != x) || (this.previousStep.y != y) || (this.previousStep.fire != fire)) {
			RecordedStep step = new RecordedStep(timestamp, x, y, fire);
			steps.add(step);
			this.previousStep.timestamp = timestamp;
			this.previousStep.x = x;
			this.previousStep.y = y;
		}
	}

	/**
	 * @brief Updates player's Box2D body velocity and records the current
	 *        position.
	 * @param playerBody
	 *            player's Box2D body.
	 * @param timeEpoch
	 *            current time.
	 * @param upKeyPressed
	 *            if up key is pressed.
	 * @param downKeyPressed
	 *            if down key is pressed.
	 * @param leftKeyPressed
	 *            if left key is pressed.
	 * @param rightKeyPressed
	 *            if right key pressed.
	 * @param fireKeyPressed
	 *            if fire key is pressed.
	 */
	public void updatePlayerPosition(Body playerBody, long timeEpoch, boolean upKeyPressed, boolean downKeyPressed,
			boolean leftKeyPressed, boolean rightKeyPressed, boolean fireKeyPressed) {

		Vector2 currentVelocity = playerBody.getLinearVelocity();
		if (upKeyPressed) {
			if (this.isTouching() == true) {
				currentVelocity = currentVelocity.add(0, impulse.y);
				if (currentVelocity.y > maxVelocity.y)
					currentVelocity.y = maxVelocity.y;
				this.setTouching(false);
			}
		}
		if (downKeyPressed) {
			currentVelocity = currentVelocity.add(0, impulse.y * -1);
			if (currentVelocity.y < maxVelocity.y * -1)
				currentVelocity.y = maxVelocity.y * -1;
		}
		if (leftKeyPressed == rightKeyPressed) {
			currentVelocity = currentVelocity.sub(currentVelocity.x / 3, 0);

		} else {
			if (leftKeyPressed) {
				currentVelocity = currentVelocity.add(impulse.x * -1, 0);
				if (currentVelocity.x < maxVelocity.x * -1)
					currentVelocity.x = maxVelocity.x * -1;
			}
			if (rightKeyPressed) {
				currentVelocity = currentVelocity.add(impulse.x, 0);
				if (currentVelocity.x > maxVelocity.x)
					currentVelocity.x = maxVelocity.x;
			}
		}

		playerBody.setLinearVelocity(currentVelocity);
		this.addRecordedStep(timeEpoch, playerBody.getWorldCenter().x - (float) 0.5,
				playerBody.getWorldCenter().y - (float) 0.5, fireKeyPressed);
	}

	public boolean isTouching() {
		return this.touchingSomething;
	}

	public void setTouching(boolean touching) {
		this.touchingSomething = touching;
	}

	public int getPlayerState() {
		return this.playerState;
	}

	public void setPlayerState(final int playerState) {
		this.playerState = playerState;
	}

	public boolean isReplaying() {
		return this.isReplaying;
	}

	public void setReplaying(boolean isReplaying) {
		this.isReplaying = isReplaying;
	}

	public int getActiveInLevel() {
		return activeInLevel;
	}

	public void setActiveInLevel(int activeInLevel) {
		this.activeInLevel = activeInLevel;
	}

	public RecordedStep getCurrentRecordedStep(long timeEpoch) {
		for (RecordedStep step : steps) {
			if (step.timestamp >= timeEpoch) {
				return step;
			}
		}
		return new RecordedStep(timeEpoch, -1, -1, false);
	}


}

