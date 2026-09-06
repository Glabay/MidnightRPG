package dev.midnightcoder.rpg.entity.mob.npc.behaviors;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.rpg.MidnightRPG;
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
public class AggressiveBehavior implements Behavior {
    private final int aggroRadius;

    public AggressiveBehavior(int aggroRadius) {
        this.aggroRadius = aggroRadius;
    }

    public AggressiveBehavior() {
        this(4);
    }

    @Override
    public void update(Mob npc, double delta) {
        if (npc.isDead()) return;


        Mob target = null;
        if (npc instanceof Monster monster)
            target = monster.getTarget();
        else if (npc.getCombat() != null)
            target = npc.getCombat().getTarget();

        if (target != null) {
            if (target.isDead()) {
                if (npc instanceof Monster monster)
                    monster.setTarget(null);
                if (npc.getCombat() != null)
                    npc.getCombat().setTarget(null);
            }
            else return;
        }

        Player player = null;
        if (MidnightRPG.getInstance() != null &&
            MidnightRPG.getInstance().getGameScreen() != null
        ) player = MidnightRPG.getInstance().getGameScreen().getPlayer();

        if (player != null && !player.isDead()) {
            double distance = Vec2i.getDistance(npc.getPosition(), player.getPosition());
            if (distance <= (aggroRadius << 5)) {
                if (npc instanceof Monster monster)
                    monster.setTarget(player);
                if (npc.getCombat() != null)
                    npc.getCombat().retaliate(player);
            }
        }
    }
}
