package dev.midnightcoder.rpg.entity.mob.npc.impl;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.dialogue.DialogueManager;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.NpcManager;
import dev.midnightcoder.rpg.entity.mob.npc.behaviors.WanderBehavior;
import dev.midnightcoder.rpg.scene.impl.GameScreen;
import dev.midnightcoder.rpg.util.NpcId;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-19
 */
public class Pokey extends NPC {

    public Pokey(int tileX, int tileY, GameMap currentMap, NpcManager npcManager, GameScreen gameScreen) {
        super(NpcId.POKEY, new Vec2i(tileX << 5, tileY << 5), currentMap, npcManager, gameScreen, gameScreen.getGameServices().dropManager());
        addBehavior(new WanderBehavior(this));
    }

    @Override
    protected int getInteractionDistance() {
        return 2;
    }

    @Override
    public void handleMenuOption(String option) {
        var gs = getGameScreen();
        var player = gs.getPlayer();
        switch (option.toLowerCase()) {
            case "talk-to" -> {
                if (!entityWithinDist(this, getInteractionDistance(), player)) {
                    gs
                        .getDialogueInterface()
                        .sendInfoInter("Too far away", "You are too far away to interact with this.");
                    return;
                }
                gs.getGameServices().dialogueManager().start("pokey_greeting");
            }
            case "examine" ->
                gs
                    .getDialogueInterface()
                    .sendInfoInter(getDefinition().getName(), getDefinition().getDescription());
        }
    }
}
