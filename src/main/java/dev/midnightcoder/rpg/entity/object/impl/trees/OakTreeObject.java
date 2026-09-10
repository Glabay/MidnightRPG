package dev.midnightcoder.rpg.entity.object.impl.trees;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.util.ObjectId;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-21
 */
public class OakTreeObject extends TreeObject {
    public OakTreeObject(GameMap currentMap, Vec2i position) {
        super(currentMap, position);
    }

    @Override
    protected int getObjectId() {
        return ObjectId.OAK_TREE;
    }
}
