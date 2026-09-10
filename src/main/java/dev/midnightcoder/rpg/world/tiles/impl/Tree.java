package dev.midnightcoder.rpg.world.tiles.impl;

import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.rpg.world.tiles.GameObjectTile;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-20
 */
public class Tree extends GameObjectTile {
    public Tree(String id) {
        super(id, null, CollisionFlag.FULL);
    }
}
