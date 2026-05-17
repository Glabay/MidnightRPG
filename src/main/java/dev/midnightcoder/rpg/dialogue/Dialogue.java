package dev.midnightcoder.rpg.dialogue;

import dev.midnightcoder.rpg.dialogue.frame.DialogueFrame;

import java.util.Map;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public class Dialogue {
    private final String startingFrameId;
    private final Map<String, DialogueFrame> frames;

    public Dialogue(String startingFrameId, Map<String, DialogueFrame> frames) {
        this.startingFrameId = startingFrameId;
        this.frames = frames;
    }

    public DialogueFrame getFrame(String frameId) {
        return frames.get(frameId);
    }

    public String getStartingFrameId() {
        return startingFrameId;
    }
}
