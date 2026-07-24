package dev.midnightcoder.rpg.entity.mob.npc.monster;

import dev.midnightcoder.engine.util.Boundary;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.behaviors.CombatBehavior;
import dev.midnightcoder.rpg.entity.mob.npc.behaviors.WanderBehavior;
import dev.midnightcoder.rpg.entity.skill.SkillSet;
import dev.midnightcoder.rpg.entity.skill.SkillType;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-06-24
 */
public class Monster extends NPC {
    protected final SkillSet skillSet;

    protected Mob target;

    public Monster(int id, Vec2i position, GameMap currentMap) {
        super(id, position, currentMap);
        addBehavior(new CombatBehavior(this));

        this.skillSet = new SkillSet(this);
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
                setTarget(player);
            }
            case "examine" ->
                MidnightRPG.getInstance()
                    .getGameScreen()
                    .getDialogueInterface()
                    .sendInfoInter(getDefinition().getName(), getDefinition().getDescription());
        }
    }

    @Override
    public int getWalkRadius() {
        return 4;
    }

    public int getAttackRange() {
        return getInteractionDistance();
    }

    public Mob getTarget() {
        return target;
    }

    public void setTarget(Mob target) {
        this.target = target;
    }

    protected void setLevel(SkillType skill, int level) {
        skillSet.getSkills().get(skill.ordinal()).setLevel(level);
    }
}
