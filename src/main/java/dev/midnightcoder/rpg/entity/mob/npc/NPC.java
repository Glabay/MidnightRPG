package dev.midnightcoder.rpg.entity.mob.npc;

import dev.midnightcoder.cache.CacheReader;
import dev.midnightcoder.cache.model.NPCDefinition;
import dev.midnightcoder.engine.entity.Direction;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.MidnightRPG;
import dev.midnightcoder.rpg.drop.DropManager;
import dev.midnightcoder.rpg.drop.NPCDropTable;
import dev.midnightcoder.rpg.entity.combat.CombatStats;
import dev.midnightcoder.rpg.entity.combat.NpcCombat;
import dev.midnightcoder.rpg.entity.ground.GroundItem;
import dev.midnightcoder.rpg.entity.mob.Mob;
import dev.midnightcoder.rpg.entity.mob.NpcAvatar;
import dev.midnightcoder.rpg.entity.mob.player.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static dev.midnightcoder.rpg.entity.combat.Combat.DEFAULT_DEATH_DELAY;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-07
 */
public class NPC extends Mob {
    private static final Logger log = LoggerFactory.getLogger(NPC.class);
    private final NpcAvatar avatar;
    private final NpcMovement movement;
    private final GameMap currentMap;
    private final Vec2i spawnPosition;
    private final NPCDefinition definition;
    private final List<Behavior> behaviors = new ArrayList<>();
    private final int id;
    private boolean despawned = false;
    private NPCDropTable dropTable;

    public NPC(int id, Vec2i position, GameMap currentMap) {
        this.id = id;
        this.movement = new NpcMovement(currentMap.getTileMap());
        this.spawnPosition = position;
        this.avatar = new NpcAvatar(spawnPosition, movement, currentMap);
        this.currentMap = currentMap;
        this.definition = NpcManager.getInstance().getDefinition(id);
        loadAnimatedFrames();
        var frames = definition.getAnimatedFrames(Direction.SOUTH);
        if (frames != null && frames.length > 0) {
            getAvatar().animatedSprite.setFrames(frames);
            getAvatar().setAvatarTexture(getAvatar().animatedSprite.getCurrentFrame());
        }

        this.lastX = getAvatar().getX();
        this.lastY = getAvatar().getY();
        this.deathDelay = definition.getDeathDelay() > 0 ? definition.getDeathDelay() : DEFAULT_DEATH_DELAY;
        this.combat = new NpcCombat(this);
        int health = 10;
        if (definition.getHealth() > 0) {
            health = definition.getHealth();
        }
        else if (definition.getCombatLevel() > 0) {
            health = definition.getCombatLevel() * 2;
        }
        this.combatStats = new CombatStats(health);
    }

    private void loadAnimatedFrames() {
        // set the NPC AnimatedSprites
        var cacheMan = CacheReader.getInstance().getCacheManager();
        var spriteSheets = cacheMan.getSpriteSheets();
        var sheetId = definition.getSpriteSheetId();

        if (sheetId < 0 || sheetId >= spriteSheets.size()) {
            log.error("Invalid sprite sheet ID: {} for NPC {}", sheetId, id);
            return;
        }

        var spriteSheet = spriteSheets.get(sheetId);
        var cachedSpriteIndex = spriteSheet.getSpriteId();
        var cachedSprite = CacheReader.getInstance().getTexture(cachedSpriteIndex);

        for (int row = 0; row < spriteSheet.getRows(); row++) {
            var animatedFrames = getAvatarImages(spriteSheet, cachedSprite, row);
            switch (row) {
                case 0 -> definition.setAnimatedFrames(Direction.SOUTH, animatedFrames);
                case 1 -> definition.setAnimatedFrames(Direction.NORTH, animatedFrames);
                case 2 -> definition.setAnimatedFrames(Direction.WEST, animatedFrames);
                default -> definition.setAnimatedFrames(Direction.EAST, animatedFrames);
            }
        }
    }

    public int getWalkRadius() {
        return 0;
    }

    @Override
    public void update(double delta) {
        if (despawned) return;

        if (dying || isDead()) {
            if (!dying) onDeath();

            int ticks = Math.max(1, (int) Math.round(delta));
            deathTicks -= ticks;
            if (deathTicks <= 0) {
                despawn();
                dropItems();
            }
            updateHitsplats(delta);
            return;
        }

        super.update(delta);
        getAvatar().updateHitbox();

        behaviors.forEach(behavior ->
            behavior.update(this, delta));
    }

    @Override
    public void render(Renderer renderer) {
        if (despawned) return;

        avatar.render(renderer);
        super.render(renderer);
    }

    @Override
    public void onDeath() {
        super.onDeath();
        if (combat != null) {
            combat.reset();
        }
    }

    public List<GroundItem> dropItems() {
        Player killer = (lastAttacker instanceof Player p) ? p : null;
        return DropManager.getInstance().dropItems(this, killer);
    }

    public void despawn() {
        this.despawned = true;
    }

    public boolean isDespawned() {
        return despawned;
    }

    public void setDespawned(boolean despawned) {
        this.despawned = despawned;
    }

    public NPCDropTable getDropTable() {
        return dropTable;
    }

    public void setDropTable(NPCDropTable dropTable) {
        this.dropTable = dropTable;
    }

    @Override
    public void applyHit(int damage) {
        super.applyHit(damage);
        behaviors.forEach(behavior -> behavior.onDamage(this, damage));
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

    public NpcMovement getMovement() {
        return movement;
    }

    public int getId() {
        return id;
    }

    protected int getInteractionDistance() {
        return 1;
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
    public NpcCombat getCombat() {
        return (NpcCombat) combat;
    }

    @Override
    public void handleMenuOption(String option) {
        if (despawned || isDead()) {
            return;
        }
        if (option.equalsIgnoreCase("attack")) {
            if (!entityWithinDist(this, getInteractionDistance())) {
                MidnightRPG.getInstance()
                    .getGameScreen()
                    .getDialogueInterface()
                    .sendInfoInter("Too far away", "You are too far away to interact with this.");
                return;
            }
            var player = MidnightRPG.getInstance().getGameScreen().getPlayer();
            if (player != null) {
                player.getCombat().setTarget(this);
                player.getCombat().attack(this);
                if (getCombat() != null) {
                    getCombat().retaliate(player);
                }
            }
        }
        else if (option.equalsIgnoreCase("examine")) {
            MidnightRPG.getInstance()
                .getGameScreen()
                .getDialogueInterface()
                .sendInfoInter(getDefinition().getName(), getDefinition().getDescription());
        }
    }
}
