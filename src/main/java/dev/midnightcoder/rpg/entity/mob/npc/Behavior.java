package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.rpg.entity.mob.player.Player;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public interface Behavior {
    default void update(NPC npc, double delta) {}

    default void onSpawn(NPC npc) {}

    default void onInteraction(NPC npc, Player player) {}

    default void onDamage(NPC npc, int damage) {}

    default void onDeath(NPC npc) {}

    default void onDespawn(NPC npc) {}
}
