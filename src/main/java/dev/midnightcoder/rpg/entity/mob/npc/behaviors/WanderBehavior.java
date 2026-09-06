package dev.midnightcoder.rpg.entity.mob.npc.behaviors;

import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.util.Boundary;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.npc.Behavior;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;

import java.util.Random;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class WanderBehavior implements Behavior {
    private final Random random = new Random();
    private Boundary walkingBoundary;

    public WanderBehavior(NPC npc, int radius) {
        this.walkingBoundary = new Boundary(
            new Vec2i(npc.getPosition().getX() - radius >> 5, npc.getPosition().getY() - radius >> 5),
            new Vec2i(npc.getPosition().getX() + radius >> 5, npc.getPosition().getY() + radius >> 5)
        );
    }

    public WanderBehavior(NPC npc) {
        this(npc, 4);
    }

    @Override
    public void update(Mob mob, double delta) {
        move((NPC) mob, delta);
    }

    private void rollForRandomDirection(NPC npc) {
        var roll = random.nextDouble();
        if (roll < 0.3) {
            npc.getAvatar().setDirection(Direction.NORTH);
            npc.getAvatar().animatedSprite.setFrames(npc.getDefinition().getAnimatedFrames(Direction.NORTH));
        }
        else if (roll > 0.3 && roll < 0.6) {
            npc.getAvatar().setDirection(Direction.SOUTH);
            npc.getAvatar().animatedSprite.setFrames(npc.getDefinition().getAnimatedFrames(Direction.SOUTH));
        }
        else if (roll > 0.6 && roll < 0.9) {
            npc.getAvatar().setDirection(Direction.WEST);
            npc.getAvatar().animatedSprite.setFrames(npc.getDefinition().getAnimatedFrames(Direction.WEST));
        }
        else if (roll > 0.9) {
            npc.getAvatar().setDirection(Direction.EAST);
            npc.getAvatar().animatedSprite.setFrames(npc.getDefinition().getAnimatedFrames(Direction.EAST));
        }
    }

    private void move(NPC npc, double delta) {
        if (random.nextDouble() < 0.04) {
            rollForRandomDirection(npc);

            var dx = (int) (npc.getAvatar().getMoveX() * npc.speed * delta);
            var dy = (int) (npc.getAvatar().getMoveY() * npc.speed * delta);

            move(npc, delta, dx, dy);
        }
    }

    protected void move(Mob mob, double delta, Vec2i destination) {
        // if we're close enough to the target
        if (Vec2i.getDistance(mob.getPosition(), destination) <= Math.min(mob.getHeight(), mob.getWidth()))
            return;

        var difX = destination.getX() - mob.getPosition().getX();
        var difY = destination.getY() - mob.getPosition().getY();
        var dx = (int) (Integer.signum(difX) * mob.speed * delta);
        var dy = (int) (Integer.signum(difY) * mob.speed * delta);

        move((NPC) mob, delta, dx, dy);
    }

    protected void move(NPC npc, double delta, int dx, int dy) {
        var wasMoving = npc.moving;
        npc.moving = npc.lastX != npc.getAvatar().getX() || npc.lastY != npc.getAvatar().getY();

        if (npc.moving)
            npc.getAvatar().animatedSprite.update(delta);
        else if (wasMoving)
            npc.getAvatar().animatedSprite.reset();

        npc.getAvatar().setAvatarTexture(npc.getAvatar().animatedSprite.getCurrentFrame());

        npc.lastX = npc.getAvatar().getX();
        npc.lastY = npc.getAvatar().getY();

        npc.getMovement().move(npc.getAvatar(), dx, dy);
    }
}
