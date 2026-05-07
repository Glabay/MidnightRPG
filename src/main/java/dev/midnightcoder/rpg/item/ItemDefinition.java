package dev.midnightcoder.rpg.item;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-03
 */
public record ItemDefinition(
    int id,
    int value,
    int equipSlotId,
    String name,
    String description,
    String category,
    String[] inventoryOptions,
    String[] equipmentOptions,
    boolean stackable,
    boolean equippable,
    boolean tradable,
    ItemEquipmentData equipmentData
) {}
