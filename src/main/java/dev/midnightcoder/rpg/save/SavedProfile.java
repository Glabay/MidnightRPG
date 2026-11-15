package dev.midnightcoder.rpg.save;

import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.item.EquipmentSlot;

import java.util.HashMap;
import java.util.Map;

/**
 * Data transfer object holding all saved player data loaded from the SQLite database.
 */
public record SavedProfile(
    String username,
    float masterVolume,
    Map<SkillType, Integer> skillXp,
    Map<Integer, SavedItem> backpackItems,
    Map<EquipmentSlot, SavedItem> equipmentItems,
    int positionX,
    int positionY,
    int currentHealth
) {
    public SavedProfile(
        String username, float masterVolume, Map<SkillType, Integer> skillXp, Map<Integer, SavedItem> backpackItems, Map<EquipmentSlot, SavedItem> equipmentItems,
        int positionX, int positionY, int currentHealth
    ) {
        this.username = username;
        this.masterVolume = masterVolume;
        this.skillXp = skillXp != null
            ? skillXp
            : new HashMap<>();
        this.backpackItems = backpackItems != null
            ? backpackItems
            : new HashMap<>();
        this.equipmentItems = equipmentItems != null
            ? equipmentItems
            : new HashMap<>();
        this.positionX = positionX;
        this.positionY = positionY;
        this.currentHealth = currentHealth;
    }

    /**
     * Holds the item ID and quantity for a saved inventory or equipment slot.
     */
    public record SavedItem(
        int itemId,
        int quantity
    ) {}
}
