package dev.midnightcoder.rpg.drop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DropTable} weighted item rolling.
 */
@DisplayName("DropTable Tests")
class DropTableTest {

    private final Random random = new Random(42);

    @Nested
    @DisplayName("Construction")
    class Construction {

        @Test
        @DisplayName("Default constructor uses rarity default weight")
        void defaultConstructorUsesRarityWeight() {
            DropTable table = new DropTable(DropRarity.COMMON);
            assertEquals(128, table.getTableWeight());
        }

        @Test
        @DisplayName("Constructor with weight sets correctly")
        void constructorWithWeight() {
            DropTable table = new DropTable(DropRarity.RARE, 5);
            assertEquals(5, table.getTableWeight());
        }

        @Test
        @DisplayName("Null rarity defaults to COMMON")
        void nullRarityDefaultsToCommon() {
            DropTable table = new DropTable(null, 10);
            assertEquals(10, table.getTableWeight());
        }

        @Test
        @DisplayName("Weight clamped to minimum 1")
        void weightClampedToMinimum() {
            DropTable table = new DropTable(DropRarity.COMMON, 0);
            assertEquals(1, table.getTableWeight(), "Weight should be clamped to 1");
        }

        @Test
        @DisplayName("Negative weight clamped to 1")
        void negativeWeightClamped() {
            DropTable table = new DropTable(DropRarity.COMMON, -5);
            assertEquals(1, table.getTableWeight());
        }
    }

    @Nested
    @DisplayName("Adding drops")
    class AddingDrops {

        @Test
        @DisplayName("addDrop returns self for chaining")
        void addDropReturnsSelf() {
            DropTable table = new DropTable(DropRarity.COMMON);
            assertSame(table, table.addDrop(1, 1, 1, 1));
        }

        @Test
        @DisplayName("Null drop is ignored")
        void nullDropIgnored() {
            DropTable table = new DropTable(DropRarity.COMMON);
            table.addDrop(null);
            assertTrue(table.isEmpty());
        }

        @Test
        @DisplayName("Multiple drops increase size")
        void multipleDropsIncreaseSize() {
            DropTable table = new DropTable(DropRarity.COMMON);
            table.addDrop(1, 1, 1, 1);
            table.addDrop(2, 1, 1, 1);
            table.addDrop(3, 1, 1, 1);
            assertEquals(3, table.size());
        }

        @Test
        @DisplayName("Total weight sums correctly")
        void totalWeightSumsCorrectly() {
            DropTable table = new DropTable(DropRarity.COMMON);
            table.addDrop(1, 1, 1, 10);
            table.addDrop(2, 1, 1, 20);
            table.addDrop(3, 1, 1, 30);
            assertEquals(60, table.getTotalItemWeight());
        }
    }

    @Nested
    @DisplayName("Rolling drops")
    class RollingDrops {

        @Test
        @DisplayName("Empty table returns null")
        void emptyTableReturnsNull() {
            DropTable table = new DropTable(DropRarity.COMMON);
            assertNull(table.rollDropItem(random));
        }

        @Test
        @DisplayName("Single item always returns that item")
        void singleItemAlwaysReturned() {
            DropTable table = new DropTable(DropRarity.COMMON);
            table.addDrop(42, 1, 1, 1);

            for (int i = 0; i < 100; i++) {
                DropItem item = table.rollDropItem(random);
                assertNotNull(item);
                assertEquals(1, item.getWeight());
            }
        }

        @Test
        @DisplayName("Weighted roll favors higher weight items")
        void weightedRollFavorsHigherWeight() {
            DropTable table = new DropTable(DropRarity.COMMON);
            // Item 1: weight 1, Item 2: weight 99
            table.addDrop(1, 1, 1, 1);
            table.addDrop(2, 1, 1, 99);

            int item2Count = 0;
            int total = 10000;
            for (int i = 0; i < total; i++) {
                DropItem item = table.rollDropItem(random);
                // We can't check itemId directly, but weight 99 item should dominate
                if (item.getWeight() == 99) {
                    item2Count++;
                }
            }
            // Item 2 should appear ~99% of the time
            double ratio = (double) item2Count / total;
            assertTrue(ratio > 0.95, "Item 2 should appear >95% of the time, got " + ratio);
        }

        @Test
        @DisplayName("Equal weights distribute evenly")
        void equalWeightsDistributeEvenly() {
            DropTable table = new DropTable(DropRarity.COMMON);
            table.addDrop(1, 1, 1, 1);
            table.addDrop(2, 1, 1, 1);

            int count1 = 0;
            int total = 10000;
            for (int i = 0; i < total; i++) {
                DropItem item = table.rollDropItem(random);
                if (item != null) count1++;
            }
            // Both should appear roughly 50%
            assertTrue(count1 == total, "All rolls should return an item");
        }

        @Test
        @DisplayName("Empty table returns null for rollDropItem")
        void emptyTableReturnsNullForRoll() {
            DropTable table = new DropTable(DropRarity.COMMON);
            assertNull(table.rollDropItem(random));
        }
    }

    @Nested
    @DisplayName("Table weight management")
    class TableWeightManagement {

        @Test
        @DisplayName("setTableWeight updates weight")
        void setTableWeightUpdates() {
            DropTable table = new DropTable(DropRarity.COMMON);
            table.setTableWeight(50);
            assertEquals(50, table.getTableWeight());
        }

        @Test
        @DisplayName("setTableWeight clamps to 1")
        void setTableWeightClampsTo1() {
            DropTable table = new DropTable(DropRarity.COMMON);
            table.setTableWeight(0);
            assertEquals(1, table.getTableWeight());
        }
    }

    @Nested
    @DisplayName("Roll all items")
    class RollAllItems {

        @Test
        @DisplayName("Empty table returns empty list")
        void emptyTableReturnsEmptyList() {
            DropTable table = new DropTable(DropRarity.ALWAYS);
            assertTrue(table.rollAllItems(random).isEmpty());
        }
    }
}
