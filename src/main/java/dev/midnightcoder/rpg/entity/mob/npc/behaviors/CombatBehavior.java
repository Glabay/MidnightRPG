package dev.midnightcoder.rpg.entity.mob.npc.behaviors;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.entity.mob.Mob;
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
        Mob target = null;
        if (npc instanceof Monster monster)
            target = monster.getTarget();
        else if (npc.getCombat() != null)
            target = npc.getCombat().getTarget();

        if (target == null || target.isDead()) {
            if (target != null && target.isDead()) {
                if (npc instanceof Monster monster)
                    monster.setTarget(null);
                if (npc.getCombat() != null)
                    npc.getCombat().setTarget(null);
            }
            super.update(npc, delta);
            return;
        }

        int attackRange = 1;
        if (npc instanceof Monster monster)
            attackRange = monster.getAttackRange();
        else if (npc.getCombat() != null)
            attackRange = npc.getCombat().getAttackRange();

        var distance = Vec2i.getDistance(npc.getPosition(), target.getPosition());
        if (distance <= (attackRange << 5)) {
            // Target is in attack range; combat tick handles executing the attack
            return;
        }
        move(npc, delta, target.getPosition());
    }

    @Override
    public void onInteraction(Mob npc, Player player) {
        if (npc instanceof Monster monster)
            monster.setTarget(player);
        if (npc.getCombat() != null)
            npc.getCombat().retaliate(player);
    }
}
