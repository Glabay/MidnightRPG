package dev.midnightcoder.rpg.content.skills.mining;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the mining success chance formula.
 * The formula is: successChance = (miningLevel * pickaxeEffectiveness) / (miningLevel * pickaxeEffectiveness + rockLevelRequired)
 */
@DisplayName("Mining Success Chance Formula Tests")
class MiningSuccessChanceTest {

    /**
     * Calculates the mining success chance using the same formula as MiningAction.
     */
    private double calculateSuccessChance(int miningLevel, PickaxeType pickaxe, RockType rock) {
        double miningPower = miningLevel * pickaxe.getEffectiveness();
        double ratio = miningPower / rock.getLevelRequired();
        return ratio / (ratio + 1.0);
    }

    @Nested
    @DisplayName("Success chance bounds")
    class SuccessChanceBounds {

        @Test
        @DisplayName("Success chance is between 0 and 1")
        void successChanceBetweenZeroAndOne() {
            for (RockType rock : RockType.values()) {
                for (PickaxeType pickaxe : PickaxeType.values()) {
                    for (int level = 1; level <= 99; level++) {
                        double chance = calculateSuccessChance(level, pickaxe, rock);
                        assertTrue(chance >= 0.0 && chance <= 1.0,
                            "Chance should be 0-1 for level " + level + " with " + pickaxe + " on " + rock);
                    }
                }
            }
        }

        @Test
        @DisplayName("Success chance increases with level")
        void successChanceIncreasesWithLevel() {
            RockType rock = RockType.IRON;
            PickaxeType pickaxe = PickaxeType.IRON_PICKAXE;

            double prevChance = 0;
            for (int level = 1; level <= 99; level++) {
                double chance = calculateSuccessChance(level, pickaxe, rock);
                assertTrue(chance > prevChance,
                    "Chance should increase at level " + level);
                prevChance = chance;
            }
        }

        @Test
        @DisplayName("Success chance increases with better pickaxe")
        void successChanceIncreasesWithBetterPickaxe() {
            int level = 20;
            RockType rock = RockType.IRON;

            double stoneChance = calculateSuccessChance(level, PickaxeType.STONE_PICKAXE, rock);
            double ironChance = calculateSuccessChance(level, PickaxeType.IRON_PICKAXE, rock);
            double steelChance = calculateSuccessChance(level, PickaxeType.STEEL_PICKAXE, rock);

            assertTrue(steelChance > ironChance, "Steel should be better than iron");
            assertTrue(ironChance > stoneChance, "Iron should be better than stone");
        }
    }

    @Nested
    @DisplayName("Specific scenarios")
    class SpecificScenarios {

        @Test
        @DisplayName("Level 1 mining on stone rock has positive chance")
        void level1OnStoneHasPositiveChance() {
            double chance = calculateSuccessChance(1, PickaxeType.STONE_PICKAXE, RockType.STONE);
            // Level 1, effectiveness 1, rock level 5: ratio = 1/5 = 0.2, chance = 0.2/1.2 ≈ 0.167
            assertTrue(chance > 0, "Chance should not be 0");
            assertTrue(chance < 1, "Chance should be < 1");
        }

        @Test
        @DisplayName("High level mining has high chance")
        void highLevelHasHighChance() {
            double chance = calculateSuccessChance(99, PickaxeType.STEEL_PICKAXE, RockType.STONE);
            assertTrue(chance > 0.95, "Level 99 with steel on stone should have >95% chance");
        }

        @Test
        @DisplayName("Low level on high rock has very low chance")
        void lowLevelOnHighRockHasLowChance() {
            double chance = calculateSuccessChance(1, PickaxeType.STONE_PICKAXE, RockType.MITHRIL);
            assertTrue(chance < 0.1, "Level 1 on mithril should have <10% chance");
        }
    }

    @Nested
    @DisplayName("RockType properties")
    class RockTypeProperties {

        @Test
        @DisplayName("Rock levels are ordered correctly")
        void rockLevelsOrdered() {
            assertTrue(RockType.STONE.getLevelRequired() <= RockType.COPPER.getLevelRequired());
            assertTrue(RockType.COPPER.getLevelRequired() <= RockType.TIN.getLevelRequired());
            assertTrue(RockType.TIN.getLevelRequired() <= RockType.IRON.getLevelRequired());
            assertTrue(RockType.IRON.getLevelRequired() <= RockType.COAL.getLevelRequired());
            assertTrue(RockType.COAL.getLevelRequired() <= RockType.MITHRIL.getLevelRequired());
        }

        @Test
        @DisplayName("Mining experience increases with rock level")
        void experienceIncreasesWithLevel() {
            assertTrue(RockType.STONE.getMiningExperience() <= RockType.COPPER.getMiningExperience());
            assertTrue(RockType.COPPER.getMiningExperience() <= RockType.TIN.getMiningExperience());
            assertTrue(RockType.TIN.getMiningExperience() <= RockType.IRON.getMiningExperience());
            assertTrue(RockType.IRON.getMiningExperience() <= RockType.COAL.getMiningExperience());
            assertTrue(RockType.COAL.getMiningExperience() <= RockType.MITHRIL.getMiningExperience());
        }

        @Test
        @DisplayName("All rocks have positive respawn ticks")
        void allRocksHavePositiveRespawn() {
            for (RockType rock : RockType.values()) {
                assertTrue(rock.getRespawnTicks() > 0,
                    rock.name() + " should have positive respawn ticks");
            }
        }
    }

    @Nested
    @DisplayName("PickaxeType properties")
    class PickaxeTypeProperties {

        @Test
        @DisplayName("Effectiveness increases with pickaxe tier")
        void effectivenessIncreases() {
            assertTrue(PickaxeType.STONE_PICKAXE.getEffectiveness() < PickaxeType.IRON_PICKAXE.getEffectiveness());
            assertTrue(PickaxeType.IRON_PICKAXE.getEffectiveness() < PickaxeType.STEEL_PICKAXE.getEffectiveness());
        }

        @Test
        @DisplayName("Level requirements increase with pickaxe tier")
        void levelRequirementsIncrease() {
            assertTrue(PickaxeType.STONE_PICKAXE.getLevelRequired() <= PickaxeType.IRON_PICKAXE.getLevelRequired());
            assertTrue(PickaxeType.IRON_PICKAXE.getLevelRequired() <= PickaxeType.STEEL_PICKAXE.getLevelRequired());
        }
    }
}
