package dev.midnightcoder.rpg.world.tiles;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.engine.world.tile.TileType;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-04
 */
public abstract class WorldTile extends TileType {
    public WorldTile(String id, Texture texture, CollisionFlag flag) {
        super(id, texture, flag.getMask());
    }
}
