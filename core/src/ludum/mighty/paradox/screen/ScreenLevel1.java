package ludum.mighty.paradox.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import ludum.mighty.paradox.enemy.Bat;
import ludum.mighty.paradox.enemy.BreakableBlock;
import ludum.mighty.paradox.enemy.EnemyFactory;
import ludum.mighty.paradox.enemy.GreenBlob;
import ludum.mighty.paradox.enemy.Guillotine;
import ludum.mighty.paradox.enemy.NoPlayer;
import ludum.mighty.paradox.enemy.PurpleBlob;
import ludum.mighty.paradox.enemy.WickedBlueMage;
import ludum.mighty.paradox.enemy.WickedBullet;
import ludum.mighty.paradox.enemy.WickedPurpleMage;
import ludum.mighty.paradox.enemy.WickedRedMage;
import ludum.mighty.paradox.player.Player;
import ludum.mighty.paradox.player.RecordedStep;
import ludum.mighty.paradox.render.MightyRender;
import ludum.mighty.paradox.settings.CommonSettings;
import ludum.mighty.paradox.world.MightyWorld;

public class ScreenLevel1 extends DefaultScreen {

	private static final int STATE_INIT = 0;
	private static final int STATE_NORMALPLAY = 1;

	OrthographicCamera guiCam;

	private TiledMap map;

	MightyWorld gameWorld;
	SpriteBatch batcher; //FIXME delete
	int state;
	
	MightyRender mightyRender;
	
	int currentLevel;


	public ScreenLevel1(Game game, int level, Array<Array<RecordedStep>> allRecordedSteps) {
			super(game);
			// TODO Auto-generated constructor stub

			this.currentLevel = level;
		
		this.state = STATE_INIT;

		this.guiCam = new OrthographicCamera();
		this.guiCam.setToOrtho(false, CommonSettings.CAMERA_WIDTH, CommonSettings.CAMERA_HEIGHT);
		this.guiCam.update();



		this.batcher = new SpriteBatch();

		//Create world and render;                                                                                                    
		this.gameWorld = new MightyWorld();
	
		//Init map
		this.map = new TmxMapLoader().load("maps/test_dw_fin.tmx");
		
		//Init the world
		this.gameWorld.init(this.map, this.currentLevel, allRecordedSteps);

		//Create rendered
		this.mightyRender = new MightyRender(this.gameWorld, this.map);

	}

	public void initWorld()
	{
		//Doing init stuff here

		this.state = STATE_NORMALPLAY;

	}

	//TODO Hector Stuff for debugging
	public void initEnemies()
	{
		//Create a bat
		
		ArrayList<NoPlayer> enemyList = new ArrayList<NoPlayer>();
		
		//Add a wicked mage with a clumsy movement
		String enemyPat1 = "WICKEDREDMAGE 1 -1 5000;0 2500 1 1 5 1 1 13;2500 5000 5 1 1 1 1 12";
		WickedRedMage enemy1 = (WickedRedMage) EnemyFactory.buildEnemy(enemyPat1);
		
		String enemyPat2 = "WICKEDBLUEMAGE 2 -1 10000;"
				+ "0 2500 6 4 8 4 1 25;"
				+ "2500 3000 8 4 10 5 2 27;"
				+ "3000 5000 10 5 12 4 2 27;"
				+ "5000 10000 12 4 6 4 1 24";
		WickedBlueMage enemy2 = (WickedBlueMage) EnemyFactory.buildEnemy(enemyPat2);
		//Add enemy to list
		
		String enemyPat3 = "WICKEDBULLET 3 2 10000;"
				+ "3000 3500 10 5 20 5 1 40";
		
		WickedBullet enemy3 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat3);
		
		String enemyPat4 = "WICKEDPURPLEMAGE 4 -1 7000;"
				+ "0 7000 25 5 25 5 1 16";
		
		WickedPurpleMage enemy4 = (WickedPurpleMage) EnemyFactory.buildEnemy(enemyPat4);
		
		String enemyPat5 = "WICKEDBULLET 5 4 7000;"
				+ "3000 3500 25 5 28 5 1 40";
		
