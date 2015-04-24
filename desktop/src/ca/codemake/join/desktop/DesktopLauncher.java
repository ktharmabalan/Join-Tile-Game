package ca.codemake.join.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ca.codemake.join.Join;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = Join.title;
        config.width = Join.WIDTH;
        config.height = Join.HEIGHT;
		new LwjglApplication(new Join(), config);
	}
}
