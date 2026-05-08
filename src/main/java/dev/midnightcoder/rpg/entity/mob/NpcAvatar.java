package dev.midnightcoder.rpg.entity.mob;

import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.entity.mob.Avatar;
import dev.midnightcoder.engine.renderer.graphics.TextureFactory;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;

import java.awt.*;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NpcAvatar extends Avatar {

    public NpcAvatar(Vec2i position, Movement movement, GameMap currentMap) {
        super(position, movement, currentMap);
        width = 32;
        height = 32;

        texture = TextureFactory.createSolidColor(width, height, Color.BLUE);
    }

    @Override
    public int getMoveX() {
        var dx = 0;

        if (direction == Direction.WEST) dx -= moveSpeed;
        if (direction == Direction.EAST) dx += moveSpeed;
        return dx;
    }

    @Override
    public int getMoveY() {
        var dy = 0;
        if (direction == Direction.NORTH) dy -= moveSpeed;
        if (direction == Direction.SOUTH) dy += moveSpeed;
        return dy;
    }

    @Override
    public void update(double delta) {
        super.update(delta);
        updateHitbox();

        // TODO: Implement some way to not move every tick

        var dx = (int) (getMoveX() * speed * delta);
        var dy = (int) (getMoveY() * speed * delta);

        movement.move(this, dx, dy);
    }
}
