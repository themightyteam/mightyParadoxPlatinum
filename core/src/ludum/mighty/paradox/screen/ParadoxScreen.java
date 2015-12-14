package ludum.mighty.paradox.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ParadoxScreen extends DefaultScreen {


	SpriteBatch batch;
	Texture img;

	public ParadoxScreen(Game game) {
		super(game);
		this.batch = new SpriteBatch();
		this.img = new Texture("./badlogic.jpg");
		System.out.println("You have losed!!!");
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.batch.begin();
		this.batch.draw(img, 0, 0);
		this.batch.end();
	}

}
