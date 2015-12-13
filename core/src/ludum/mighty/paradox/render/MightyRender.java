package ludum.mighty.paradox.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import ludum.mighty.paradox.assets.WholeGameAssets;
import ludum.mighty.paradox.enemy.NoPlayer;
import ludum.mighty.paradox.world.MightyWorld;

public class MightyRender 
{

	private Box2DDebugRenderer box2DRenderer;
	private OrthogonalTiledMapRenderer renderer;
	OrthographicCamera guiCam;
	WholeGameAssets gameAssets;

	MightyWorld gameWorld;
	TiledMap map;

	public MightyRender(MightyWorld gameWorld, TiledMap map)
	{

		this.gameAssets = new WholeGameAssets();
		this.gameAssets.load();

		this.map = map;
		this.gameWorld = gameWorld;

		this.box2DRenderer = new Box2DDebugRenderer();	

		this.renderer = new OrthogonalTiledMapRenderer(map, 1 /16f);



	}

	public void setCameraView(OrthographicCamera guiCam)
	{
		this.guiCam = guiCam;
		this.renderer.setView(this.guiCam);
	}

	private void renderPlayer() {
		// TODO Auto-generated method stub

	}

	public void renderEnemies()
	{
		this.renderer.getBatch().begin();

		//System.out.println("Time Epoch "+this.gameWorld.getTimeEpoch());

		for (NoPlayer enemy : this.gameWorld.getEnemyList())
		{
			if (enemy.getAnimation() > 0)
			{	
				TextureRegion enemyRegion = this.gameAssets.getAnimation(
						enemy.getAnimation(), (float) ((this.gameWorld.getTimeEpoch() % 1000)/1000.0));

				if (enemyRegion != null)
					this.renderer.getBatch().draw(enemyRegion, enemy.getCurrentX(),
							this.gameWorld.getMapHeight() - enemy.getCurrentY() - 1,
							enemyRegion.getRegionWidth() / 16f, enemyRegion.getRegionHeight() / 16f);
			}
		}

		this.renderer.getBatch().end();
	}

	public void render()
	{

		this.renderer.render();
		this.renderEnemies();
		this.renderPlayer();
		this.box2DRenderer.render(this.gameWorld.getWorld(), this.guiCam.combined);
	}



}
