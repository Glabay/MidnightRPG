package dev.midnightcoder.rpg.dialogue.script;

import dev.midnightcoder.rpg.dialogue.DialogueManager;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.behaviors.DialogueBehavior;
import dev.midnightcoder.rpg.entity.mob.player.Player;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-19
 */
public class PokeyGreeting extends DialogueBehavior {

    @Override
    public void onInteraction(NPC npc, Player player) {
        DialogueManager.getInstance().start("pokey_greeting");
    }
}
