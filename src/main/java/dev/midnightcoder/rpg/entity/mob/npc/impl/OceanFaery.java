package dev.midnightcoder.rpg.entity.mob.npc.impl;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.behaviors.CombatBehavior;
import dev.midnightcoder.rpg.entity.mob.npc.behaviors.WanderBehavior;
import dev.midnightcoder.rpg.util.NpcId;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-19
 */
public class OceanFaery extends NPC {

    public OceanFaery(int tileX, int tileY, GameMap currentMap) {
        super(NpcId.OCEANFAERY, new Vec2i(tileX << 5, tileY << 5), currentMap);
        addBehavior(new WanderBehavior(this));
    }

    @Override
    protected int getInteractionDistance() {
        return 2;
    }

    @Override
    public void handleMenuOption(String option) {
        var player = MidnightRPG.getInstance().getGameScreen().getPlayer();
        switch (option.toLowerCase()) {
            case "attack" -> {
                // if the user is too far, send a dialogue message
                if (!entityWithinDist(this, getInteractionDistance())) {
                    MidnightRPG.getInstance()
                        .getGameScreen()
                        .getDialogueInterface()
                        .sendInfoInter("Too far away", "You are too far away to interact with this.");
                    return;
                }
                // TODO: Implement Combat System
            }
            case "examine" ->
                MidnightRPG.getInstance()
                    .getGameScreen()
                    .getDialogueInterface()
                    .sendInfoInter(getDefinition().getName(), getDefinition().getDescription());
        }
    }
}
