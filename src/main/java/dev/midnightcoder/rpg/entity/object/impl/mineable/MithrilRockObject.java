package dev.midnightcoder.rpg.entity.object.impl.mineable;

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
public class MithrilRockObject extends GameObject {
    public MithrilRockObject(GameMap currentMap, Vec2i position) {
        super(currentMap, position);
    }

    @Override
    protected int getObjectId() {
        return ObjectId.MITHRIL_ROCK;
    }
}
