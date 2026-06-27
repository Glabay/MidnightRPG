package dev.midnightcoder.rpg.entity.mob.npc.monster.impl;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.mob.npc.monster.Monster;
import dev.midnightcoder.rpg.entity.skill.SkillSet;
import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.util.NpcId;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-19
 */
public class Chicken extends Monster {

    public Chicken(int tileX, int tileY, GameMap currentMap) {
        super(NpcId.CHICKEN, new Vec2i(tileX << 5, tileY << 5), currentMap);
        setLevel(SkillType.HITPOINTS, 5);
    }
}
