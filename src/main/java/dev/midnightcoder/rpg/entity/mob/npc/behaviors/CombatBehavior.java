package dev.midnightcoder.rpg.entity.mob.npc.behaviors;

import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.npc.Behavior;
import dev.midnightcoder.rpg.entity.mob.npc.monster.Monster;
import dev.midnightcoder.rpg.entity.mob.player.Player;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class CombatBehavior implements Behavior {
    @Override
    public void update(Mob npc, double delta) {

    }

    @Override
    public void onInteraction(Mob npc, Player player) {
        if (npc instanceof Monster monster) {
            monster.setTarget(player);
        }
    }
}
