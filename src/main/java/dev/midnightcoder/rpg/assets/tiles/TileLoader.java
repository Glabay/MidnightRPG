package dev.midnightcoder.rpg.assets.tiles;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.tile.*;
import dev.midnightcoder.rpg.world.tiles.impl.GroundTile;

import java.awt.image.BufferedImage;

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

    private Texture getTextureFromSpriteSheet(BufferedImage spriteSheet, Vec2i spritePosition) {
        return TextureFactory.createTextureFromSpriteSheet(spriteSheet, Tile.TILE_SIZE, spritePosition);
    }


    public void loadTiles() {
        var groundTiles = getTileTexture("ground_tileset");

        var grassFull = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(0, 3));

        var waterGrassNorthWest = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(0, 0));
        var waterGrassNorth = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(0, 1));
        var waterGrassNorthEast = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(0, 2));

        var waterGrassWest = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(1, 0));
        var waterFull = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(1, 1));
        var waterGrassEast = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(1, 2));

        var waterGrassSouthWest = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(2, 0));
        var waterGrassSouth = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(2, 1));
        var waterGrassSouthEast = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(2, 2));

        var dirtGrassNorthWest = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(3, 0));
        var dirtGrassNorth = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(3, 1));
        var dirtGrassNorthEast = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(3, 2));

        var dirtGrassWest = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(4, 0));
        var dirtFull = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(4, 1));
        var dirtGrassEast = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(4, 2));

        var dirtGrassSouthWest = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(5, 0));
        var dirtGrassSouth = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(5, 1));
        var dirtGrassSouthEast = getTextureFromSpriteSheet(groundTiles.image(), new Vec2i(5, 2));


        // Load in textures
        var registry = TileColorRegistry.getInstance();
            registry.register(TileColor.GRASS_PLAIN, new GroundTile("ground_grass", grassFull));
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
