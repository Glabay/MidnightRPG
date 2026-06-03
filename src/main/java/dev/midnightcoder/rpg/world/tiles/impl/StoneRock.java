package dev.midnightcoder.rpg.world.tiles.impl;

import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import dev.midnightcoder.rpg.world.tiles.GameObjectTile;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-20
 */
public class StoneRock extends GameObjectTile {
    public StoneRock(String id, Texture texture) {
        super(id, texture, CollisionFlag.FULL);
    }


    @Override
    protected void loadGameObject(String tileId) {

    }
}
