package ca.codemake.join.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

/**
 * Created by Kajan on 4/22/2015.
 */
public class TextureAtlasLoader {
    private HashMap<String, TextureAtlas> atlases;

    public TextureAtlasLoader() {
        atlases = new HashMap<String, TextureAtlas>();
    }

    public void loadAtlas(String path, String key) {
        atlases.put(key, new TextureAtlas(Gdx.files.internal(path)));
    }

    public TextureAtlas getAtlas(String key) {
        return atlases.get(key);
    }
}
