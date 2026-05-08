package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.rpg.assets.definitions.NpcDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NpcManager {
    private static NpcManager instance;

    private List<NpcDefinition> definitions;

    public static NpcManager getInstance() {
        if (instance == null) {
            instance = new NpcManager();
        }
        return instance;
    }

    NpcManager() {
        definitions = new ArrayList<>();

        loadDefinitions();
    }

    public NpcDefinition getDefinition(int id) {
        return definitions.stream()
                .filter(def -> def.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void loadDefinitions() {}
}
