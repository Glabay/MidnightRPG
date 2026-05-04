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
import dev.midnightcoder.rpg.inventory.container.Backpack;
import dev.midnightcoder.rpg.inventory.container.Equipment;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Player extends Entity {
    private final PlayerAvatar playerAvatar;

    private final PlayerProfile profile;
    private final SkillSet skillSet;
    private final Backpack backpack;
    private final Equipment equipment;
    private final CombatStats combatStats;

    private Entity selectedEntity;

    public Player(GameMap currentMap, InputManager input) {
        var playerMovement = new PlayerMovement(currentMap.getTileMap());
        playerAvatar = new PlayerAvatar(currentMap, input, playerMovement);

        this.profile = new PlayerProfile("Glabay");
        this.skillSet = new SkillSet(this);
        this.backpack = new Backpack();
        this.equipment = new Equipment();
        this.combatStats = new CombatStats(getMaxHealth());

        // load up default details
        loadDefaults();

        // load Saved details
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
        skillSet.getSkill(SkillType.HITPOINTS).addExp(900);
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


    /// Setters here

    public void setSelectedEntity(Entity entity) {
        this.selectedEntity = entity;
    }

    /// Getters Below here

    public PlayerAvatar getAvatar() {
        return playerAvatar;
    }

    public CombatStats getCombatStats() {
        return combatStats;
    }

    public Entity getSelectedEntity() {
        return selectedEntity;
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

    public int getX() {
        return playerAvatar.getX();
    }

    public int getY() {
        return playerAvatar.getY();
    }
}
