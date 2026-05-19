package dev.midnightcoder.rpg.entity.mob.npc.behaviors;

import dev.midnightcoder.engine.entity.Direction;
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

    @Override
    public void update(NPC npc, double delta) {
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
