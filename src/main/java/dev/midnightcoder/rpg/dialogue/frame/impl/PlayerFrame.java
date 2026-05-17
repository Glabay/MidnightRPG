package dev.midnightcoder.rpg.dialogue.frame.impl;

import dev.midnightcoder.rpg.dialogue.frame.DialogueFrame;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public class PlayerFrame extends DialogueFrame {
    private final String text;
    private final String nextFrameId;

    public PlayerFrame(String id, String speaker, String text, String nextFrameId) {
        super(id, speaker);
        this.text = text;
        this.nextFrameId = nextFrameId;
    }

    public String getText() {
        return text;
    }

    public String getNextFrameId() {
        return nextFrameId;
    }

}
