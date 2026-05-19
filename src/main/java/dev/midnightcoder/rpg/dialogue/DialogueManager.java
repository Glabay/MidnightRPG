package dev.midnightcoder.rpg.dialogue;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.dialogue.choice.DialogueChoice;
import dev.midnightcoder.rpg.dialogue.frame.DialogueFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.*;
import dev.midnightcoder.rpg.item.Item;

import java.util.HashMap;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public class DialogueManager {
    private static DialogueManager instance;

    private final DialogueRepository repository;

    private DialogueSession session;

    private DialogueManager(DialogueRepository repository) {
        this.repository = repository;
        loadDialogues();
    }

    private void loadDialogues() {
        var cacheReader = CacheReader.getInstance();

        for (var def : cacheReader.getCacheManager().getDialogues()) {
            var frames = new HashMap<String, DialogueFrame>();
            for (var frameDef : def.getFrames()) {
                DialogueFrame frame = switch (frameDef.getType().toLowerCase()) {
                    case "npc" -> new NpcFrame(frameDef.getId(), frameDef.getSpeaker(), frameDef.getText(), frameDef.getNext());
                    case "player" -> new PlayerFrame(frameDef.getId(), frameDef.getSpeaker(), frameDef.getText(), frameDef.getNext());
                    case "end" -> new EndFrame(frameDef.getId(), frameDef.getSpeaker());
                    case "choice" -> {
                        var choices = frameDef.getChoices().stream()
                                .map(c -> new DialogueChoice(c.getText(), c.getNextId()))
                                .toList();
                        yield new ChoiceFrame(frameDef.getId(), frameDef.getSpeaker(), frameDef.getText(), choices);
                    }
                    case "message" -> new MessageFrame(frameDef.getId(), frameDef.getSpeaker(), frameDef.getText());
                    case "item" -> new ItemFrame(frameDef.getId(), frameDef.getSpeaker(), Item.of(frameDef.getItemId()), frameDef.getText());
                    default -> throw new IllegalStateException("Unexpected dialogue frame type: " + frameDef.getType());
                };
                frames.put(frameDef.getId(), frame);
            }
            var dialogue = new Dialogue(def.getStartFrameId(), frames);
            repository.register(def.getDialogueId(), dialogue);
        }
    }

    public static DialogueManager getInstance() {
        if (instance == null) {
            instance = new DialogueManager(new DialogueRepository());
        }
        return instance;
    }

    public void start(String dialogueId) {
        if (!repository.contains(dialogueId)) {
            throw new IllegalArgumentException("Dialogue with ID '" + dialogueId + "' does not exist");
        }
        session = new DialogueSession(repository.get(dialogueId));

        var gameScreen = MidnightRPG.getInstance().getGameScreen();
        var dialogueInter = gameScreen.getDialogueInterface();
        dialogueInter.sendDialogue(session, session.getCurrentFrame().getText())
            .display();
        gameScreen.getPlayer().setDialogueSession(session);
    }

    public void advance() {
        if (!isActive())
            return;

        session.advance();

        var gameScreen = MidnightRPG.getInstance().getGameScreen();
        var dialogueInter = gameScreen.getDialogueInterface();

        if (session.getCurrentFrame() instanceof EndFrame) {
            dialogueInter.display();
            end();
            return;
        }

        dialogueInter.sendDialogue(session, session.getCurrentFrame().getText());

        if (!session.isActive())
            end();
    }

    public void choose(int choiceIndex) {
        if (!isActive())
            return;
        session.choose(choiceIndex);

        if (!session.isActive())
            end();
    }

    public void end() {
        session = null;
    }

    public boolean isActive() {
        return session != null && session.isActive();
    }

    public DialogueSession getSession() {
        return session;
    }

    public DialogueFrame getCurrentFrame() {
        if (!isActive())
            return null;
        return session.getCurrentFrame();
    }
}
