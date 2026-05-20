package dev.midnightcoder.rpg.assets;

import dev.midnightcoder.rpg.assets.tiles.TileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public class AssetManager {
    private static final Logger log = LoggerFactory.getLogger(AssetManager.class);
    private static AssetManager assetManager;

    public static AssetManager getInstance() {
        if (assetManager == null) {
            assetManager = new AssetManager();
        }
        return assetManager;
    }

    public void loadAssets() {
        log.info("Loading assets");
        loadTiles();
    }

    private void loadTiles() {
        log.info("Loading tile assets");
        TileLoader.getInstance().loadTiles();
    }
}
