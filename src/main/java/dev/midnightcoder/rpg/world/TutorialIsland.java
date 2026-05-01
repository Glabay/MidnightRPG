package dev.midnightcoder.rpg.world;

import dev.midnightcoder.engine.renderer.camera.Camera2D;
import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.window.WindowConfig;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.TileMap;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.engine.world.tile.TileType;

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

    public void initTileMap() {
        initializeCamera();

        mapWidth = 32;
        mapHeight = 24;

        tileMap = new TileMap(mapWidth, mapHeight);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                TileType type = GRASS;
                // Border walls
                if (x == 0 || y == 0 ||
                    x == mapWidth - 1 ||
                    y == mapHeight - 1
                ) type = WALL;
                // Simple water patch
                if (x > 5 && x < 10 &&
                    y > 5 && y < 10
                ) type = WATER;

                tileMap.setTile(x, y, new Tile(x, y, type));
            }
        }
    }

    private void initializeCamera() {
        var viewWidth = WindowConfig.getWindowWidth();
        var viewHeight = WindowConfig.getWindowHeight();

        var worldWidth = mapWidth * Tile.TILE_SIZE;
        var worldHeight = mapHeight * Tile.TILE_SIZE;

        camera = new Camera2D(viewWidth, viewHeight, worldWidth, worldHeight);
    }
}
