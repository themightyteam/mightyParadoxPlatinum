package ludum.mighty.paradox.collisions;

import ludum.mighty.paradox.enemy.Beer;
import ludum.mighty.paradox.enemy.BreakableBlock;
import ludum.mighty.paradox.enemy.GreenBlob;
import ludum.mighty.paradox.enemy.Jewel;
import ludum.mighty.paradox.enemy.Key;
import ludum.mighty.paradox.enemy.Lever;
import ludum.mighty.paradox.enemy.NoPlayer;
import ludum.mighty.paradox.player.Player;
import ludum.mighty.paradox.settings.CommonSettings;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionsListener implements ContactListener {

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void beginContact(Contact contact) {
		// System.out.println("collision!");
		Player player = null;
		NoPlayer noPlayer = null;

		if (contact.getFixtureA().getBody().getUserData() instanceof Player) {
			player = (Player) contact.getFixtureA().getBody().getUserData();
			player.setTouching(true);
		}
		if (contact.getFixtureB().getBody().getUserData() instanceof Player) {
			if (player != null) {
				// Paradox!!
				player.setPlayerState(Player.STATE_PARADOX);
				player = (Player) contact.getFixtureB().getBody().getUserData();
				player.setPlayerState(Player.STATE_PARADOX);
				return;
			}
			player = (Player) contact.getFixtureB().getBody().getUserData();
			player.setTouching(true);
		}

		if (contact.getFixtureA().getBody().getUserData() instanceof NoPlayer) {
			noPlayer = (NoPlayer) contact.getFixtureA().getBody().getUserData();
		} else if (contact.getFixtureB().getBody().getUserData() instanceof NoPlayer) {
			noPlayer = (NoPlayer) contact.getFixtureB().getBody().getUserData();
		}

		if ((player == null) || (noPlayer == null))
			return;
		// System.out.println("collision with " + noPlayer.getNoPlayerType());

		// resolve collision
		if (noPlayer.getNoPlayerType() == noPlayer.TYPE_DOOR) {
			if (player.getActiveInLevel() == 1) {
				// Next level or victory!
				if (player.getButtonsPushed() >= CommonSettings.LEVERS_TO_PUSH) 
				{
					player.setPlayerState(Player.STATE_VICTORY);
				}
			} else {
				player.setPlayerState(Player.STATE_VICTORY);
			}

		} else if ((noPlayer.getNoPlayerType() == noPlayer.TYPE_WICKEDBULLET)
				|| (noPlayer.getNoPlayerType() == noPlayer.TYPE_SPIKE)
				|| (noPlayer.getNoPlayerType() == noPlayer.TYPE_BULLET)
				|| (noPlayer.getNoPlayerType() == noPlayer.TYPE_GUILLOTINE)
				|| (noPlayer.getNoPlayerType() == noPlayer.TYPE_PURPLEBLOB)
				|| (noPlayer.getNoPlayerType() == noPlayer.TYPE_BAT)) {
			// Death


			player.setPlayerState(Player.STATE_DEAD);
		} else if (noPlayer.getNoPlayerType() == noPlayer.TYPE_LEVER) {
			if (((Lever) noPlayer).isTouched() == false) {
				player.setButtonsPushed(player.getButtonsPushed() + 1);
				((Lever) noPlayer).setTouched(true);
				
				//FIXME : open the door if the number of buttons pushed is more than x
				
				
			}
		} else if (noPlayer.getNoPlayerType() == noPlayer.TYPE_GREENBLOB) {
			((GreenBlob) noPlayer).setTouched(true);
		} else if (noPlayer.getNoPlayerType() == noPlayer.TYPE_BREAKABLEBLOCK) {
			((BreakableBlock) noPlayer).setTouched(true);
		} 
		else if (noPlayer.getNoPlayerType() == noPlayer.TYPE_BEER)
		{
			((Beer) noPlayer).setTouched(true);
		}
		else if (noPlayer.getNoPlayerType() == noPlayer.TYPE_KEY)
		{
			((Key) noPlayer).setTouched(true);
		}
		else if (noPlayer.getNoPlayerType() == noPlayer.TYPE_JEWEL)
		{
			((Jewel) noPlayer).setTouched(true);
		}

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}





}
