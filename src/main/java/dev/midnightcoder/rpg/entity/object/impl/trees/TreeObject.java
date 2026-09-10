package dev.midnightcoder.rpg.entity.object.impl.trees;

import dev.midnightcoder.engine.renderer.Renderer;
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
public abstract class TreeObject extends GameObject {
    public TreeObject(GameMap currentMap, Vec2i position) {
        super(currentMap, position, 64, 64);
    }

    @Override
    public void render(Renderer renderer) {
        var screenX = (int) (worldX - getCurrentMap().getCamera().getX()) - (width / 4);
        var screenY = (int) (worldY - getCurrentMap().getCamera().getY()) - (height / 2);
        renderer.renderImage(getImage(), screenX, screenY, width, height);
    }
}
