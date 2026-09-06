package dev.midnightcoder.rpg.entity.mob;

import dev.midnightcoder.cache.model.SpriteSheet;
import dev.midnightcoder.engine.renderer.Renderer;
import dev.midnightcoder.engine.renderer.graphics.Texture;
import dev.midnightcoder.engine.util.Vec2i;
import dev.midnightcoder.engine.world.GameMap;
import dev.midnightcoder.rpg.entity.Entity;
import dev.midnightcoder.rpg.entity.combat.Combat;
import dev.midnightcoder.rpg.entity.combat.CombatStats;
import dev.midnightcoder.rpg.entity.combat.Hitsplat;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Glabay | Glabay-Studios
 * @project MidnightRPG
 * @social Discord: Glabay
 * @since 2026-05-01
 */
public abstract class Mob extends Entity {
    public int speed = 3;
    public int lastX = -1;
    public int lastY = -1;

    public boolean moving = false;

    protected CombatStats combatStats;
    protected Combat combat;
    protected Mob lastAttacker;
    protected int deathDelay = 4;
    protected int deathTicks = 0;
    protected boolean dying = false;
    protected final List<Hitsplat> hitsplats = new CopyOnWriteArrayList<>();

    @Override
    public void update(double delta) {
        super.update(delta);
        if (combat != null)
            combat.tick();
        updateHitsplats(delta);
    }

    @Override
    public void render(Renderer renderer) {
        super.render(renderer);
        renderHitsplats(renderer);
    }

    public void applyHit(int damage) {
        if (combatStats != null)
            combatStats.damage(damage);
        addHitsplat(new Hitsplat(damage));
        if (isDead() && !dying)
            onDeath();
    }

    public void applyHit(int damage, Mob attacker) {
        if (attacker != null)
            this.lastAttacker = attacker;
        applyHit(damage);
    }

    public void onDeath() {
        dying = true;
        deathTicks = deathDelay;
        if (combat != null)
            combat.reset();
    }

    public void addHitsplat(Hitsplat hitsplat) {
        if (hitsplat != null)
            hitsplats.add(hitsplat);
    }

    public void clearHitsplats() {
        hitsplats.clear();
    }

    public void updateHitsplats(double delta) {
        for (Hitsplat hitsplat : hitsplats) {
            hitsplat.update(delta);
        }
        hitsplats.removeIf(Hitsplat::isExpired);
    }

    public GameMap getCurrentMap() {
        return null;
    }

    public void renderHitsplats(Renderer renderer) {
        if (hitsplats.isEmpty() || renderer == null) {
            return;
        }
        GameMap map = getCurrentMap();
        if (map == null || map.getCamera() == null) {
            return;
        }
        Graphics2D g2d = renderer.getGraphics2D();
        if (g2d == null) {
            return;
        }

        int screenX = (int) (getX() - map.getCamera().getX());
        int screenY = (int) (getY() - map.getCamera().getY());
        int entityWidth = getWidth() > 0 ? getWidth() : 32;
        int entityHeight = getHeight() > 0 ? getHeight() : 32;

        int centerX = screenX + (entityWidth / 2);
        int centerY = screenY + (entityHeight / 2);

        int count = hitsplats.size();
        for (int i = 0; i < count; i++) {
            Hitsplat hitsplat = hitsplats.get(i);
            if (hitsplat == null || hitsplat.isExpired()) {
                continue;
            }
            int offsetX = 0;
            int offsetY = 0;
            if (count > 1) {
                offsetX = (i - (count - 1) / 2) * 14;
                offsetY = (i % 2 == 0 ? -2 : 2);
            }
            hitsplat.render(renderer, centerX + offsetX, centerY + offsetY);
        }
    }

    public Vec2i getPosition() {
        return new Vec2i(getX(), getY());
    }

    public CombatStats getCombatStats() {
        return combatStats;
    }

    public Combat getCombat() {
        return combat;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    public boolean isDead() {
        return combatStats != null && combatStats.isDead();
    }

    public int getCurrentHealth() {
        return combatStats != null ? combatStats.getCurrentHealth() : 0;
    }

    protected BufferedImage[] getAvatarImages(SpriteSheet spriteSheet, Texture cachedSprite, int row) {
        var animatedFrames = new BufferedImage[spriteSheet.getCols()];
        for (int col = 0; col < spriteSheet.getCols(); col++) {
            var frame = cachedSprite.image().getSubimage(
                col * spriteSheet.getFrameWidth(),
                row * spriteSheet.getFrameHeight(),
                spriteSheet.getFrameWidth(),
                spriteSheet.getFrameHeight()
            );
            animatedFrames[col] = frame;
        }
        return animatedFrames;
    }
}
