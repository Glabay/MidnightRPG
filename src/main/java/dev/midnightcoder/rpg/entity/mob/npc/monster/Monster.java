package dev.midnightcoder.rpg.entity.mob.npc.monster;

import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.combat.CombatStats;
import dev.midnightcoder.rpg.entity.combat.NpcCombat;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.npc.NPC;
import dev.midnightcoder.rpg.entity.mob.npc.behaviors.CombatBehavior;
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
        this.skillSet = new SkillSet(this);
        var def = getDefinition();
        if (def != null) {
            if (def.getHealth() > 0) {
                setLevel(SkillType.HITPOINTS, def.getHealth());
            }
            if (def.getAttack() > 0) {
                setLevel(SkillType.ATTACK, def.getAttack());
            }
            if (def.getStrength() > 0) {
                setLevel(SkillType.STRENGTH, def.getStrength());
            }
            if (def.getDefence() > 0) {
                setLevel(SkillType.DEFENCE, def.getDefence());
            }
            if (def.getRanged() > 0) {
                setLevel(SkillType.RANGED, def.getRanged());
            }
            if (def.getMagic() > 0) {
                setLevel(SkillType.MAGIC, def.getMagic());
            }
            int atkSpeed = def.getAttackSpeed() > 0 ? def.getAttackSpeed() : 4;
            this.combat = new NpcCombat(this);
        } else {
            this.combatStats = new CombatStats(skillSet.getSkill(SkillType.HITPOINTS).getLevel());
            this.combat = new NpcCombat(this);
        }
        addBehavior(new CombatBehavior(this));
    }

    @Override
    protected int getInteractionDistance() {
        return 2;
    }

    @Override
    public void handleMenuOption(String option) {
        if (isDespawned() || isDead()) return;

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
                if (player != null) {
                    player.getCombat().setTarget(this);
                    player.getCombat().attack(this);
                    if (getCombat() != null) {
                        getCombat().retaliate(player);
                    }
                }
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
        if (combat != null && combat.getTarget() != target) {
            combat.setTarget(target);
        }
    }

    public SkillSet getSkillSet() {
        return skillSet;
    }

    public void setLevel(SkillType skill, int level) {
        skillSet.getSkill(skill).setLevel(level);
        if (skill == SkillType.HITPOINTS) {
            this.combatStats = new CombatStats(level);
        }
    }
}
