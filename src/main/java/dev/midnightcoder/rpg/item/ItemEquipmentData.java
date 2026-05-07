package dev.midnightcoder.rpg.item;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public record ItemEquipmentData(
    int strengthBonus,
    int defenceBonus,
    int accuracyBonus,
    int magicBonus,
    int rangedBonus,
    int attackSpeed
) {}
