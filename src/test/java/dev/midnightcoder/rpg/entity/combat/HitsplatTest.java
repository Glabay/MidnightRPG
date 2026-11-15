package dev.midnightcoder.rpg.entity.combat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Hitsplat} type selection and expiration logic.
 */
@DisplayName("Hitsplat Tests")
class HitsplatTest {

    @Nested
    @DisplayName("Construction")
    class Construction {

        @Test
        @DisplayName("Positive damage creates DAMAGE type")
        void positiveDamageCreatesDamageType() {
            Hitsplat hitsplat = new Hitsplat(10);
            assertEquals(Hitsplat.HitsplatType.DAMAGE, hitsplat.getType());
        }

        @Test
        @DisplayName("Zero damage creates BLOCK type")
        void zeroDamageCreatesBlockType() {
            Hitsplat hitsplat = new Hitsplat(0);
            assertEquals(Hitsplat.HitsplatType.BLOCK, hitsplat.getType());
        }

        @Test
        @DisplayName("Negative damage creates BLOCK type")
        void negativeDamageCreatesBlockType() {
            Hitsplat hitsplat = new Hitsplat(-5);
            assertEquals(Hitsplat.HitsplatType.BLOCK, hitsplat.getType());
        }

        @Test
        @DisplayName("Explicit type overrides default")
        void explicitTypeOverridesDefault() {
            Hitsplat hitsplat = new Hitsplat(10, Hitsplat.HitsplatType.BLOCK);
            assertEquals(Hitsplat.HitsplatType.BLOCK, hitsplat.getType());
        }

        @Test
        @DisplayName("Null type uses default based on damage")
        void nullTypeUsesDefault() {
            Hitsplat hitsplat = new Hitsplat(10, null);
            assertEquals(Hitsplat.HitsplatType.DAMAGE, hitsplat.getType());
        }

        @Test
        @DisplayName("Duration clamped to minimum 1")
        void durationClampedToMinimum() {
            Hitsplat hitsplat = new Hitsplat(10, Hitsplat.HitsplatType.DAMAGE, 0);
            assertEquals(1, hitsplat.getDurationTicks());
        }

        @Test
        @DisplayName("Default lifetime is 60 ticks")
        void defaultLifetimeIs60() {
            Hitsplat hitsplat = new Hitsplat(10);
            assertEquals(Hitsplat.DEFAULT_LIFETIME_TICKS, hitsplat.getMaxDurationTicks());
        }
    }

    @Nested
    @DisplayName("Factory methods")
    class FactoryMethods {

        @Test
        @DisplayName("damage() creates DAMAGE hitsplat")
        void damageFactory() {
            Hitsplat hitsplat = Hitsplat.damage(25);
            assertEquals(Hitsplat.HitsplatType.DAMAGE, hitsplat.getType());
            assertEquals(25, hitsplat.getDamage());
        }

        @Test
        @DisplayName("block() creates BLOCK hitsplat with 0 damage")
        void blockFactory() {
            Hitsplat hitsplat = Hitsplat.block();
            assertEquals(Hitsplat.HitsplatType.BLOCK, hitsplat.getType());
            assertEquals(0, hitsplat.getDamage());
        }

        @Test
        @DisplayName("block(amount) creates BLOCK with amount")
        void blockWithAmount() {
            Hitsplat hitsplat = Hitsplat.block(5);
            assertEquals(Hitsplat.HitsplatType.BLOCK, hitsplat.getType());
            assertEquals(5, hitsplat.getDamage());
        }
    }

    @Nested
    @DisplayName("Expiration")
    class Expiration {

        @Test
        @DisplayName("New hitsplat is not expired")
        void newHitsplatNotExpired() {
            Hitsplat hitsplat = new Hitsplat(10);
            assertFalse(hitsplat.isExpired());
        }

        @Test
        @DisplayName("Tick reduces duration")
        void tickReducesDuration() {
            Hitsplat hitsplat = new Hitsplat(10, Hitsplat.HitsplatType.DAMAGE, 5);
            hitsplat.tick();
            assertEquals(4, hitsplat.getDurationTicks());
        }

        @Test
        @DisplayName("Hitsplat expires after max ticks")
        void expiresAfterMaxTicks() {
            Hitsplat hitsplat = new Hitsplat(10, Hitsplat.HitsplatType.DAMAGE, 3);
            hitsplat.tick();
            hitsplat.tick();
            hitsplat.tick();
            assertTrue(hitsplat.isExpired());
        }

        @Test
        @DisplayName("Update with delta reduces duration")
        void updateWithDelta() {
            Hitsplat hitsplat = new Hitsplat(10, Hitsplat.HitsplatType.DAMAGE, 10);
            hitsplat.update(2.0);
            assertEquals(8, hitsplat.getDurationTicks());
        }

        @Test
        @DisplayName("Update rounds delta to at least 1")
        void updateRoundsDelta() {
            Hitsplat hitsplat = new Hitsplat(10, Hitsplat.HitsplatType.DAMAGE, 10);
            hitsplat.update(0.4);
            // 0.4 rounds to 0, clamped to 1
            assertEquals(9, hitsplat.getDurationTicks());
        }
    }

    @Nested
    @DisplayName("Colors")
    class Colors {

        @Test
        @DisplayName("DAMAGE type has red fill color")
        void damageTypeHasRedColor() {
            Color color = Hitsplat.HitsplatType.DAMAGE.getFillColor();
            assertEquals(204, color.getRed());
            assertEquals(30, color.getGreen());
            assertEquals(30, color.getBlue());
        }

        @Test
        @DisplayName("BLOCK type has blue fill color")
        void blockTypeHasBlueColor() {
            Color color = Hitsplat.HitsplatType.BLOCK.getFillColor();
            assertEquals(30, color.getRed());
            assertEquals(100, color.getGreen());
            assertEquals(220, color.getBlue());
        }

        @Test
        @DisplayName("Text color is white")
        void textColorIsWhite() {
            Hitsplat hitsplat = new Hitsplat(10);
            assertEquals(Color.WHITE, hitsplat.getTextColor());
        }
    }

    @Nested
    @DisplayName("Properties")
    class Properties {

        @Test
        @DisplayName("Default radius is 10")
        void defaultRadius() {
            Hitsplat hitsplat = new Hitsplat(10);
            assertEquals(Hitsplat.DEFAULT_RADIUS, hitsplat.getRadius());
        }

        @Test
        @DisplayName("Damage is preserved")
        void damageIsPreserved() {
            Hitsplat hitsplat = new Hitsplat(42);
            assertEquals(42, hitsplat.getDamage());
        }
    }
}
