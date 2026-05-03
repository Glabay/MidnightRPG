package dev.midnightcoder.rpg.entity.skill;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class Skill {
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

    // TODO: work out some real level calculation logic
    public void recalculateLevel() {
        // temp... every 100 exp
        this.level = (int) (1 + (this.experience / 100));
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    protected void onLevelUp(int oldLevel, int newLevel) {}
}
