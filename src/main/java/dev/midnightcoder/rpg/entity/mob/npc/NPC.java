package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.cache.model.NPCDefinition;
import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.NpcAvatar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NPC extends Mob {
    private final NpcAvatar avatar;
    private final NpcMovement movement;
    private final GameMap currentMap;
    private final Vec2i spawnPosition;
    private final NPCDefinition definition;
    private final List<Behavior> behaviors = new ArrayList<>();
    private final int id;

    private final Random random = new Random();

    public NPC(int id, Vec2i position, GameMap currentMap) {
        this.id = id;
        this.movement = new NpcMovement(currentMap.getTileMap());
        this.spawnPosition = position;
        this.avatar = new NpcAvatar(spawnPosition, movement, currentMap);
        this.currentMap = currentMap;
        this.definition = NpcManager.getInstance().getDefinition(id);
    }

    @Override
    public void update(double delta) {
        getAvatar().updateHitbox();

        behaviors.forEach(behavior ->
            behavior.update(this, delta));

        if (random.nextDouble() < 0.01) {
            if (random.nextDouble() < 0.3)
                getAvatar().setDirection(Direction.NORTH);
            else if (random.nextDouble() > 0.3 && random.nextDouble() < 0.6)
                getAvatar().setDirection(Direction.SOUTH);
            else if (random.nextDouble() > 0.6 && random.nextDouble() < 0.9)
                getAvatar().setDirection(Direction.WEST);
            else if (random.nextDouble() > 0.9)
                getAvatar().setDirection(Direction.EAST);

            var dx = (int) (getAvatar().getMoveX() * speed * delta);
            var dy = (int) (getAvatar().getMoveY() * speed * delta);

            movement.move(getAvatar(), dx, dy);
        }
    }

    @Override
    public void render(Renderer renderer) {
        avatar.render(renderer);
    }

    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
    }

    /// Getters + Setters below

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public NPCDefinition getDefinition() {
        return definition;
    }

    public NpcAvatar getAvatar() {
        return avatar;
    }

    public Vec2i getSpawnPosition() {
        return spawnPosition;
    }

    public Movement getMovement() {
        return movement;
    }

    public int getId() {
        return id;
    }

    @Override
    public int getX() {
        return avatar.getX();
    }

    @Override
    public int getY() {
        return avatar.getY();
    }

    @Override
    public int getWidth() {
        return avatar.getWidth();
    }

    @Override
    public int getHeight() {
        return avatar.getHeight();
    }

    @Override
    public void handleMenuOption(String option) {
        var player = MidnightRPG.getInstance().getGameScreen().getPlayer();
        switch (option.toLowerCase()) {
            case "talk-to" -> {
                // if the user is too far, send a dialogue message
                if (!entityWithinDist(this, 2)) {
                    MidnightRPG.getInstance()
                        .getGameScreen()
                        .getDialogueInterface()
                        .sendInfoInter("Too far away", "You are too far away to interact with this.");
                    return;
                }
                // TODO: Load this NPC's dialogue

            }
            case "examine" ->
                MidnightRPG.getInstance()
                    .getGameScreen()
                    .getDialogueInterface()
                    .sendInfoInter(getDefinition().getName(), getDefinition().getDescription());
        }
    }

}
