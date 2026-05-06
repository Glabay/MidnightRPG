package dev.midnightcoder.rpg.entity.skill;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public enum SkillType {
    ATTACK,
    STRENGTH,
    DEFENCE,
    RANGED,
    MAGIC,
    HITPOINTS
    ;
    public String getDisplayName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
