package dev.midnightcoder.rpg.world.tiles.impl;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.rpg.world.tiles.WorldTile;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-04
 */
public class WallTile extends WorldTile {

    public WallTile(String id, Texture texture) {
        super(id, texture, CollisionFlag.PROGRAMMATIC);
    }
}
