package dev.midnightcoder.rpg.dialogue;

import dev.midnightcoder.rpg.dialogue.frame.DialogueFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.ChoiceFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.EndFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.NpcFrame;
import dev.midnightcoder.rpg.dialogue.frame.impl.PlayerFrame;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public final class DialogueSession {
    private final Dialogue dialogue;
    private DialogueFrame currentFrame;

    public DialogueSession(Dialogue dialogue) {
        this.dialogue = dialogue;
        this.currentFrame = dialogue.getFrame(dialogue.getStartingFrameId());
    }

    public DialogueFrame getCurrentFrame() {
        return currentFrame;
    }

    public boolean isActive() {
        return currentFrame != null && !(currentFrame instanceof EndFrame);
    }

    public void advance() {
        switch (currentFrame) {
            case NpcFrame npc ->
                currentFrame = dialogue.getFrame(npc.getNextFrameId());
            case PlayerFrame player ->
                currentFrame = dialogue.getFrame(player.getNextFrameId());
            default ->
                throw new IllegalStateException("Unexpected value: " + currentFrame);
        }
    }

    public void choose(int index) {
        if (!(currentFrame instanceof ChoiceFrame choiceFrame))
            return;

        var choice = choiceFrame.getChoices().get(index);
        currentFrame = dialogue.getFrame(choice.nextId());
    }
}
