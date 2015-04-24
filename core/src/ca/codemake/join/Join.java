package ca.codemake.join;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ca.codemake.join.handlers.TextureAtlasLoader;
import ca.codemake.join.state.GSM;
import ca.codemake.join.state.PlayState;

public class Join extends ApplicationAdapter {

    public static final String title = "Join";

    public static final int HEIGHT = 480;
    public static final int WIDTH = 800;

    private GSM gsm;
    private SpriteBatch sb;
    public static TextureAtlasLoader tal;

	public void create () {

        tal = new TextureAtlasLoader();
        tal.loadAtlas("pack.pack", "pack");
        sb = new SpriteBatch();
        gsm = new GSM();
        gsm.push(new PlayState(gsm));

    }

	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(sb);
	}
}
