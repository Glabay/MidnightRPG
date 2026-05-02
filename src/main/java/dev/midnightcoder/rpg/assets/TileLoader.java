package dev.midnightcoder.rpg.assets;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.engine.world.tile.TileColor;
import dev.midnightcoder.engine.world.tile.TileColorRegistry;
import dev.midnightcoder.engine.world.tile.TileType;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public class TileLoader {
    // Mock textures (replace later with real sprites)
    Texture grassTex = TextureFactory.createFromImageFile("/world/tiles/ground_grass.png");
    Texture wallStoneTex  = TextureFactory.createFromImageFile("/world/tiles/wall_stone.png");
    Texture wallStoneCapTex  = TextureFactory.createFromImageFile("/world/tiles/wall_stone_cap.png");
    Texture wallWoodenTex  = TextureFactory.createFromImageFile("/world/tiles/wall_wood.png");
    Texture wallWoodenCapTex  = TextureFactory.createFromImageFile("/world/tiles/wall_wood_cap.png");
    Texture waterTex = TextureFactory.createFromImageFile("/world/tiles/water_full.png");
    Texture farmPlotTex = TextureFactory.createFromImageFile("/world/tiles/farm_plot.png");

    Texture dirtPathTex = TextureFactory.createFromImageFile("/world/tiles/ground_dirt.png");
    Texture stonePathTex = TextureFactory.createFromImageFile("/world/tiles/ground_stone.png");
    Texture woodFloorTex = TextureFactory.createFromImageFile("/world/tiles/floor_wood.png");

    // Define tile types
    TileType GRASS = new TileType("ground_grass", grassTex, CollisionFlag.NONE.getMask());
    TileType WALL_STONE  = new TileType("wall_stone", wallStoneTex, CollisionFlag.FULL.getMask());
    TileType WALL_STONE_CAP  = new TileType("wall_stone_cap", wallStoneCapTex, CollisionFlag.FULL.getMask());
    TileType WALL_WOODEN  = new TileType("wall_wood", wallWoodenTex, CollisionFlag.FULL.getMask());
    TileType WALL_WOODEN_CAP  = new TileType("wall_wood_cap", wallWoodenCapTex, CollisionFlag.FULL.getMask());
    TileType STONE_PATH = new TileType("ground_stone", stonePathTex, CollisionFlag.NONE.getMask());
    TileType WOOD_FLOOR = new TileType("floor_wood", woodFloorTex, CollisionFlag.NONE.getMask());
    TileType WATER = new TileType("water_full", waterTex, CollisionFlag.FULL.getMask());
    TileType FARM_PLOT = new TileType("farm_plot", farmPlotTex, CollisionFlag.NONE.getMask());

    private static TileLoader instance;

    public static TileLoader getInstance() {
        if (instance == null) {
            instance = new TileLoader();
        }
        return instance;
    }

    public void loadTiles() {
        var registry = TileColorRegistry.getInstance();
            registry.register(TileColor.GRASS_PLAIN, GRASS);
            registry.register(TileColor.STONE_WALL, WALL_STONE);
            registry.register(TileColor.STONE_WALL_TOP, WALL_STONE_CAP);
            registry.register(TileColor.WOOD_WALL, WALL_WOODEN);
            registry.register(TileColor.WOOD_WALL_TOP, WALL_WOODEN_CAP);
            registry.register(TileColor.STONE_PATH, STONE_PATH);
            registry.register(TileColor.WOOD_FLOOR, WOOD_FLOOR);
            registry.register(TileColor.WATER_TILE, WATER);
            registry.register(TileColor.GARDEN_PATCH, FARM_PLOT);
    }
}
