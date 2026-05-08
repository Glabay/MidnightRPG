package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.entity.mob.Mob;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.world.TileMap;
import dev.midnightcoder.engine.world.tile.CollisionFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static dev.midnightcoder.engine.world.tile.Tile.TILE_SIZE;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NpcMovement extends Movement {
    private final Logger logger = LoggerFactory.getLogger(NpcMovement.class);

    public NpcMovement(TileMap tileMap) {
        super(tileMap);
    }

    @Override
    public void move(Mob entity, int dx, int dy) {
        if (dx != 0) {
            entity.setX(entity.getX() + dx);
            resolveX(entity, dx);
        }
        if (dy != 0) {
            entity.setY(entity.getY() + dy);
            resolveY(entity, dy);
        }
    }

    private void resolveX(Mob entity, int dx) {
        int top = entity.getY() / TILE_SIZE;
        int bottom =(entity.getY() + entity.getHeight() - 1) / TILE_SIZE;

        if (dx > 0) {
            var right = (entity.getX() + entity.getWidth() - 1) / TILE_SIZE;
            for (int ty = top; ty <= bottom; ty++) {
                // Look left
                if (isBlocked(entity, right, ty)) {
                    entity.setX(right * TILE_SIZE - entity.getWidth());
                    break;
                }
            }
        }
        else if (dx < 0) {
            var left = entity.getX() / TILE_SIZE;
            for (int ty = top; ty <= bottom; ty++) {
                // Look left
                if (isBlocked(entity, left, ty)) {
                    entity.setX((left + 1) * TILE_SIZE);
                    break;
                }
            }
        }
    }

    private void resolveY(Mob entity, int dy) {
        var left = entity.getX() / TILE_SIZE;
        var right = (entity.getX() + entity.getWidth() -1) / TILE_SIZE;

        var top = entity.getY() / TILE_SIZE;
        var bottom = (entity.getY() + entity.getHeight() -1) / TILE_SIZE;

        for (int tx = left; tx <= right; tx++) {
            // look down
            if (dy > 0 && isBlocked(entity, tx, bottom)) {
                entity.setY(top * TILE_SIZE);
                break;
            }
            // look up
            if (dy < 0 && isBlocked(entity, tx, top)) {
                entity.setY(bottom * TILE_SIZE);
                break;
            }
        }
    }

    protected boolean isBlocked(Entity entity, int tileX, int tileY) {
        var tile = tileMap.getTile(tileX, tileY);
        if (tile == null)
            return true;
        var hasNoCollision = tile.type().getCollisionFlags() == CollisionFlag.NONE.getMask();
        if (hasNoCollision)
            return false;

        var flag = tile.type().getCollisionFlags();
        var entityHb = entity.getHitbox();
        var rec = tile.getHitbox(tileX, tileY);
        var collided = entityHb.getHitboxRectangle().intersects(rec);

        if ((flag & CollisionFlag.FULL.getMask()) != 0)
            return collided;

        if (flag == CollisionFlag.PROGRAMMATIC.getMask()) {
            // TODO: Smarter Collision detection
            return entityHb.getHitboxRectangle().intersects(rec);
        }
        return false;
    }
}
