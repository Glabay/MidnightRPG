package dev.midnightcoder.rpg.assets;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.engine.world.TileMap;
import dev.midnightcoder.engine.world.loader.MapLoader;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.rpg.assets.tiles.TileColorRegistry;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.util.UUID;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class PngMapLoader extends MapLoader {

    public TileMap loadMapFile(String uuid) {
        try {
            var cacheReader = CacheReader.getInstance();
            var mapDefinition = cacheReader.getCacheManager().getMap(UUID.fromString(uuid));
            var mapBytes = mapDefinition.getPngData();
            var image = ImageIO.read(new ByteArrayInputStream(mapBytes));
            if (image == null)
                throw new RuntimeException("Failed to load map image from bytes: " + uuid);

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
        catch (Exception e) {
            throw new RuntimeException("Failed to load map file: " + uuid, e);
        }
    }
}
