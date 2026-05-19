package dev.midnightcoder.rpg.dialogue;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-19
 */
public class DialogueRepository {
    private final Map<String, Dialogue> dialogues = new HashMap<>();

    public Dialogue get(String id) {
        return dialogues.get(id);
    }

    public void register(String id, Dialogue dialogue) {
        dialogues.put(id, dialogue);
    }

    public boolean contains(String id) {
        return dialogues.containsKey(id);
    }
}
