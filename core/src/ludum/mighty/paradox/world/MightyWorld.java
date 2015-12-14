package ludum.mighty.paradox.world;

import java.util.ArrayList;
import java.util.StringTokenizer;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import ludum.mighty.paradox.collisions.CollisionsListener;
import ludum.mighty.paradox.enemy.EnemyFactory;
import ludum.mighty.paradox.enemy.NoPlayer;
import ludum.mighty.paradox.player.Player;
import ludum.mighty.paradox.player.RecordedStep;
import ludum.mighty.paradox.settings.CommonSettings;
import net.dermetfan.gdx.physics.box2d.Box2DMapObjectParser;

public class MightyWorld {

	private World world;
	private CollisionsListener collisionListener;
	
	private ArrayList<NoPlayer> enemyList;
	
	long startEpoch;
	
	long timeEpoch;
	long differenceEpoch; // difference between now and when the level is really
							// played (in the future!!)
	
	private int curentLevel;

	//FIXME massive destruction only for debug (Hector stuff)
	boolean massiveDeath = false;
	boolean massiveCharred = false;
	
	private Integer mapWidth;
	private Integer mapHeight;
	
	public Integer getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(Integer mapWidth) {
		this.mapWidth = mapWidth;
	}

	public Integer getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(Integer mapHeight) {
		this.mapHeight = mapHeight;
	}

	public MightyWorld()
	{

	}

	public void init(TiledMap map, int level, Array<Array<RecordedStep>> allRecordedSteps) {
		this.curentLevel = level;

		this.enemyList = new ArrayList<NoPlayer>();
		
		this.differenceEpoch = ((10 - this.curentLevel) * CommonSettings.SECONDS_PER_LEVEL) * 1000;
		this.startEpoch = TimeUtils.millis();
		System.out.println("start epoch " + String.valueOf(this.differenceEpoch));
		//Init physics here
	
		// create physics
		this.world = new World(new Vector2(0, -10f), true);
		this.collisionListener = new CollisionsListener();
		world.setContactListener(this.collisionListener);

		// populate box2D with objects from the map
		Box2DMapObjectParser parser = new Box2DMapObjectParser(1 / 16f);
		parser.load(world, map);
		
		// print map objects
		// based in
		// https://bitbucket.org/dermetfan/somelibgdxtests/src/adac6846bf5cb74d413e23a6f538b35d9820486c/libgdx-utils/net/dermetfan/libgdx/box2d/Box2DMapObjectParser.java?at=default&fileviewer=file-view-default#Box2DMapObjectParser.java-567
		// not in this version of the library
		/*
		 * String hierarchy = map.getClass().getSimpleName() + "\n";
		 * 
		 * Iterator<String> keys = map.getProperties().getKeys(); while
		 * (keys.hasNext()) { String key = keys.next(); hierarchy += key + ": "
		 * + map.getProperties().get(key) + "\n"; }
		 * 
		 * for (MapLayer layer : map.getLayers()) { hierarchy += "\t" +
		 * layer.getName() + " (" + layer.getClass().getSimpleName() + "):\n";
		 * String layerHierarchy = "";
		 * 
		 * for (MapObject object : layer.getObjects()) { layerHierarchy +=
		 * object.getName() + " (" + object.getClass().getSimpleName() + "):\n";
		 * Iterator<String> keys0 = object.getProperties().getKeys(); while
		 * (keys0.hasNext()) { String key1 = keys0.next(); layerHierarchy +=
		 * "\t" + key1 + ": " + object.getProperties().get(key1) + "\n"; } }
		 * layerHierarchy = layerHierarchy.replace("\n", "\n\t\t");
		 * 
		 * layerHierarchy = layerHierarchy.endsWith("\n\t\t") ?
		 * layerHierarchy.substring(0, layerHierarchy.lastIndexOf("\n\t\t")) :
		 * layerHierarchy; hierarchy += !layerHierarchy.equals("") ? "\t\t" +
		 * layerHierarchy : layerHierarchy; }
		 * 
		 * System.out.println(hierarchy);
		 */

		// print world objects position and velocity
		// Array<Body> bodies = new Array<Body>();
		// world.getBodies(bodies);
		// for (Body body : bodies) {
		// System.out.println("Object X:" + body.getPosition().x + " Y:" +
		// body.getPosition().y + "\tvelocity "
		// + body.getLinearVelocity().x + " " + body.getLinearVelocity().y);
		// }
		
		// Find objects in the world and assign them a new player or noplayer
		// object. Only assign objects from this level or bellow
		// removes objects from the other levels.

		
		// Print all recorded steps
		// if (allRecordedSteps != null) {
		// for (Array<RecordedStep> recordedSteps : allRecordedSteps) {
		// System.out.println("__New List ");
		// for (RecordedStep step : recordedSteps) {
		// System.out.println(" " + String.valueOf(step.timestamp) + " up:" +
		// String.valueOf(step.up)
		// + " down:" + String.valueOf(step.down) + " left:" +
		// String.valueOf(step.left) + " right:"
		// + String.valueOf(step.right) + " fire:" + String.valueOf(step.fire));
		// }
		// }
		// }
		 
		 
		 Array<Body> bodies = new Array<Body>();
		 world.getBodies(bodies);
		 for (Body body : bodies) {
		 if (body.getUserData() instanceof String) {

				StringTokenizer tokens = new StringTokenizer((String) body.getUserData(), ";");
				String bodyType = tokens.nextToken();
				
				int bodyLevel = Integer.parseInt(tokens.nextToken());

				if (bodyType.equals("player")) {
					if (bodyLevel <= level) {
						Player player = new Player();
						player.setPlayerState(Player.STATE_PLAYING);
						player.setActiveInLevel(bodyLevel);
						System.out.println("player found from level " + String.valueOf(bodyLevel));
						if (bodyLevel < level) {
							player.setReplaying(true);
							player.setRecordedSteps(allRecordedSteps.get(bodyLevel - 1));
							System.out.println("Ghost player with recorded steps: "
									+ String.valueOf(player.getRecordedSteps().size));
						} else {
							player.setReplaying(false);
						}
						body.setUserData(player);
						if (body.getUserData() instanceof Player) {
							System.out.println(
									"player found " + String.valueOf(((Player) body.getUserData()).getActiveInLevel()));
						}
					} else {
						world.destroyBody(body);
					}
					
				} else if (bodyType.equals("noplayer"))
				{
					if (bodyLevel <= level) {
						
						String enemyPattern = tokens.nextToken();
						
						while(tokens.hasMoreTokens())
						{
							enemyPattern = enemyPattern + ";" + tokens.nextToken();	
									
						}
						
						System.out.println("Enemy Pattern:"+enemyPattern);
						
						NoPlayer noPlayer = EnemyFactory.buildEnemy(enemyPattern);
						
						body.setUserData(noPlayer);
						
						this.enemyList.add(noPlayer);
						
					} else {
						world.destroyBody(body);
					}
				}
		 }
		 }
		MapProperties prop = map.getProperties();

		this.mapWidth = prop.get("width", Integer.class);
		this.mapHeight = prop.get("height", Integer.class);

		 
	}
	
