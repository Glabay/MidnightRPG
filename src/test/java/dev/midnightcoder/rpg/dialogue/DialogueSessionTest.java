package dev.midnightcoder.rpg.dialogue;

import dev.midnightcoder.rpg.dialogue.choice.DialogueChoice;
import dev.midnightcoder.rpg.dialogue.frame.DialogueFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.ChoiceFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.EndFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.NpcFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.PlayerFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link DialogueSession} frame advancement and choice handling.
 */
@DisplayName("DialogueSession Tests")
class DialogueSessionTest {

    private Dialogue dialogue;
    private DialogueSession session;

    @Nested
    @DisplayName("Simple NPC → Player → End flow")
    class SimpleFlow {

        @BeforeEach
        void setUp() {
            Map<String, DialogueFrame> frames = Map.of(
                "start", new NpcFrame("start", "NPC", "Hello!", "player1"),
                "player1", new PlayerFrame("player1", "Player", "Hi there!", "end"),
                "end", new EndFrame("end", null)
            );
            dialogue = new Dialogue("start", frames);
            session = new DialogueSession(dialogue);
        }

        @Test
        @DisplayName("Session starts at the starting frame")
        void startsAtStartingFrame() {
            DialogueFrame frame = session.getCurrentFrame();
            assertEquals("start", frame.getId());
            assertEquals("Hello!", frame.getText());
        }

        @Test
        @DisplayName("Session is active initially")
        void isActiveInitially() {
            assertTrue(session.isActive());
        }

        @Test
        @DisplayName("Advance moves from NPC to Player frame")
        void advanceFromNpcToPlayer() {
            session.advance();
            DialogueFrame frame = session.getCurrentFrame();
            assertEquals("player1", frame.getId());
            assertEquals("Hi there!", frame.getText());
        }

        @Test
        @DisplayName("Advance moves from Player to End frame")
        void advanceFromPlayerToEnd() {
            session.advance(); // NPC → Player
            session.advance(); // Player → End
            assertFalse(session.isActive());
        }

        @Test
        @DisplayName("Full flow: NPC → Player → End")
        void fullFlow() {
            assertTrue(session.isActive());
            session.advance();
            assertTrue(session.isActive());
            session.advance();
            assertFalse(session.isActive());
        }
    }

    @Nested
    @DisplayName("Choice handling")
    class ChoiceHandling {

        @BeforeEach
        void setUp() {
            Map<String, DialogueFrame> frames = Map.of(
                "start", new NpcFrame("start", "NPC", "Choose your path:", "choice1"),
                "choice1", new ChoiceFrame("choice1", "NPC", "What do you want to do?",
                    List.of(
                        new DialogueChoice("Fight", "fight"),
                        new DialogueChoice("Flee", "flee")
                    )),
                "fight", new NpcFrame("fight", "NPC", "You fight bravely!", "end"),
                "flee", new NpcFrame("flee", "NPC", "You run away!", "end"),
                "end", new EndFrame("end", null)
            );
            dialogue = new Dialogue("start", frames);
            session = new DialogueSession(dialogue);
        }

        @Test
        @DisplayName("Advance reaches choice frame")
        void advanceReachesChoice() {
            session.advance(); // NPC → Choice
            assertTrue(session.getCurrentFrame() instanceof ChoiceFrame);
        }

        @Test
        @DisplayName("Choose index 0 selects first option")
        void chooseFirstOption() {
            session.advance(); // NPC → Choice
            session.choose(0); // Choose "Fight"
            assertEquals("fight", session.getCurrentFrame().getId());
        }

        @Test
        @DisplayName("Choose index 1 selects second option")
        void chooseSecondOption() {
            session.advance(); // NPC → Choice
            session.choose(1); // Choose "Flee"
            assertEquals("flee", session.getCurrentFrame().getId());
        }

        @Test
        @DisplayName("Full choice flow")
        void fullChoiceFlow() {
            session.advance(); // NPC → Choice
            session.choose(0); // Choose "Fight"
            session.advance(); // Fight → End
            assertFalse(session.isActive());
        }

        @Test
        @DisplayName("Choose on non-choice frame does nothing")
        void chooseOnNonChoiceDoesNothing() {
            // Current frame is NpcFrame, not ChoiceFrame
            session.choose(0);
            assertEquals("start", session.getCurrentFrame().getId());
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCases {

        @Test
        @DisplayName("Session ending at EndFrame is inactive")
        void endFrameIsInactive() {
            Map<String, DialogueFrame> frames = Map.of(
                "end", new EndFrame("end", null)
            );
            dialogue = new Dialogue("end", frames);
            session = new DialogueSession(dialogue);
            assertFalse(session.isActive());
        }

        @Test
        @DisplayName("Advance on EndFrame throws IllegalStateException")
        void advanceOnEndThrows() {
            Map<String, DialogueFrame> frames = Map.of(
                "end", new EndFrame("end", null)
            );
            dialogue = new Dialogue("end", frames);
            session = new DialogueSession(dialogue);
            assertThrows(IllegalStateException.class, () -> {
                // Session is inactive, but if we try to advance it would throw
                session.advance();
            });
        }
    }

    @Nested
    @DisplayName("Dialogue frame properties")
    class FrameProperties {

        @Test
        @DisplayName("NpcFrame has correct speaker and text")
        void npcFrameProperties() {
            NpcFrame frame = new NpcFrame("id", "Goblin", "Go away!", "next");
            assertEquals("id", frame.getId());
            assertEquals("Goblin", frame.getSpeaker());
            assertEquals("Go away!", frame.getText());
            assertEquals("next", frame.getNextFrameId());
        }

        @Test
        @DisplayName("PlayerFrame has correct speaker and text")
        void playerFrameProperties() {
            PlayerFrame frame = new PlayerFrame("id", "Player", "I will fight!", "next");
            assertEquals("id", frame.getId());
            assertEquals("Player", frame.getSpeaker());
            assertEquals("I will fight!", frame.getText());
            assertEquals("next", frame.getNextFrameId());
        }

        @Test
        @DisplayName("EndFrame text is fixed")
        void endFrameText() {
            EndFrame frame = new EndFrame("id", null);
            assertEquals("End of dialogue", frame.getText());
        }

        @Test
        @DisplayName("ChoiceFrame has choices")
        void choiceFrameChoices() {
            List<DialogueChoice> choices = List.of(
                new DialogueChoice("Yes", "yes"),
                new DialogueChoice("No", "no")
            );
            ChoiceFrame frame = new ChoiceFrame("id", "NPC", "Choose?", choices);
            assertEquals(2, frame.getChoices().size());
            assertEquals("Yes", frame.getChoices().get(0).text());
            assertEquals("yes", frame.getChoices().get(0).nextId());
        }

        @Test
        @DisplayName("Speaker can be changed")
        void speakerCanBeChanged() {
            NpcFrame frame = new NpcFrame("id", "Original", "Text", "next");
            frame.setSpeaker("Changed");
            assertEquals("Changed", frame.getSpeaker());
        }
    }
}
