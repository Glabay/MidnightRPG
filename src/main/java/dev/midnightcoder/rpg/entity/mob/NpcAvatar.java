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

    public void setDirection(Direction direction) {
        this.direction = direction;
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
}
