package dev.midnightcoder.rpg.assets;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.engine.world.tile.TileColor;
import dev.midnightcoder.engine.world.tile.TileColorRegistry;
import dev.midnightcoder.engine.world.tile.TileType;

import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-02
 */
public class TileLoader {

    private static TileLoader instance;

    public static TileLoader getInstance() {
        if (instance == null) {
            instance = new TileLoader();
        }
        return instance;
    }

    private TileType getTileType(String textureName, CollisionFlag collisionFlag) {
        return new TileType(textureName, getTileTexture(textureName), collisionFlag.getMask());
    }

    private Texture getTileTexture(String textureName) {
        return TextureFactory.createFromImageFile("/world/tiles/" + textureName + ".png");
    }

    public void loadTiles() {
        // Load in textures
        var registry = TileColorRegistry.getInstance();
            registry.register(TileColor.GRASS_PLAIN, getTileType("ground_grass", CollisionFlag.NONE));
            registry.register(TileColor.STONE_WALL, getTileType("wall_stone", CollisionFlag.FULL));
            registry.register(TileColor.STONE_WALL_TOP, getTileType("wall_stone_cap", CollisionFlag.FULL));
            registry.register(TileColor.WOOD_WALL, getTileType("wall_wood", CollisionFlag.FULL));
            registry.register(TileColor.WOOD_WALL_TOP, getTileType("wall_wood_cap", CollisionFlag.FULL));
            registry.register(TileColor.STONE_PATH, getTileType("ground_stone", CollisionFlag.NONE));
            registry.register(TileColor.WOOD_FLOOR, getTileType("floor_wood", CollisionFlag.NONE));
            registry.register(TileColor.WATER_TILE, getTileType("water_full", CollisionFlag.FULL));
            registry.register(TileColor.GARDEN_PATCH, getTileType("farm_plot", CollisionFlag.NONE));
    }
}
