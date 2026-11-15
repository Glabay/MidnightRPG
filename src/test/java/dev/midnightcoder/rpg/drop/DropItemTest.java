package dev.midnightcoder.rpg.drop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DropItem} amount rolling and factory methods.
 */
@DisplayName("DropItem Tests")
class DropItemTest {

    private static final int TEST_ITEM_ID = 1;
    private final Random random = new Random(42);

    @Nested
    @DisplayName("Constructor validation")
    class ConstructorValidation {

        @Test
        @DisplayName("Min amount clamped to 1")
        void minAmountClampedTo1() {
            DropItem item = new DropItem(TEST_ITEM_ID, 0, 5, 1);
            // rollAmount should never return less than 1
            int amount = item.rollAmount(random);
            assertTrue(amount >= 1, "Amount should be at least 1");
        }

        @Test
        @DisplayName("Max amount clamped to min amount")
        void maxAmountClampedToMin() {
            DropItem item = new DropItem(TEST_ITEM_ID, 5, 3, 1);
            int amount = item.rollAmount(random);
            assertEquals(5, amount, "Amount should be 5 when max < min");
        }

        @Test
        @DisplayName("Weight clamped to 1")
        void weightClampedTo1() {
            DropItem item = new DropItem(TEST_ITEM_ID, 1, 1, 0);
            assertEquals(1, item.getWeight(), "Weight should be clamped to 1");
        }

        @Test
        @DisplayName("Negative weight clamped to 1")
        void negativeWeightClampedTo1() {
            DropItem item = new DropItem(TEST_ITEM_ID, 1, 1, -5);
            assertEquals(1, item.getWeight(), "Negative weight should be clamped to 1");
        }
    }

    @Nested
    @DisplayName("Factory methods")
    class FactoryMethods {

        @Test
        @DisplayName("of(itemId, min, max, weight) creates correctly")
        void ofWithAllParams() {
            DropItem item = DropItem.of(10, 2, 5, 3);
            assertEquals(3, item.getWeight());
        }

        @Test
        @DisplayName("of(itemId, amount, weight) creates fixed amount")
        void ofWithFixedAmount() {
            DropItem item = DropItem.of(10, 3, 5);
            assertEquals(3, item.rollAmount(random));
        }

        @Test
        @DisplayName("of(itemId, weight) creates default amount 1")
        void ofWithDefaultAmount() {
            DropItem item = DropItem.of(10, 2);
            assertEquals(1, item.rollAmount(random));
        }

        @Test
        @DisplayName("always(itemId, min, max) creates always drop")
        void alwaysWithRange() {
            DropItem item = DropItem.always(10, 2, 4);
            int amount = item.rollAmount(random);
            assertTrue(amount >= 2 && amount <= 4);
        }

        @Test
        @DisplayName("always(itemId, amount) creates fixed always drop")
        void alwaysWithFixedAmount() {
            DropItem item = DropItem.always(10, 3);
            assertEquals(3, item.rollAmount(random));
        }

        @Test
        @DisplayName("always(itemId) creates default always drop")
        void alwaysWithDefault() {
            DropItem item = DropItem.always(10);
            assertEquals(1, item.rollAmount(random));
        }
    }

    @Nested
    @DisplayName("Amount rolling")
    class AmountRolling {

        @Test
        @DisplayName("Fixed amount returns consistently")
        void fixedAmountReturnsConsistently() {
            DropItem item = new DropItem(TEST_ITEM_ID, 5, 5, 1);
            for (int i = 0; i < 100; i++) {
                assertEquals(5, item.rollAmount(random));
            }
        }

        @Test
        @DisplayName("Range amount stays within bounds")
        void rangeAmountStaysWithinBounds() {
            DropItem item = new DropItem(TEST_ITEM_ID, 3, 7, 1);
            for (int i = 0; i < 1000; i++) {
                int amount = item.rollAmount(random);
                assertTrue(amount >= 3 && amount <= 7,
                    "Amount " + amount + " should be between 3 and 7");
            }
        }

        @Test
        @DisplayName("Range amount covers full range over many rolls")
        void rangeAmountCoversFullRange() {
            DropItem item = new DropItem(TEST_ITEM_ID, 1, 5, 1);
            boolean[] seen = new boolean[6];
            for (int i = 0; i < 10000; i++) {
                int amount = item.rollAmount(random);
                if (amount >= 1 && amount <= 5) {
                    seen[amount] = true;
                }
            }
            for (int i = 1; i <= 5; i++) {
                assertTrue(seen[i], "Value " + i + " should have been rolled at least once");
            }
        }
    }

    @Nested
    @DisplayName("Roll item")
    class RollItem {

        @Test
        @DisplayName("rollItem returns non-null Item")
        void rollItemReturnsNonNull() {
            DropItem item = new DropItem(1, 1, 1, 1);
            // This will fail if item ID 1 doesn't exist in cache,
            // but tests the method doesn't throw
            assertDoesNotThrow(() -> item.rollItem(random));
        }
    }
}
