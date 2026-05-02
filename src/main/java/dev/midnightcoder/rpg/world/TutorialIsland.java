package dev.midnightcoder.rpg.world;

import dev.midnightcoder.engine.renderer.camera.Camera2D;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.loader.PngMapLoader;
import dev.midnightcoder.engine.world.tile.Tile;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class TutorialIsland extends GameMap {

    public TutorialIsland() {
        IO.println("Initializing tile map for Tutorial Island");
        var mapPath = "/world/map/tutorial/map_landscape.png";
        tileMap = new PngMapLoader().loadMapFile(mapPath);

        mapWidth = tileMap.width;
        mapHeight = tileMap.height;

        initializeCamera();
    }

    private void initializeCamera() {
        var viewWidth = WindowConfig.getWindowWidth();
        var viewHeight = WindowConfig.getWindowHeight();

        var worldWidth = mapWidth * Tile.TILE_SIZE;
        var worldHeight = mapHeight * Tile.TILE_SIZE;

        camera = new Camera2D(viewWidth, viewHeight, worldWidth, worldHeight);
    }
}
