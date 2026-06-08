package dev.midnightcoder.rpg.assets;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.engine.world.TileMap;
import dev.midnightcoder.engine.world.loader.MapLoader;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.rpg.assets.tiles.TileColorRegistry;
import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.entity.object.impl.StoneRockObject;
import dev.midnightcoder.rpg.world.tiles.GameObjectTile;
import dev.midnightcoder.rpg.world.tiles.impl.StoneRock;
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
                    if (gameObject instanceof StoneRock) {
                        addObject(currentMap, new StoneRockObject(currentMap, new Vec2i(x, y)));
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
