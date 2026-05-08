package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.engine.renderer.Renderer;
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

        var dx = (int) (getAvatar().getMoveX() * speed * delta);
        var dy = (int) (getAvatar().getMoveY() * speed * delta);

        movement.move(getAvatar(), dx, dy);
    }

    @Override
    public void render(Renderer renderer) {
        avatar.render(renderer);
        IO.println("NPC Render: " + spawnPosition);
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
