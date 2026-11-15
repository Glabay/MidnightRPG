package dev.midnightcoder.rpg.drop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DropRarity} enum parsing and default weights.
 */
@DisplayName("DropRarity Tests")
class DropRarityTest {

    @Test
    @DisplayName("Default weights match expected values")
    void defaultWeightsMatchExpected() {
        assertEquals(0, DropRarity.ALWAYS.getDefaultWeight());
        assertEquals(128, DropRarity.COMMON.getDefaultWeight());
        assertEquals(32, DropRarity.UNCOMMON.getDefaultWeight());
        assertEquals(8, DropRarity.RARE.getDefaultWeight());
        assertEquals(1, DropRarity.ULTRA.getDefaultWeight());
    }

    @Test
    @DisplayName("fromString matches exact names")
    void fromStringMatchesExactNames() {
        assertEquals(DropRarity.ALWAYS, DropRarity.fromString("ALWAYS"));
        assertEquals(DropRarity.COMMON, DropRarity.fromString("COMMON"));
        assertEquals(DropRarity.UNCOMMON, DropRarity.fromString("UNCOMMON"));
        assertEquals(DropRarity.RARE, DropRarity.fromString("RARE"));
        assertEquals(DropRarity.ULTRA, DropRarity.fromString("ULTRA"));
    }

    @Test
    @DisplayName("fromString is case insensitive")
    void fromStringIsCaseInsensitive() {
        assertEquals(DropRarity.COMMON, DropRarity.fromString("common"));
        assertEquals(DropRarity.RARE, DropRarity.fromString("rare"));
        assertEquals(DropRarity.UNCOMMON, DropRarity.fromString("Uncommon"));
    }

    @Test
    @DisplayName("fromString with spaces falls back to COMMON")
    void fromStringWithSpacesFallsBack() {
        // "UN COMMON" → "UN_COMMON" which does NOT match "UNCOMMON"
        assertEquals(DropRarity.COMMON, DropRarity.fromString("UN COMMON"));
    }

    @Test
    @DisplayName("fromString with hyphens falls back to COMMON")
    void fromStringWithHyphensFallsBack() {
        // "UN-COMMON" → "UN_COMMON" which does NOT match "UNCOMMON"
        assertEquals(DropRarity.COMMON, DropRarity.fromString("UN-COMMON"));
    }

    @Test
    @DisplayName("fromString handles ULTRA_RARE variant")
    void fromStringHandlesUltraRare() {
        assertEquals(DropRarity.ULTRA, DropRarity.fromString("ULTRA_RARE"));
    }

    @Test
    @DisplayName("fromString handles ULTRARARE variant")
    void fromStringHandlesUltraRareCombined() {
        assertEquals(DropRarity.ULTRA, DropRarity.fromString("ULTRARARE"));
    }

    @Test
    @DisplayName("fromString returns COMMON for null")
    void fromStringReturnsCommonForNull() {
        assertEquals(DropRarity.COMMON, DropRarity.fromString(null));
    }

    @Test
    @DisplayName("fromString returns COMMON for unknown value")
    void fromStringReturnsCommonForUnknown() {
        assertEquals(DropRarity.COMMON, DropRarity.fromString("UNKNOWN"));
    }

    @Test
    @DisplayName("fromString returns COMMON for empty string")
    void fromStringReturnsCommonForEmpty() {
        assertEquals(DropRarity.COMMON, DropRarity.fromString(""));
    }

    @Test
    @DisplayName("fromString trims whitespace")
    void fromStringTrimsWhitespace() {
        assertEquals(DropRarity.RARE, DropRarity.fromString("  RARE  "));
    }
}
