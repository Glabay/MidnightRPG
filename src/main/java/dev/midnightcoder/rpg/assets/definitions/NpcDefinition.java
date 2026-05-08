package dev.midnightcoder.rpg.assets.definitions;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NpcDefinition {
    private final int id;
    private String name;

    private final String[] interactions = { "Attack", "Talk-to", "Trade", "Examine" };

    public NpcDefinition(int id) {
        this.id = id;
    }

    public void setInteraction(String interaction, int index) {
        if (index >= 0 && index < interactions.length) {
            interactions[index] = interaction;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String[] getInteractions() {
        return interactions;
    }
}
