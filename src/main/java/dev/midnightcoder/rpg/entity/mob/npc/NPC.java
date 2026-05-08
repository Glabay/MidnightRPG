package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.engine.system.Movement;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.assets.definitions.NpcDefinition;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.NpcAvatar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NPC extends Mob {
    private final NpcAvatar avatar;
    private final NpcMovement movement; // TODO: NpcMovement
    private final GameMap currentMap;
    private final Vec2i spawnPosition;
    private final NpcDefinition definition;
    private final List<Behavior> behaviors = new ArrayList<>();
    private final int id;

    public NPC(int id, Vec2i position, NpcDefinition definition, NpcMovement movement, GameMap currentMap) {
        this.id = id;
        this.avatar = new NpcAvatar(position, movement, currentMap);
        this.movement = movement;
        this.currentMap = currentMap;
        this.spawnPosition = position;
        this.definition = definition;
    }

    @Override
    public void update(double delta) {
        getAvatar().update(delta);

        behaviors.forEach(behavior ->
            behavior.update(this, delta));
    }

    public void addBehavior(Behavior behavior) {
        behaviors.add(behavior);
    }

    /// Getters + Setters below

    public GameMap getCurrentMap() {
        return currentMap;
    }

    public NpcDefinition getDefinition() {
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

}
