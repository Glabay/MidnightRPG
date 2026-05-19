package dev.midnightcoder.rpg.dialogue.frame.impl;

import dev.midnightcoder.rpg.dialogue.frame.DialogueFrame;
import dev.midnightcoder.rpg.item.Item;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-18
 */
public class ItemFrame extends DialogueFrame  {
    private final String text;
    private final Item item;

    protected ItemFrame(String id, String speaker, Item item, String message) {
        super(id, speaker);
        this.item = item;
        this.text = message;
    }

    public String getText() {
        return text;
    }

    public Item getItem() {
        return item;
    }
}
