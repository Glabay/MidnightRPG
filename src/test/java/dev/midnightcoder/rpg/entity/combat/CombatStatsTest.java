package dev.midnightcoder.rpg.entity.combat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link CombatStats} health management.
 */
@DisplayName("CombatStats Tests")
class CombatStatsTest {

    private CombatStats stats;

    @BeforeEach
    void setUp() {
        stats = new CombatStats(100);
    }

    @Nested
    @DisplayName("Initial state")
    class InitialState {

        @Test
        @DisplayName("Initial health is set correctly")
        void initialHealthIsSet() {
            assertEquals(100, stats.getCurrentHealth());
        }

        @Test
        @DisplayName("Entity is not dead initially")
        void notDeadInitially() {
            assertFalse(stats.isDead());
        }
    }

    @Nested
    @DisplayName("Damage")
    class Damage {

        @Test
        @DisplayName("Damage reduces health")
        void damageReducesHealth() {
            stats.damage(30);
            assertEquals(70, stats.getCurrentHealth());
        }

        @Test
        @DisplayName("Damage cannot reduce health below 0")
        void damageCannotReduceBelowZero() {
            stats.damage(150);
            assertEquals(0, stats.getCurrentHealth());
        }

        @Test
        @DisplayName("Multiple damage calls accumulate")
        void multipleDamageAccumulate() {
            stats.damage(30);
            stats.damage(30);
            stats.damage(30);
            assertEquals(10, stats.getCurrentHealth());
        }

        @Test
        @DisplayName("Zero damage has no effect")
        void zeroDamageNoEffect() {
            stats.damage(0);
            assertEquals(100, stats.getCurrentHealth());
        }

        @Test
        @DisplayName("Entity is dead when health reaches 0")
        void isDeadWhenHealthZero() {
            stats.damage(100);
            assertTrue(stats.isDead());
        }

        @Test
        @DisplayName("Entity is dead when health exceeds max")
        void isDeadWhenDamageExceedsHealth() {
            stats.damage(200);
            assertTrue(stats.isDead());
        }
    }

    @Nested
    @DisplayName("Healing with max health")
    class HealingWithMax {

        @Test
        @DisplayName("Heal increases health up to max")
        void healIncreasesUpToMax() {
            stats.damage(40);
            assertEquals(60, stats.getCurrentHealth());
            stats.heal(20, 100);
            assertEquals(80, stats.getCurrentHealth());
        }

        @Test
        @DisplayName("Heal cannot exceed max health")
        void healCannotExceedMax() {
            stats.damage(20);
            stats.heal(90, 100);
            assertEquals(100, stats.getCurrentHealth());
        }

        @Test
        @DisplayName("Heal from dead state")
        void healFromDeadState() {
            stats.damage(100);
            assertTrue(stats.isDead());
            stats.heal(30, 100);
            assertEquals(30, stats.getCurrentHealth());
            assertFalse(stats.isDead());
        }
    }

    @Nested
    @DisplayName("Healing without max")
    class HealingWithoutMax {

        @Test
        @DisplayName("Heal without max can exceed initial health")
        void healWithoutMaxCanExceed() {
            stats.heal(50);
            assertEquals(150, stats.getCurrentHealth());
        }
    }

    @Nested
    @DisplayName("Set current health")
    class SetCurrentHealth {

        @Test
        @DisplayName("Set health updates correctly")
        void setHealthUpdates() {
            stats.setCurrentHealth(50);
            assertEquals(50, stats.getCurrentHealth());
        }

        @Test
        @DisplayName("Set health clamps to 0 minimum")
        void setHealthClampsToZero() {
            stats.setCurrentHealth(-10);
            assertEquals(0, stats.getCurrentHealth());
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCases {

        @Test
        @DisplayName("Zero health entity is dead")
        void zeroHealthIsDead() {
            stats.damage(100);
            assertTrue(stats.isDead());
        }

        @Test
        @DisplayName("1 health entity is alive")
        void oneHealthIsAlive() {
            stats.damage(99);
            assertFalse(stats.isDead());
        }

        @Test
        @DisplayName("Starting with 0 health is dead")
        void startingWithZeroIsDead() {
            CombatStats zeroStats = new CombatStats(0);
            assertTrue(zeroStats.isDead());
        }
    }
}
