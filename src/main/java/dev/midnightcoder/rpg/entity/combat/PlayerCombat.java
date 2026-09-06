package dev.midnightcoder.rpg.entity.combat;

import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.skill.SkillType;

import java.util.Random;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-09-05
 */
public class PlayerCombat extends Combat {
    public PlayerCombat(Player owner) {
        super(owner, 60, 1);
    }

    public PlayerCombat(Player owner, int attackSpeed) {
        super(owner, attackSpeed, 1);
    }

    public PlayerCombat(Player owner, int attackSpeed, int attackRange) {
        super(owner, attackSpeed, attackRange);
    }

    @Override
    public void attack(Mob target) {
        if (target == null || target.isDead() || (getOwner() != null && getOwner().isDead())) {
            reset();
            return;
        }

        int damage = calculateDamage();
        target.applyHit(damage, getOwner());

        if (getOwner() instanceof Player player) {
            int xpGained = Math.max(1, damage * 4);
            if (player.getSkillSet() != null) {
                if (player.getSkillSet().getSkill(SkillType.ATTACK) != null) {
                    player.getSkillSet().getSkill(SkillType.ATTACK).addExp(xpGained);
                }
                if (player.getSkillSet().getSkill(SkillType.HITPOINTS) != null) {
                    player.getSkillSet().getSkill(SkillType.HITPOINTS).addExp(Math.max(1, damage));
                }
            }
        }

        if (target.getCombat() != null) {
            target.getCombat().retaliate(getOwner());
        }

        if (target.isDead()) {
            reset();
        }
    }

    @Override
    public int calculateDamage() {
        int maxHit = 1;
        if (getOwner() instanceof Player player && player.getSkillSet() != null) {
            var strSkill = player.getSkillSet().getSkill(SkillType.STRENGTH);
            var atkSkill = player.getSkillSet().getSkill(SkillType.ATTACK);
            int strLevel = strSkill != null ? strSkill.getLevel() : 1;
            int atkLevel = atkSkill != null ? atkSkill.getLevel() : 1;
            maxHit = Math.max(1, (strLevel + atkLevel) / 2);
        }
        return RANDOM.nextInt(maxHit + 1);
    }
}