	public void stepBox2D()
	{
		this.world.step(1f / 60f, 6, 2);
		
	}
	

	
	//TODO: Hector stuff for debugging
	public void updateTime()
	{
		this.timeEpoch = TimeUtils.millis() - startEpoch + this.differenceEpoch;
	}

	public void updateEnemyPosition() {
		
		for (NoPlayer enemy : this.enemyList)
		{
			enemy.update(this.timeEpoch);
			/*
			 * System.out.println("ID ENEMY "
			 * +Integer.toString(enemy.getIdEnemy())); System.out.println(
			 * "\tCURRENT X"+Float.toString(enemy.getCurrentX()));
			 * System.out.println("\tCURRENT Y"
			 * +Float.toString(enemy.getCurrentY())); System.out.println(
			 * "\tNSTEPS " +
			 * Integer.toString(enemy.getPattern().getPatternList().size()));
			 */

		}
		
		// Update position on world
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			if (body.getUserData() instanceof NoPlayer) {
				body.setTransform(((NoPlayer) body.getUserData()).getCurrentX(),
						this.mapHeight - ((NoPlayer) body.getUserData()).getCurrentY() - 1, 0);
				// System.out.println(
				// "object moved to " + Integer.toString((int) ((NoPlayer)
				// body.getUserData()).getCurrentX()));
		
			}
		}

	}
	
	public void setAllEnemiesDeath() {
		// TODO Auto-generated method stub
		if (!this.massiveDeath)
		{
		for (NoPlayer enemy : this.enemyList)
		{
			enemy.nowIsDead(this.timeEpoch);
		}
		
		this.massiveDeath = true;
		}
	}	
	
	
	public void setAllEnemiesCharred() {
		
		if (!this.massiveCharred)
		{
		for (NoPlayer enemy : this.enemyList)
		{
			enemy.nowIsCharred(this.timeEpoch);
		}
		this.massiveCharred = true;
		}
		
	}
	
	// returns only the active player (not from other levels)
		private Body getPlayer() {
			// playerbody should be a variable in mightyworld?
			Array<Body> bodies = new Array<Body>();
			world.getBodies(bodies);
			for (Body body : bodies) {
				if (body.getUserData() instanceof Player) {
					if (((Player) body.getUserData()).isReplaying() == false)
					return body;
				}
			}
			return null;
		}


		public void updatePlayer(boolean up, boolean down, boolean left, boolean right, boolean fire) {
			Vector2 maxVelocity = new Vector2(10, 10);
		Vector2 impulse = new Vector2(6, 8);
			Body playerBody = this.getPlayer();
			Vector2 currentVelocity = playerBody.getLinearVelocity();
			if (up) {
				if (((Player) playerBody.getUserData()).isTouching() == true) {
				currentVelocity = currentVelocity.add(0, impulse.y);
				if (currentVelocity.y > maxVelocity.y)
					currentVelocity.y = maxVelocity.y;
					((Player) playerBody.getUserData()).setTouching(false);
				}
			}
			if (down) {
				currentVelocity = currentVelocity.add(0, impulse.y * -1);
				if (currentVelocity.y < maxVelocity.y * -1)
					currentVelocity.y = maxVelocity.y * -1;
			}
			if ((left == right) || ((left == false) && (right == false))) {
				currentVelocity = currentVelocity.sub(currentVelocity.x / 3, 0);

			} else {
			if (left) {
				currentVelocity = currentVelocity.add(impulse.x * -1, 0);
				if (currentVelocity.x < maxVelocity.x * -1)
					currentVelocity.x = maxVelocity.x * -1;
			}
			if (right) {
				currentVelocity = currentVelocity.add(impulse.x, 0);
				if (currentVelocity.x > maxVelocity.x)
					currentVelocity.x = maxVelocity.x;
			}
			}

			playerBody.setLinearVelocity(currentVelocity);
		((Player) playerBody.getUserData()).addRecordedStep(timeEpoch, playerBody.getWorldCenter().x - (float) 0.5,
				playerBody.getWorldCenter().y - (float) 0.5, fire);
		}


		public float getCameraPositionX() {
			Body playerBody = this.getPlayer();
			float camPosX = (float) playerBody.getWorldCenter().x;
			float camBorderX = ((float) CommonSettings.CAMERA_WIDTH) / 2;
			if (camPosX < camBorderX)
				return camBorderX;
			if (camPosX > this.mapWidth - camBorderX)
				return this.mapWidth - camBorderX;
			return playerBody.getWorldCenter().x;
		}

		public float getCameraPositionY() {
			Body playerBody = this.getPlayer();
			float camPosY = (float) playerBody.getWorldCenter().y;
			float camBorderY = ((float) CommonSettings.CAMERA_HEIGHT) / 2;
			if (camPosY < camBorderY)
				return camBorderY;
			if (camPosY > this.mapHeight - camBorderY)
				return this.mapHeight - camBorderY;
			return playerBody.getWorldCenter().y;
		}

		public int getPlayerStatus() {
			return ((Player) this.getPlayer().getUserData()).getPlayerState();
		}	
		
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public ArrayList<NoPlayer> getEnemyList() {
		return enemyList;
	}

	public void setEnemyList(ArrayList<NoPlayer> enemyList) {
		this.enemyList = enemyList;
	}

	public long getTimeEpoch() {
		return timeEpoch;
	}

	public void setTimeEpoch(long timeEpoch) {
		this.timeEpoch = timeEpoch;
	}

	public long getStartEpoch() {
		return startEpoch;
	}

	public void setStartEpoch(long startEpoch) {
		this.startEpoch = startEpoch;
	}

	public Array<Array<RecordedStep>> getAllRecordedSteps() {
		Array<Array<RecordedStep>> allRecordedSteps = new Array<Array<RecordedStep>>(true, 10);

		for (int i = 0; i < 10; i++)
			allRecordedSteps.add(null);

		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			if (body.getUserData() instanceof Player) {
				allRecordedSteps.set(((Player) body.getUserData()).getActiveInLevel() - 1,
						((Player) body.getUserData()).getRecordedSteps());
			}
		}

		return allRecordedSteps;
	}

	public void updateGhostPosition() {
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			if (body.getUserData() instanceof Player) {
				if (((Player) body.getUserData()).getActiveInLevel() < this.curentLevel) {

					RecordedStep step = ((Player) body.getUserData()).getCurrentRecordedStep(this.timeEpoch);
					if (step != null) {
						body.setTransform(step.x, step.y, 0);
						// TODO fires here
					}

				}
			}
		}
	}

	public ArrayList<Body> getPlayerBodysList() {
		ArrayList<Body> list = new ArrayList<Body>();
		Array<Body> bodies = new Array<Body>();
		world.getBodies(bodies);
		for (Body body : bodies) {
			list.add(body);
		}
		return list;
	}
	
}
