package dev.midnightcoder.rpg.world.tiles;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.rpg.entity.object.GameObject;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-20
 */
public abstract class GameObjectTile extends WorldTile {
    protected GameObject gameObject;

    public GameObjectTile(String id, Texture texture, CollisionFlag flag) {
        super(id, texture, flag);
    }

    protected abstract void loadGameObject(String tileId);
}
