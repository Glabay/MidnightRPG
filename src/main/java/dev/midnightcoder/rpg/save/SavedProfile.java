package dev.midnightcoder.rpg.save;

import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.item.EquipmentSlot;

import java.util.HashMap;
import java.util.Map;

/**
 * Data transfer object holding all saved player data loaded from the SQLite database.
 */
public class SavedProfile {
    private final String username;
    private final float masterVolume;
    private final Map<SkillType, Integer> skillXp;
    private final Map<Integer, SavedItem> backpackItems;
    private final Map<EquipmentSlot, SavedItem> equipmentItems;
    private final int positionX;
    private final int positionY;
    private final int currentHealth;

    public SavedProfile(String username, float masterVolume, Map<SkillType, Integer> skillXp,
                        Map<Integer, SavedItem> backpackItems, Map<EquipmentSlot, SavedItem> equipmentItems,
                        int positionX, int positionY, int currentHealth) {
        this.username = username;
        this.masterVolume = masterVolume;
        this.skillXp = skillXp != null ? skillXp : new HashMap<>();
        this.backpackItems = backpackItems != null ? backpackItems : new HashMap<>();
        this.equipmentItems = equipmentItems != null ? equipmentItems : new HashMap<>();
        this.positionX = positionX;
        this.positionY = positionY;
        this.currentHealth = currentHealth;
    }

    public String getUsername() {
        return username;
    }

    public float getMasterVolume() {
        return masterVolume;
    }

    public Map<SkillType, Integer> getSkillXp() {
        return skillXp;
    }

    public Map<Integer, SavedItem> getBackpackItems() {
        return backpackItems;
    }

    public Map<EquipmentSlot, SavedItem> getEquipmentItems() {
        return equipmentItems;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Holds the item ID and quantity for a saved inventory or equipment slot.
     */
    public static class SavedItem {
        private final int itemId;
        private final int quantity;

        public SavedItem(int itemId, int quantity) {
            this.itemId = itemId;
            this.quantity = quantity;
        }

        public int getItemId() {
            return itemId;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
