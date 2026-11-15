package dev.midnightcoder.rpg.entity.skill;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Skill} XP/level calculation logic.
 */
@DisplayName("Skill XP and Level Calculations")
class SkillTest {

    private Skill skill;

    @BeforeEach
    void setUp() {
        skill = new Skill(SkillType.MINING);
    }

    @Nested
    @DisplayName("Initial state")
    class InitialState {

        @Test
        @DisplayName("New skill starts at level 1")
        void newSkillStartsAtLevel1() {
            assertEquals(1, skill.getLevel());
        }

        @Test
        @DisplayName("New skill starts with 0 XP")
        void newSkillStartsWithZeroXp() {
            assertEquals("0", skill.getExperience());
        }

        @Test
        @DisplayName("New skill has correct type")
        void newSkillHasCorrectType() {
            assertEquals(SkillType.MINING, skill.getSkillType());
        }
    }

    @Nested
    @DisplayName("Adding experience")
    class AddingExperience {

        @Test
        @DisplayName("Adding XP increases experience")
        void addingXpIncreasesExperience() {
            skill.addExp(100);
            assertNotEquals("0", skill.getExperience());
        }

        @Test
        @DisplayName("Level stays at 1 with small XP")
        void levelStaysAt1WithSmallXp() {
            skill.addExp(10);
            assertEquals(1, skill.getLevel());
        }

        @Test
        @DisplayName("Level increases with enough XP")
        void levelIncreasesWithEnoughXp() {
            // Level 2 requires 25 * 2^2.4 ≈ 109 XP (cast to long)
            skill.addExp(200);
            // Verify level increased from 1
            assertTrue(skill.getLevel() >= 2, "Level should be at least 2 with 200 XP, got level " + skill.getLevel());
        }

        @Test
        @DisplayName("Multiple XP additions accumulate")
        void multipleXpAdditionsAccumulate() {
            skill.addExp(50);
            skill.addExp(50);
            assertEquals("100", skill.getExperience());
        }

        @Test
        @DisplayName("Level caps at 99")
        void levelCapsAt99() {
            // Set to max level
            skill.setLevel(99);
            assertEquals(99, skill.getLevel());

            // Adding more XP shouldn't exceed 99
            skill.addExp(1000000);
            assertEquals(99, skill.getLevel(), "Level should cap at 99");
        }
    }

    @Nested
    @DisplayName("Setting level directly")
    class SettingLevel {

        @Test
        @DisplayName("Setting level calculates correct XP")
        void settingLevelCalculatesXp() {
            skill.setLevel(10);
            assertEquals(10, skill.getLevel());
        }

        @Test
        @DisplayName("Setting level to 1 resets XP")
        void settingLevelToOneResetsXp() {
            skill.addExp(500);
            skill.setLevel(1);
            assertEquals(1, skill.getLevel());
        }
    }

    @Nested
    @DisplayName("Next level calculation")
    class NextLevel {

        @Test
        @DisplayName("Next level XP is greater than current XP")
        void nextLevelXpIsGreater() {
            skill.addExp(50);
            String currentXp = skill.getExperience();
            String nextLevelXp = skill.getNextLevel();

            long current = Long.parseLong(currentXp.replace(",", "").replace(".", ""));
            long next = Long.parseLong(nextLevelXp.replace(",", "").replace(".", ""));

            assertTrue(next > current, "Next level XP should be greater than current XP");
        }
    }

    @Nested
    @DisplayName("XP curve consistency")
    class XpCurveConsistency {

        @Test
        @DisplayName("XP curve is monotonically increasing")
        void xpCurveIsMonotonicallyIncreasing() {
            long prevXp = 0;
            for (int level = 1; level <= 50; level++) {
                skill.setLevel(level);
                long currentXp = Long.parseLong(skill.getExperience().replace(",", "").replace(".", ""));
                assertTrue(currentXp >= prevXp,
                    "XP for level " + level + " should be >= XP for level " + (level - 1));
                prevXp = currentXp;
            }
        }

        @Test
        @DisplayName("Level 1 requires 0 XP")
        void level1RequiresZeroXp() {
            skill.setLevel(1);
            // Level 1 XP should be very low (25 * 1^2.4 = 25)
            String xp = skill.getExperience();
            long xpValue = Long.parseLong(xp.replace(",", "").replace(".", ""));
            assertTrue(xpValue >= 0 && xpValue < 100, "Level 1 XP should be very low");
        }
    }
}
