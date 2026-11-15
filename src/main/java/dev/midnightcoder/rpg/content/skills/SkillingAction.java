package dev.midnightcoder.rpg.content.skills;

import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.scene.impl.GameScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    protected final GameScreen gameScreen;
    protected final Map<DepletedResource, GameObject> depletedResourceMap;

    protected int actionSpeed;
    protected int actionDelay;

    public SkillingAction(Player player, String name, SkillType skillType, GameScreen gameScreen, Map<DepletedResource, GameObject> depletedResourceMap) {
        this.depletedResources = new ArrayList<>();
        this.player = player;
        this.name = name;
        this.skillType = skillType;
        this.actionSpeed = 90;
        this.actionDelay = 0;
        this.gameScreen = gameScreen;
        this.depletedResourceMap = depletedResourceMap;
    }

    public abstract void process();

    protected abstract boolean isSuccessful();
    protected abstract void depleteResource();
    protected abstract void onSuccess();

}
