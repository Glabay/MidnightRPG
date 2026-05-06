package dev.midnightcoder.rpg.assets.tiles;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.tile.*;
import dev.midnightcoder.rpg.world.tiles.WorldTile;
import dev.midnightcoder.rpg.world.tiles.impl.GroundTile;
import dev.midnightcoder.rpg.world.tiles.impl.WallTile;
import dev.midnightcoder.rpg.world.tiles.impl.WaterTile;

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
        return new WorldTile(textureName, getTileTexture(textureName), collisionFlag);
    }

    private Texture getTileTexture(String textureName) {
        return TextureFactory.createFromImageFile("/world/tiles/" + textureName + ".png");
    }

    private Texture getTextureFromSpriteSheet(BufferedImage spriteSheet, Vec2i spritePosition) {
        return TextureFactory.createTextureFromSpriteSheet(spriteSheet, Tile.TILE_SIZE, spritePosition);
    }


    public void loadTiles() {
        var groundTiles = getTileTexture("ground_tileset").image();

        var grassFull = getTextureFromSpriteSheet(groundTiles, new Vec2i(0, 3));

        var waterGrassNorthWest = getTextureFromSpriteSheet(groundTiles, new Vec2i(0, 0));
        var waterGrassNorth = getTextureFromSpriteSheet(groundTiles, new Vec2i(1, 0));
        var waterGrassNorthEast = getTextureFromSpriteSheet(groundTiles, new Vec2i(2, 0));

        var waterGrassWest = getTextureFromSpriteSheet(groundTiles, new Vec2i(0, 1));
        var waterFull = getTextureFromSpriteSheet(groundTiles, new Vec2i(1, 1));
        var waterGrassEast = getTextureFromSpriteSheet(groundTiles, new Vec2i(2, 1));

        var waterGrassSouthWest = getTextureFromSpriteSheet(groundTiles, new Vec2i(0, 2));
        var waterGrassSouth = getTextureFromSpriteSheet(groundTiles, new Vec2i(1, 2));
        var waterGrassSouthEast = getTextureFromSpriteSheet(groundTiles, new Vec2i(2, 2));

        var dirtGrassNorthWest = getTextureFromSpriteSheet(groundTiles, new Vec2i(3, 0));
        var dirtGrassNorth = getTextureFromSpriteSheet(groundTiles, new Vec2i(4, 0));
        var dirtGrassNorthEast = getTextureFromSpriteSheet(groundTiles, new Vec2i(5, 0));

        var dirtGrassNorthWestCorner = getTextureFromSpriteSheet(groundTiles, new Vec2i(4, 4));
        var dirtGrassNorthEastCorner = getTextureFromSpriteSheet(groundTiles, new Vec2i(3, 4));
        var dirtGrassSouthWestCorner = getTextureFromSpriteSheet(groundTiles, new Vec2i(4, 3));
        var dirtGrassSouthEastCorner = getTextureFromSpriteSheet(groundTiles, new Vec2i(3, 3));

        var dirtGrassWest = getTextureFromSpriteSheet(groundTiles, new Vec2i(3, 1));
        var dirtFull = getTextureFromSpriteSheet(groundTiles, new Vec2i(4, 1));
        var dirtGrassEast = getTextureFromSpriteSheet(groundTiles, new Vec2i(5, 1));

        var dirtGrassSouthWest = getTextureFromSpriteSheet(groundTiles, new Vec2i(3, 2));
        var dirtGrassSouth = getTextureFromSpriteSheet(groundTiles, new Vec2i(4, 2));
        var dirtGrassSouthEast = getTextureFromSpriteSheet(groundTiles, new Vec2i(5, 2));


        // Load in textures
        var registry = TileColorRegistry.getInstance();
            registry.register(TileColor.GRASS_PLAIN, new GroundTile("ground_grass", grassFull));
            registry.register(TileColor.DIRT_GRASS_SOUTH_WEST, new GroundTile("ground_dirt_grass_sw", dirtGrassSouthWest));
            registry.register(TileColor.DIRT_GRASS_SOUTH_EAST, new GroundTile("ground_dirt_grass_se", dirtGrassSouthEast));
            registry.register(TileColor.DIRT_GRASS_NORTH_EAST, new GroundTile("ground_dirt_grass_ne", dirtGrassNorthEast));
            registry.register(TileColor.DIRT_GRASS_NORTH_WEST, new GroundTile("ground_dirt_grass_nw", dirtGrassNorthWest));
            registry.register(TileColor.DIRT_GRASS_NORTH, new GroundTile("ground_dirt_grass_n", dirtGrassNorth));
            registry.register(TileColor.DIRT_GRASS_EAST, new GroundTile("ground_dirt_grass_e", dirtGrassEast));
            registry.register(TileColor.DIRT_GRASS_WEST, new GroundTile("ground_dirt_grass_w", dirtGrassWest));
            registry.register(TileColor.DIRT_GRASS_SOUTH, new GroundTile("ground_dirt_grass_s", dirtGrassSouth));
            registry.register(TileColor.DIRT_GRASS_NORTH_WEST_CORNER, new GroundTile("ground_dirt_grass_nw_corner", dirtGrassNorthWestCorner));
            registry.register(TileColor.DIRT_GRASS_NORTH_EAST_CORNER, new GroundTile("ground_dirt_grass_ne_corner", dirtGrassNorthEastCorner));
            registry.register(TileColor.DIRT_GRASS_SOUTH_WEST_CORNER, new GroundTile("ground_dirt_grass_sw_corner", dirtGrassSouthWestCorner));
            registry.register(TileColor.DIRT_GRASS_SOUTH_EAST_CORNER, new GroundTile("ground_dirt_grass_se_corner", dirtGrassSouthEastCorner));
            registry.register(TileColor.WATER_GRASS_NORTH, new WaterTile("ground_water_grass_n", waterGrassNorth));
            registry.register(TileColor.WATER_GRASS_WEST, new WaterTile("ground_water_grass_w", waterGrassWest));
            registry.register(TileColor.WATER_GRASS_NORTH_WEST, new WaterTile("ground_water_grass_nw", waterGrassNorthWest));
            registry.register(TileColor.DIRT_FULL, new GroundTile("ground_dirt_full", dirtFull));
            registry.register(TileColor.WATER_TILE, new WaterTile("water_full", waterFull));

            registry.register(TileColor.STONE_WALL, getTileType("wall_stone", CollisionFlag.FULL));
            registry.register(TileColor.STONE_WALL_TOP, getTileType("wall_stone_cap", CollisionFlag.FULL));
            registry.register(TileColor.WOOD_WALL, getTileType("wall_wood", CollisionFlag.FULL));
            registry.register(TileColor.WOOD_WALL_TOP, getTileType("wall_wood_cap", CollisionFlag.FULL));
            registry.register(TileColor.STONE_PATH, getTileType("ground_stone", CollisionFlag.NONE));
            registry.register(TileColor.WOOD_FLOOR, getTileType("floor_wood", CollisionFlag.NONE));
            registry.register(TileColor.GARDEN_PATCH, getTileType("farm_plot", CollisionFlag.NONE));
    }
}
