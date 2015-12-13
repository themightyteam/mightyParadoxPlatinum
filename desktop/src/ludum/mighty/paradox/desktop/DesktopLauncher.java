package ludum.mighty.paradox.desktop;

import ludum.mighty.paradox.game.MightyGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tiled Box2D Test 2";
		config.width = 320;
		config.height = 160;
		new LwjglApplication(new MightyGame(), config);
	}

}
