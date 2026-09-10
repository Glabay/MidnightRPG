package dev.midnightcoder.rpg.assets;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.TileMap;
import dev.midnightcoder.engine.world.loader.MapLoader;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.rpg.assets.tiles.TileColorRegistry;
import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.entity.object.impl.bushes.BushesBerryObject;
import dev.midnightcoder.rpg.entity.object.impl.bushes.BushesObject;
import dev.midnightcoder.rpg.entity.object.impl.mineable.*;
import dev.midnightcoder.rpg.entity.object.impl.trees.MapleTreeObject;
import dev.midnightcoder.rpg.entity.object.impl.trees.NormalTreeObject;
import dev.midnightcoder.rpg.entity.object.impl.trees.OakTreeObject;
import dev.midnightcoder.rpg.entity.object.impl.trees.WillowTreeObject;
import dev.midnightcoder.rpg.entity.object.impl.trees.stump.*;
import dev.midnightcoder.rpg.world.tiles.GameObjectTile;
import dev.midnightcoder.rpg.world.tiles.impl.Bushes;
import dev.midnightcoder.rpg.world.tiles.impl.MineableRock;
import dev.midnightcoder.rpg.world.tiles.impl.Tree;
import dev.midnightcoder.rpg.world.tiles.impl.TreeStump;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class PngMapLoader extends MapLoader {
    private static final Logger log = LoggerFactory.getLogger(PngMapLoader.class);

    public TileMap loadMapFile(String uuid) {
        var cacheReader = CacheReader.getInstance();
        var mapDefinition = cacheReader.getCacheManager().getMap(UUID.fromString(uuid));
        var mapBytes = mapDefinition.getPngData();
        BufferedImage image;
        try {
            image = ImageIO.read(new ByteArrayInputStream(mapBytes));
            if (image == null)
                throw new RuntimeException("Failed to load map image from bytes: " + uuid);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load map file: " + uuid, e);
        }
        var width = image.getWidth();
        var height = image.getHeight();
        var tileMap = new TileMap(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                var pixel = image.getRGB(x, y);
                var hexStr = String.format("0x%08X", pixel);
                var tileType = TileColorRegistry.getInstance().getTileType(hexStr);
                if (tileType == null)
                    continue;
                tileMap.setTile(x, y, new Tile(x, y, tileType));
            }
        }
        return tileMap;
    }

    public void loadObjectMapFile(GameMap currentMap, String uuid) {
        var cacheReader = CacheReader.getInstance();
        var mapDefinition = cacheReader.getCacheManager().getMap(UUID.fromString(uuid));
        var mapBytes = mapDefinition.getPngData();
        BufferedImage image;
        try {
            image = ImageIO.read(new ByteArrayInputStream(mapBytes));
            if (image == null)
                throw new RuntimeException("Failed to load map image from bytes: " + uuid);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to load object map file: " + uuid, e);
        }
        var width = image.getWidth();
        var height = image.getHeight();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                var pixel = image.getRGB(x, y);
                var hexStr = String.format("0x%08X", pixel);
                // based on the hexStr, fetch the applicable GameObject
                var tileType = TileColorRegistry.getInstance().getTileType(hexStr);
                if (tileType == null) continue;
                // create a GameObject at the x, y
                if (tileType instanceof GameObjectTile gameObject) {
                    log.debug("Creating {} at ({}, {})", gameObject.getClass().getSimpleName(), x, y);
                    // TODO: Figure out a better way to load these Objects
                    switch (gameObject) {
                        case MineableRock rock -> {
                            switch (rock.getId()) {
                                case "stone_rock" -> addObject(currentMap, new StoneRockObject(currentMap, new Vec2i(x, y)));
                                case "copper_rock" -> addObject(currentMap, new CopperRockObject(currentMap, new Vec2i(x, y)));
                                case "tin_rock" -> addObject(currentMap, new TinRockObject(currentMap, new Vec2i(x, y)));
                                case "iron_rock" -> addObject(currentMap, new IronRockObject(currentMap, new Vec2i(x, y)));
                                case "coal_rock" -> addObject(currentMap, new CoalRockObject(currentMap, new Vec2i(x, y)));
                                case "mithril_rock" -> addObject(currentMap, new MithrilRockObject(currentMap, new Vec2i(x, y)));
                            }
                        }
                        case TreeStump treeStump -> {
                            switch (treeStump.getId()) {
                                case "normal_stump" -> addObject(currentMap, new TreeStumpEntity(currentMap, new Vec2i(x, y)));
                                case "normal_stump_pink" -> addObject(currentMap, new TreeStumpPinkObject(currentMap, new Vec2i(x, y)));
                                case "normal_stump_orange" -> addObject(currentMap, new TreeStumpOrangeObject(currentMap, new Vec2i(x, y)));
                                case "normal_stump_yellow" -> addObject(currentMap, new TreeStumpYellowObject(currentMap, new Vec2i(x, y)));
                                case "normal_stump_red" -> addObject(currentMap, new TreeStumpRoseObject(currentMap, new Vec2i(x, y)));
                                case "normal_stump_red2" -> addObject(currentMap, new TreeStumpTulipObject(currentMap, new Vec2i(x, y)));
                            }
                        }
                        case Bushes bushes -> {
                            switch (bushes.getId()) {
                                case "normal_bush" -> addObject(currentMap, new BushesObject(currentMap, new Vec2i(x, y)));
                                case "berry_bush" -> addObject(currentMap, new BushesBerryObject(currentMap, new Vec2i(x, y)));
                            }
                        }
                        case Tree tree -> {
                            switch (tree.getId()) {
                                case "normal_tree" -> addObject(currentMap, new NormalTreeObject(currentMap, new Vec2i(x, y)));
                                case "oak_tree" -> addObject(currentMap, new OakTreeObject(currentMap, new Vec2i(x, y)));
                                case "maple_tree" -> addObject(currentMap, new MapleTreeObject(currentMap, new Vec2i(x, y)));
                                case "willow_tree" -> addObject(currentMap, new WillowTreeObject(currentMap, new Vec2i(x, y)));
                            }
                        }
                        default -> log.warn("Unknown Object type: {}", gameObject.getId());
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void addObject(GameMap currentMap, GameObject object) {
        ((List<GameObject>) currentMap.getGameObjects()).add(object);
    }
}
