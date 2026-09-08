package dev.midnightcoder.rpg.content.skills;

import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.skill.SkillType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-09-06
 */
public abstract class SkillingAction {
    protected final List<DepletedResource> depletedResources;
    protected final String name;
    protected final SkillType skillType;
    protected final Player player;

    protected int actionSpeed;
    protected int actionDelay;

    public SkillingAction(Player player, String name, SkillType skillType) {
        this.depletedResources = new ArrayList<>();
        this.player = player;
        this.name = name;
        this.skillType = skillType;
        this.actionSpeed = 90;
        this.actionDelay = 0;
    }

    public abstract void process();

    protected abstract boolean isSuccessful();
    protected abstract void depleteResource();
    protected abstract void onSuccess();

}
