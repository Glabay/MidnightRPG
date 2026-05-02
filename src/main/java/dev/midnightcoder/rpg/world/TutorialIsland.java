package dev.midnightcoder.rpg.world;

import dev.midnightcoder.engine.renderer.camera.Camera2D;
import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.loader.PngMapLoader;
import dev.midnightcoder.engine.world.tile.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-04-30
 */
public class TutorialIsland extends GameMap {
    // Mock textures (replace later with real sprites)
    Texture grassTex = TextureFactory.createFromImageFile("/world/tiles/ground_grass.png");
    Texture wallTex  = TextureFactory.createFromImageFile("/world/tiles/wall_stone.png");
    Texture waterTex = TextureFactory.createFromImageFile("/world/tiles/water_full.png");

    // Define tile types
    TileType GRASS = new TileType("ground_grass", grassTex, CollisionFlag.NONE.getMask());
    TileType WALL  = new TileType("wall_stone", wallTex, CollisionFlag.FULL.getMask());
    TileType WATER = new TileType("water_full", waterTex, CollisionFlag.FULL.getMask());

    public TutorialIsland() {
        IO.println("Initializing tile map for Tutorial Island");
        tileColorRegistry = new TileColorRegistry();
        tileColorRegistry.register(TileColor.GRASS_PLAIN, GRASS);

        var mapPath = "/world/map/tutorial/map_landscape.png";
        tileMap = new PngMapLoader(tileColorRegistry).loadMapFile(mapPath);

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
