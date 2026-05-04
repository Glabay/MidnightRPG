package dev.midnightcoder.rpg.assets;

import dev.midnightcoder.rpg.assets.tiles.TileLoader;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public class AssetManager {
    private static AssetManager assetManager;

    public static AssetManager getInstance() {
        if (assetManager == null) {
            assetManager = new AssetManager();
        }
        return assetManager;
    }

    public void loadTiles() {
        IO.println("Loading tile assets");
        TileLoader.getInstance().loadTiles();
    }
}
