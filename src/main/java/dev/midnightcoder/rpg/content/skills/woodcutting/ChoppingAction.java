package dev.midnightcoder.rpg.content.skills.woodcutting;

import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.content.skills.DepletedResource;
import dev.midnightcoder.rpg.content.skills.SkillingAction;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import dev.midnightcoder.rpg.entity.object.GameObject;
import dev.midnightcoder.rpg.entity.skill.SkillType;
import dev.midnightcoder.rpg.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Glabay | The Midnight Coder
 * @project MidnightRPG
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-09-09
 */
public class ChoppingAction extends SkillingAction {
    private static final Logger log = LoggerFactory.getLogger(ChoppingAction.class);

    private final GameObject treeObject;
    private final TreeType treeType;

    public ChoppingAction(Player player, GameObject treeObject) {
        super(player, "Woodcutting", SkillType.WOODCUTTING);
        this.treeObject = treeObject;
        this.treeType = TreeType.findTreeFromObject(treeObject);
    }

    @Override
    public void process() {
        if (player.getSkillSet().getSkill(skillType).getLevel() < treeType.getLevelRequired()) {
            player.sendMessage("You need a level of: " + treeType.getLevelRequired() + " to chop this tree.");
            player.setSkillingAction(null);
            return;
        }
        if (!player.entityWithinDist(treeObject, 1)) {
            player.setSkillingAction(null);
            return;
        }
        if (actionDelay > 0) {
            actionDelay--;
            return;
        }
        if (isSuccessful()) {
            onSuccess();
            depleteResource();
        }
        actionDelay = actionSpeed;
    }

    @Override
    protected boolean isSuccessful() {
        var hatchet = Hatchet.findBestHatchet(player);
        if (hatchet == null) {
            player.setSkillingAction(null);
            MidnightRPG.getInstance()
                .getGameScreen()
                .getDialogueInterface()
                .sendInfoInter("Missing hatchet", "You do not seem to have a hatchet.");
            log.info("Woodcutting action failed due to missing hatchet");
            return false;
        }

        var playerSkillLevel = player.getSkillSet()
            .getSkill(skillType)
            .getLevel();

        var toolPower = playerSkillLevel * hatchet.getEffectiveness();
        var ratio = (double) toolPower / treeType.getLevelRequired();
        var successChance = ratio / (ratio + 1.0);
        return ThreadLocalRandom.current()
            .nextDouble() < successChance;
    }

    @Override
    protected void depleteResource() {
        var originalTextureId = treeObject.getDefinition().getTextureId();
        MidnightRPG.getInstance()
            .addDepletedResource(treeObject,
                new DepletedResource(originalTextureId, treeType.getRespawnTicks())
            );
        MidnightRPG.getInstance()
            .getGameScreen()
            .getCurrentMap()
            .getGameObjects()
            .remove(treeObject);

        player.setSkillingAction(null);

    }

    @Override
    protected void onSuccess() {
        player.getSkillSet()
            .getSkill(skillType)
            .addExp(treeType.getExperience());

        player.addItem(new Item(treeType.getLogId(), 1));
    }
}
