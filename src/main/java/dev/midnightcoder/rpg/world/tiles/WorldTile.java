package dev.midnightcoder.rpg.world.tiles;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.engine.world.tile.Tile;
import dev.midnightcoder.engine.world.tile.TileType;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-04
 */
public class WorldTile extends TileType {
    public WorldTile(String id, Texture texture, CollisionFlag flag) {
        super(id, texture, flag.getMask());
    }

    public Rectangle getHitbox(int x, int y) {
        return new Rectangle(x, y, Tile.TILE_SIZE, Tile.TILE_SIZE);
    }
}
