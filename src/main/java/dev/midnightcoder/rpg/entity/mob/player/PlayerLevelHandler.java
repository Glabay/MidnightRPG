package dev.midnightcoder.rpg.entity.mob.player;

import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.save.SaveManager;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public class PlayerLevelHandler {

    public static void onLevelUp(Player player, SkillType skillType, int oldLevel, int newLevel) {
        if (skillType == SkillType.HITPOINTS) {
            player.getCombatStats().heal(newLevel - oldLevel, newLevel);
        }
        // Auto-save after level-up
        SaveManager.getInstance().savePlayer(player);
    }
}
