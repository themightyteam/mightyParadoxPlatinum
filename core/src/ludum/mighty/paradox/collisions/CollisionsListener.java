package ludum.mighty.paradox.collisions;

import ludum.mighty.paradox.enemy.NoPlayer;
import ludum.mighty.paradox.player.Player;

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
		} else if (contact.getFixtureB().getBody().getUserData() instanceof Player) {
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
		System.out.println("collision with " + noPlayer.getNoPlayerType());
		
		// resolve collision
		if (noPlayer.getNoPlayerType() == noPlayer.TYPE_DOOR) {

			// Next level or victory!
			player.setPlayerState(Player.STATE_VICTORY);
		} else if ((noPlayer.getNoPlayerType() == noPlayer.TYPE_BULLET)
				|| (noPlayer.getNoPlayerType() == noPlayer.TYPE_SPIKE)) {
			// Death

			
			player.setPlayerState(Player.STATE_DEAD);
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
