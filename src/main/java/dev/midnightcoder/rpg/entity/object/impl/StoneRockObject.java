package dev.midnightcoder.rpg.entity.object.impl;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.util.ObjectId;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-06-07
 */
public class StoneRockObject extends GameObject {
    public StoneRockObject(GameMap currentMap, Vec2i position) {
        super(currentMap, position);
    }

    @Override
    protected int getObjectId() {
        return ObjectId.STONE_ROCK;
    }
}
