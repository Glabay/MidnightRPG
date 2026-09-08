package dev.midnightcoder.rpg.content.skills.mining;

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
 * @since 2026-09-06
 */
public class MiningAction extends SkillingAction {

    private static final Logger log = LoggerFactory.getLogger(MiningAction.class);
    private final GameObject rockObject;
    private final RockType rockType;

    public MiningAction(Player player, GameObject rockObject) {
        super(player, "Mining", SkillType.MINING);
        this.rockObject = rockObject;
        this.rockType = RockType.findRockTypeFromObject(rockObject);
    }

    @Override
    public void process() {
        if (!player.entityWithinDist(rockObject, 1)) {
            player.setSkillingAction(null);
            return;
        }

        if (actionDelay > 0) {
            actionDelay--;
            return;
        }
        log.info("Mining action processing");
        if (isSuccessful()) {
            onSuccess();
            depleteResource();
        }
        actionDelay = actionSpeed;
    }

    @Override
    protected boolean isSuccessful() {
        var pickaxeType = PickaxeType.findBestPickaxe(player);
        if (pickaxeType == null) {
            player.setSkillingAction(null);
            MidnightRPG.getInstance()
                .getGameScreen()
                .getDialogueInterface()
                .sendInfoInter("Missing Pickaxe", "You do not seem to have a pickaxe.");
            log.info("Mining action failed due to missing pickaxe");
            return false;
        }

        var miningLevel = player.getSkillSet()
            .getSkill(skillType)
            .getLevel();

        var miningPower = miningLevel * pickaxeType.getEffectiveness();
        var ratio = (double) miningPower / rockType.getLevelRequired();
        var successChance = ratio / (ratio + 1.0);
        return ThreadLocalRandom.current()
            .nextDouble() < successChance;
    }

    @Override
    protected void onSuccess() {
        player.getSkillSet()
            .getSkill(skillType)
            .addExp((int) rockType.getMiningExperience());

        player.addItem(new Item(rockType.getOreId(), 1));
    }

    @Override
    protected void depleteResource() {
        var originalTextureId = rockObject.getDefinition().getTextureId();
        MidnightRPG.getInstance()
            .addDepletedResource(rockObject,
                new DepletedResource(originalTextureId, rockType.getRespawnTicks())
            );
        MidnightRPG.getInstance()
            .getGameScreen()
            .getCurrentMap()
            .getGameObjects()
            .remove(rockObject);

        player.setSkillingAction(null);
    }

}
