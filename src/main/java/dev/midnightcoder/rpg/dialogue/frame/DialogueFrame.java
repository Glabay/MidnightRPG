package dev.midnightcoder.rpg.dialogue.frame;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public abstract class DialogueFrame {
    private final String id;
    private final String speaker;

    protected DialogueFrame(String id, String speaker) {
        this.id = id;
        this.speaker = speaker;
    }

    public String getId() {
        return id;
    }

    public String getSpeaker() {
        return speaker;
    }
}
