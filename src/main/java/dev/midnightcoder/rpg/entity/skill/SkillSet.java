package dev.midnightcoder.rpg.entity.skill;

import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.mob.player.PlayerLevelHandler;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class SkillSet {
    private final Map<SkillType, Skill> skills = new EnumMap<>(SkillType.class);

    public SkillSet(Player owner) {
        for (var skillType : SkillType.values()) {
            skills.put(skillType, getDefaultSkill(owner, skillType));
        }
    }

    private Skill getDefaultSkill(Player owner, SkillType skillType) {
        return new Skill(skillType) {
            @Override
            protected void onLevelUp(int oldLevel, int newLevel) {
                PlayerLevelHandler.onLevelUp(owner, getSkillType(), oldLevel, newLevel);
            }
        };
    }

    public List<Skill> getSkills() {
        return List.copyOf(skills.values());
    }

    public Skill getSkill(SkillType skillType) {
        return skills.get(skillType);
    }

}
