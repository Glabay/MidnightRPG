package dev.midnightcoder.rpg.entity.mob.player;

import dev.midnightcoder.engine.entity.mob.PlayerAvatar;
import dev.midnightcoder.engine.input.keyboard.KeyboardInputManager;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.Entity;
import dev.midnightcoder.rpg.entity.combat.CombatStats;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.skill.SkillSet;
import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.inventory.container.Backpack;
import dev.midnightcoder.rpg.inventory.container.Equipment;

import java.awt.event.KeyEvent;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public class Player extends Mob {
    private final PlayerAvatar playerAvatar;

    private final PlayerMovement playerMovement;
    private final KeyboardInputManager input;
    private final PlayerProfile profile;
    private final SkillSet skillSet;
    private final Backpack backpack;
    private final Equipment equipment;
    private final CombatStats combatStats;
    private final Vec2i startingPosition;

    private Entity selectedEntity;

    public Player(String username, GameMap currentMap, KeyboardInputManager input) {
        this.input = input;
        startingPosition = new Vec2i(100 << 5, 100 << 5);

        playerMovement = new PlayerMovement(currentMap.getTileMap());
        playerAvatar = new PlayerAvatar(startingPosition, currentMap, this.input, playerMovement);
        profile = new PlayerProfile(username);
        skillSet = new SkillSet(this);
        backpack = new Backpack();
        equipment = new Equipment();
        combatStats = new CombatStats(getMaxHealth());

        // load up default details
        loadDefaults();

        // load Saved details
    }

    @Override
    public void update(double delta) {
        // Movement logic
        if (input.isKeyHeld(KeyEvent.VK_SHIFT))
            speed = 4; // running
        else
            speed = 3;

        var dx = (int) (getAvatar().getMoveX() * speed * delta);
        var dy = (int) (getAvatar().getMoveY() * speed * delta);

        playerMovement.move(getAvatar(), dx, dy);

        // Camera follow
        var targetX = getX() + (getAvatar().getWidth() / 2f);
        var targetY = getY() + (getAvatar().getHeight() / 2f);

        getAvatar().getCurrentMap()
            .getCamera()
            .follow(targetX, targetY);
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
