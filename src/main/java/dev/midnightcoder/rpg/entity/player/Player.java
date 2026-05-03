package dev.midnightcoder.rpg.entity.player;

import dev.midnightcoder.engine.entity.mob.PlayerAvatar;
import dev.midnightcoder.engine.input.InputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.system.PlayerMovement;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.Entity;
import dev.midnightcoder.rpg.entity.combat.CombatStats;
import dev.midnightcoder.rpg.entity.skill.SkillSet;
import dev.midnightcoder.rpg.entity.skill.SkillType;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Player extends Entity {
    private final PlayerAvatar playerAvatar;

    private final SkillSet skillSet;
    private final CombatStats combatStats;

    public Player(GameMap currentMap, InputManager input) {
        var playerMovement = new PlayerMovement(currentMap.getTileMap());
        playerAvatar = new PlayerAvatar(currentMap, input, playerMovement);

        this.skillSet = new SkillSet(this);
        this.combatStats = new CombatStats(getMaxHealth());
        loadDefaults();
    }

    @Override
    public void update(double delta) {
        playerAvatar.update(delta);
    }

    @Override
    public void render(Renderer renderer) {
        playerAvatar.render(renderer);
    }

    private void loadDefaults() {
        // Assign some HP to the player
        skillSet.getSkill(SkillType.HITPOINTS).addExp(1_000);
    }

    public int getMaxHealth() {
        return skillSet.getSkill(SkillType.HITPOINTS).getLevel();
    }

    public int getCurrentHealth() {
        return combatStats.getCurrentHealth();
    }

    public boolean isDead() {
        return combatStats.isDead();
    }


    /// Getters Below here

    public PlayerAvatar getAvatar() {
        return playerAvatar;
    }

    public CombatStats getCombatStats() {
        return combatStats;
    }

    public PlayerProfile getProfile() {
        return profile;
    }

    public SkillSet getSkillSet() {
        return skillSet;
    }

    public Backpack getBackpack() {
        return backpack;
    }

    public Equipment getEquipment() {
        return equipment;
    }
}
