package dev.midnightcoder.rpg.entity.mob.npc.behaviors;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.npc.Behavior;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.monster.Monster;
import dev.midnightcoder.rpg.entity.mob.player.Player;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class CombatBehavior extends WanderBehavior {

    public CombatBehavior(NPC npc) {
        super(npc, npc.getWalkRadius());
    }

    @Override
    public void update(Mob npc, double delta) {
        // handle follow logic
        if (npc instanceof Monster monster &&
            monster.getTarget() == null
        ) {
            super.update(npc, delta);
            return;
        }
        if (npc instanceof Monster monster) {
            var target = monster.getTarget();
            if (target == null) {
                super.update(npc, delta);
                return;
            }

            var distance = Vec2i.getDistance(monster.getPosition(), target.getPosition());
            if (distance <= monster.getAttackRange() << 5) {
                // process mob combat

                return;
            }
            move(npc, delta, target.getPosition());
        }
    }

    @Override
    public void onInteraction(Mob npc, Player player) {
        if (npc instanceof Monster monster) {
            monster.setTarget(player);
        }
    }
}
