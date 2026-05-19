package dev.midnightcoder.rpg.dialogue.frame.impl;

import dev.midnightcoder.rpg.dialogue.frame.DialogueFrame;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-18
 */
public class MessageFrame extends DialogueFrame {
    private final String text;

    protected MessageFrame(String id, String speaker, String message) {
        super(id, speaker);
        this.text = message;
    }

    public String getText() {
        return text;
    }
}