		WickedBullet enemy5 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat5);
		
		String enemyPat6 = "WICKEDBULLET 6 4 7000;"
				+ "3000 3500 25 5 22 5 1 40";
		
		WickedBullet enemy6 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat6);
		
		String enemyPat7 = "WICKEDBULLET 7 4 7000;"
				+ "3000 3500 25 5 25 8 1 40";
		
		WickedBullet enemy7 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat7);
		
		String enemyPat8 = "WICKEDBULLET 8 4 7000;"
				+ "3000 3500 25 5 25 2 1 40";
		
		WickedBullet enemy8 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat8);
		
		String enemyPat9 = "WICKEDBULLET 9 4 7000;"
				+ "3250 3750 25 5 28 8 2 40";
		
		WickedBullet enemy9 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat9);
		
		String enemyPat10 = "WICKEDBULLET 10 4 7000;"
				+ "3250 3750 25 5 22 8 2 40";
		
		WickedBullet enemy10 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat10);
		
		String enemyPat11 = "WICKEDBULLET 11 4 7000;"
				+ "3250 3750 25 5 28 2 2 40";
		
		WickedBullet enemy11 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat11);
		
		String enemyPat12 = "WICKEDBULLET 12 4 7000;"
				+ "3250 3750 25 5 22 2 2 40";
		
		WickedBullet enemy12 = (WickedBullet) EnemyFactory.buildEnemy(enemyPat12);
		
		String enemyPat13 = "GREENBLOB 13 -1 10000;"
				+ "0 4000 10 7 14 7 1 31;"
				+ "4000 5000 14 7 14 7 1 29;"
				+ "5000 6000 14 7 10 7 2 30;"
				+ "6000 10000 10 7 10 7 1 28";
		
		
		GreenBlob enemy13 = (GreenBlob) EnemyFactory.buildEnemy(enemyPat13);
		
		String enemyPat14 = "PURPLEBLOB 14 -1 2000;"
				+ "0 1000 15.5 8 17 8 1 37;"
				+ "1000 2000 17 8 15.5 8 2 36";
		
		PurpleBlob enemy14 = (PurpleBlob) EnemyFactory.buildEnemy(enemyPat14);
		
		String enemyPat15 = "GUILLOTINE 15 -1 5000;"
				+ "0 4500 18 5 18 11 1 46;"
				+ "4500 5000 18 11 18 5 2 46";
		
		Guillotine enemy15 = (Guillotine) EnemyFactory.buildEnemy(enemyPat15);
		
		String enemyPat16 = "BAT 16 -1 7500;"
				+ "0 3000 4 12 4 12 1 41;"
				+ "3000 3200 4 12 5 9 1 421;"
				+ "3200 3800 5 9 7 10 2 421;"
				+ "3800 4400 7 10 3 5 1 42;"
				+ "4400 7500 3 5 4 12 1 421";
		
		Bat enemy16 = (Bat) EnemyFactory.buildEnemy(enemyPat16);
		
		String enemyPat17 = "BREAKABLEBLOCK 17 -1 10000;"
				+ "0 10000 6 6 6 6 1 56";
		BreakableBlock enemy17 = (BreakableBlock) EnemyFactory.buildEnemy(enemyPat17);
		
		enemyList.add(enemy1);
		enemyList.add(enemy2);
		enemyList.add(enemy3);
		enemyList.add(enemy4);
		enemyList.add(enemy5);
		enemyList.add(enemy6);
		enemyList.add(enemy7);
		enemyList.add(enemy8);
		enemyList.add(enemy9);
		enemyList.add(enemy10);
		enemyList.add(enemy11);
		enemyList.add(enemy12);
		enemyList.add(enemy13);
		enemyList.add(enemy14);
		enemyList.add(enemy15);
		enemyList.add(enemy16);
		enemyList.add(enemy17);
		
		
		this.gameWorld.setEnemyList(enemyList);
		
	}
	
	
	public void updateWorld()
	{
		//Pick the pad

		//Update the world
		
		//FIXME Hector Stuff for debug
		this.gameWorld.updateTime();
		this.gameWorld.updateEnemyPosition();
		this.gameWorld.updateGhostPosition();
		
		// if (this.gameWorld.getTimeEpoch() > 30000 &&
		// this.gameWorld.getTimeEpoch() < 40000)
		// {
		// this.gameWorld.setAllEnemiesDeath();
		// }
		// else if (this.gameWorld.getTimeEpoch() > 40000)
		// {
		// this.gameWorld.setAllEnemiesCharred();
		// }
	}

	/**                                                                                                                                   
	 *                                                                                                                                    
	 * @param deltaTime                                                                                                                   
	 */
	public void update(float deltaTime)
	{
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		switch(state)
		{

		case STATE_INIT:
			initWorld();
			// initEnemies();
			break;

		case STATE_NORMALPLAY:
			updatePad();
			updateWorld();
			checkEndCondition();
			break;
		}
	}

	
	private void checkEndCondition() {
		if (this.gameWorld.getPlayerStatus() == Player.STATE_DEAD) {
			System.out.println("You are dead :'(");
			this.mightyGame.setScreen(new ScoreScreen(this.mightyGame));
		}
		if (this.gameWorld.getPlayerStatus() == Player.STATE_VICTORY) {
			System.out.println("Next Level! :D " + String.valueOf(this.currentLevel));
			if (this.currentLevel >= 3) {
				this.mightyGame.setScreen(new FinalVictoryScreen(this.mightyGame));
			}

			// Create an array of player step records to pass to the next screen
			Array<Array<RecordedStep>> allRecordedSteps = this.gameWorld.getAllRecordedSteps();

			this.mightyGame.setScreen(new ScreenLevel1(this.mightyGame, this.currentLevel + 1, allRecordedSteps));
		}
	}

	private void updatePad() {
		boolean up = false, down = false, left = false, right = false, fire = false;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			left = true;
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			right = true;
		}

		if (Gdx.input.isKeyPressed(Keys.UP)) {
			up = true;
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			down = true;
		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			fire = true;
		}
		this.gameWorld.updatePlayer(up, down, left, right, fire);
	}
	
	/**                                                                                                                                   
	 *                                                                                                                                    
	 * @param deltaTime                                                                                                                   
	 */
	public void draw(float deltaTime)
	{

		// clear the screen
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		switch (state) {
		case STATE_INIT:
			break;
		case STATE_NORMALPLAY:
                               
			// measured in "real units" = 16 pixels
			this.guiCam.position.x = this.gameWorld.getCameraPositionX();
			this.guiCam.position.y = this.gameWorld.getCameraPositionY();
			this.guiCam.update();
			
			this.mightyRender.setCameraView(this.guiCam);

			this.mightyRender.render();
			break;
		}

	}

	/**                                                                                                                                   
	 *                                                                                                                                    
	 * @param delta                                                                                                                       
	 */
	public void physicStep(float delta)
	{
		this.gameWorld.stepBox2D();
	}


	@Override
	public void render(float delta)
	{
		update(delta);
		draw(delta);
		physicStep(delta);
	}



}
