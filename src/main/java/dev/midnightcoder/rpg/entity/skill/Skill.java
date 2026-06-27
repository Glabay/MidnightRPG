package dev.midnightcoder.rpg.entity.skill;

import java.text.DecimalFormat;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class Skill {
    private static final double XP_MULTIPLIER = 25;
    private static final double XP_CURVE = 2.4;

    private final SkillType skillType;

    private int level;
    private int experience;

    public Skill(SkillType skillType) {
        this.skillType = skillType;
        this.level = 1;
        this.experience = 0;
    }

    public void addExp(int amount) {
        this.experience += amount;
        var oldLevel = this.level;
        recalculateLevel();
        if (this.level > oldLevel) {
            onLevelUp(oldLevel, this.level);
        }
    }

    public void setLevel(int level) {
        this.experience = Math.toIntExact(getExperienceForLevel(level));
        recalculateLevel();
    }

    public void recalculateLevel() {
        this.level = getLevelForExp(this.experience);
    }

    private long getExperienceForLevel(int level) {
        return (long) (XP_MULTIPLIER * Math.pow(level, XP_CURVE));
    }

    private int getLevelForExp(int exp) {
        var level = 1;
        while (level < 99 && getExperienceForLevel(level + 1) <= exp) {
            level++;
        }
        return level;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public int getLevel() {
        return level;
    }

    public String getExperience() {
        return DecimalFormat.getInstance().format(experience);
    }

    public String getNextLevel() {
        return DecimalFormat.getInstance().format(getExperienceForLevel(level + 1));
    }

    protected void onLevelUp(int oldLevel, int newLevel) {}
}
