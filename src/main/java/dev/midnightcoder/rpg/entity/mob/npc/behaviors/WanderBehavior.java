package dev.midnightcoder.rpg.entity.mob.npc.behaviors;

import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.entity.Entity;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.npc.Behavior;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.monster.Monster;

import java.util.Random;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class WanderBehavior implements Behavior {
    private final Random random = new Random();

    @Override
    public void update(Mob mob, double delta) {
        if (mob instanceof Monster monster) {
            if (monster.getTarget() != null) {
                var start = new Vec2i(monster.getX(), monster.getY());
                var dest = new Vec2i(monster.getTarget().getX(), monster.getTarget().getY());
                // if we're close enough to the target
                if (Vec2i.getDistance(start, dest) <= 1)
                    return;

                var difX = dest.getX() - start.getX();
                var difY = dest.getY() - start.getY();
                var dx = Integer.signum(difX);
                var dy = Integer.signum(difY);

                if (!monster.getMovement().isBlocked(monster.getAvatar(), dx, dy)) {
                    monster.getMovement().move(monster.getAvatar(), dx, dy);
                    return;
                }
            }
        }
        move((NPC) mob, delta);
    }

    private void move(NPC npc, double delta) {
        if (random.nextDouble() < 0.04) {
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

            var dx = (int) (npc.getAvatar().getMoveX() * npc.speed * delta);
            var dy = (int) (npc.getAvatar().getMoveY() * npc.speed * delta);

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
}
