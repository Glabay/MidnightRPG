package dev.midnightcoder.rpg.dialogue.frame.impl;

import dev.midnightcoder.rpg.dialogue.choice.DialogueChoice;
import dev.midnightcoder.rpg.dialogue.frame.DialogueFrame;

import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-17
 */
public class ChoiceFrame extends DialogueFrame {
    private final String text;
    private final List<DialogueChoice> choices;

    public ChoiceFrame(String id, String speaker, String text, List<DialogueChoice> choices) {
        super(id, speaker);
        this.text = text;
        this.choices = choices;
    }

    public String getText() {
        return text;
    }

    public List<DialogueChoice> getChoices() {
        return choices;
    }

}
