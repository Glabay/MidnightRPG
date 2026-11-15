package dev.midnightcoder.rpg.entity.skill;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link SkillType} enum display names and icons.
 */
@DisplayName("SkillType Tests")
class SkillTypeTest {

    @Test
    @DisplayName("All skill types have non-empty display names")
    void allSkillsHaveDisplayName() {
        for (SkillType type : SkillType.values()) {
            String name = type.getDisplayName();
            assertNotNull(name);
            assertFalse(name.isEmpty());
        }
    }

    @Test
    @DisplayName("All skill types have non-empty icons")
    void allSkillsHaveIcon() {
        for (SkillType type : SkillType.values()) {
            String icon = type.getSkillIcon();
            assertNotNull(icon);
            assertFalse(icon.isEmpty());
        }
    }

    @Test
    @DisplayName("Display name capitalizes first letter")
    void displayNameCapitalizesFirstLetter() {
        assertEquals("Attack", SkillType.ATTACK.getDisplayName());
        assertEquals("Strength", SkillType.STRENGTH.getDisplayName());
        assertEquals("Defence", SkillType.DEFENCE.getDisplayName());
        assertEquals("Mining", SkillType.MINING.getDisplayName());
        assertEquals("Woodcutting", SkillType.WOODCUTTING.getDisplayName());
        assertEquals("Fishing", SkillType.FISHING.getDisplayName());
        assertEquals("Cooking", SkillType.COOKING.getDisplayName());
    }

    @Test
    @DisplayName("Known skill types exist")
    void knownSkillTypesExist() {
        assertArrayEquals(new SkillType[]{
            SkillType.ATTACK, SkillType.STRENGTH, SkillType.DEFENCE,
            SkillType.RANGED, SkillType.MAGIC, SkillType.HITPOINTS,
            SkillType.MINING, SkillType.BLACKSMITH, SkillType.WOODCUTTING,
            SkillType.FISHING, SkillType.COOKING
        }, SkillType.values());
    }

    @Test
    @DisplayName("Skill type count is expected")
    void skillTypeCount() {
        assertEquals(11, SkillType.values().length);
    }
}
