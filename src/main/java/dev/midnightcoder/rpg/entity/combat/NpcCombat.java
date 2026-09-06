package dev.midnightcoder.rpg.entity.combat;

import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.monster.Monster;
import dev.midnightcoder.rpg.entity.skill.SkillType;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-09-05
 */
public class NpcCombat extends Combat {
    public NpcCombat(NPC owner) {
        super(owner, DEFAULT_ATTACK_SPEED, DEFAULT_ATTACK_RANGE);
    }

    @Override
    public void attack(Mob target) {
        if (target == null || target.isDead() || (getOwner() != null && getOwner().isDead())) {
            reset();
            return;
        }

        int damage = calculateDamage();
        target.applyHit(damage, getOwner());

        if (target.isDead())
            reset();
    }

    @Override
    public int calculateDamage() {
        int maxHit = 1;
        if (getOwner() instanceof Monster monster &&
            monster.getSkillSet() != null
        ) {
            var atkSkill = monster.getSkillSet().getSkill(SkillType.ATTACK);
            var strSkill = monster.getSkillSet().getSkill(SkillType.STRENGTH);
            int atkLevel = atkSkill != null ? atkSkill.getLevel() : 1;
            int strLevel = strSkill != null ? strSkill.getLevel() : 1;
            maxHit = Math.max(1, (atkLevel + strLevel) / 2);
        }
        else if (getOwner() instanceof NPC npc &&
            npc.getDefinition() != null
        ) {
            var def = npc.getDefinition();
            if (def.getAttack() > 1 || def.getStrength() > 1) {
                maxHit = Math.max(1, (def.getAttack() + def.getStrength()) / 2);
            }
            else if (def.getCombatLevel() > 0) {
                maxHit = Math.max(1, def.getCombatLevel() / 2);
            }
        }
        return RANDOM.nextInt(maxHit + 1);
    }

    @Override
    public void retaliate(Mob attacker) {
        super.retaliate(attacker);
        if (getOwner() instanceof Monster monster) {
            monster.setTarget(attacker);
        }
    }
}
